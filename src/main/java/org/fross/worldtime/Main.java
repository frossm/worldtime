/******************************************************************************
 * WorldTime
 * 
 * WorldTime is a console based tool to display local times around the world
 * 
 *  Copyright (c) 2021-2022 Michael Fross
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *           
 ******************************************************************************/
package org.fross.worldtime;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.fross.library.Debug;
import org.fross.library.GitHub;
import org.fross.library.Output;
import org.fusesource.jansi.Ansi;

import gnu.getopt.Getopt;

/**
 * Main program entry point
 * 
 * @author Michael
 *
 */
public class Main {
	// Class Constants
	protected static final String PROPERTIES_FILE = "app.properties";
	protected static final String WORLD_TIME_API_ROOT_URL = "http://worldtimeapi.org/api/timezone/";
	protected static final String PREFS_FAVORITES = "org/fross/worldtime/favorites";
	protected static final int DISPLAY_WIDTH = 80;

	// Class Variables
	public static String programVersion;
	public static String programCopyright;

	// public static String

	public static void main(String[] args) {
		int optionEntry;
		ArrayList<String> areaCitiesList = new ArrayList<String>();
		boolean flagDetailed = false;
		boolean flagAddToFavorites = false;

		// Process application level properties file
		// Update properties from Maven at build time:
		// https://stackoverflow.com/questions/3697449/retrieve-version-from-maven-pom-xml-in-code
		try {
			InputStream iStream = Main.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
			Properties prop = new Properties();
			prop.load(iStream);
			programVersion = prop.getProperty("Application.version");
			programCopyright = "Copyright (C) " + prop.getProperty("Application.inceptionYear") + "-" + org.fross.library.Date.getCurrentYear()
					+ " by Michael Fross";

			iStream.close();

		} catch (IOException ex) {
			Output.fatalError("Unable to read property file '" + PROPERTIES_FILE + "'", 3);
		}

		// Create the supported city object for use later
		SupportedCities sc = new SupportedCities();

		// Display program header
		Output.printColorln(Ansi.Color.CYAN, "WorldTime v" + programVersion + "  " + programCopyright + "\n");

		// Process Command Line Options and set flags where needed
		Getopt optG = new Getopt("WorldTime", args, "Dh?vaei:lrsx:dz");
		while ((optionEntry = optG.getopt()) != -1) {
			switch (optionEntry) {

			/*****************************************
			 ** General Command Line Options
			 *****************************************/
			// Enable debug mode
			case 'D':
				Debug.enable();
				break;

			// Help
			case 'h':
			case '?':
				Help.Display();
				System.exit(0);
				break;

			// Display current program version and latest GitHub release
			case 'v':
				Output.printColorln(Ansi.Color.WHITE, "WorldTime Version: v" + programVersion);
				Output.printColorln(Ansi.Color.CYAN, programCopyright);
				Output.printColor(Ansi.Color.WHITE, "\nLatest Release on GitHub: ");
				Output.printColorln(Ansi.Color.YELLOW, GitHub.updateCheck("worldtime"));
				Output.printColorln(Ansi.Color.CYAN, "HomePage: https://github.com/frossm/worldtime");
				System.exit(0);
				break;

			/*****************************************
			 ** Options for Managing Favorites
			 *****************************************/
			// Add the TZ/Cities on the command line to favorites
			case 'a':
				flagAddToFavorites = true;
				break;

			// Erase all saved favorites
			case 'e':
				Output.printColorln(Ansi.Color.YELLOW, "All Favorites Erased");
				Favorites.eraseAll();
				System.exit(0);
				break;

			// Import a list of favorites and replace those currently saved
			case 'i':
				break;

			// List current favorite cities
			case 'l':
				String[] favs = Favorites.getFavorites();
				Output.printColorln(Ansi.Color.YELLOW, "Saved Favorites:");

				for (String i : favs) {
					Output.printColorln(Ansi.Color.WHITE, " - " + i);
				}
				System.exit(0);
				break;

			// Remove a TZ/City from the favorites
			case 'r':
				Output.printColorln(Ansi.Color.YELLOW, "Removing AREAS/CITIES from Favorites");

				// Loop through the command line items and remove each one
				for (int i = optG.getOptind(); i < args.length; i++) {
					boolean result = Favorites.remove(args[i]);
					if (result == true) {
						Output.printColorln(Ansi.Color.WHITE, "Removing '" + args[i] + "' from Favorites");
					} else {
						Output.printColorln(Ansi.Color.WHITE, "'" + args[i] + "' does not exist in Favorites");
					}
				}
				System.exit(0);
				break;

			// Show a list of supported TZ/City combinations
			case 's':
				sc.printCitiesList();
				System.exit(0);
				break;

			// Export the TZ/City information to the provided file
			case 'x':
				break;

			/*****************************************
			 ** Managing Display
			 *****************************************/
			// Show detailed output on each TZ/City
			case 'd':
				flagDetailed = true;
				break;

			// Display colorized output
			case 'z':
				Output.enableColor(false);
				break;

			/*****************************************
			 ** Command Line Option Error
			 *****************************************/
			default:
				Output.printColorln(Ansi.Color.RED, "ERROR: Unknown Command Line Option: '" + (char) optionEntry + "'");
				Help.Display();
				System.exit(0);
				break;
			}
		}

		// Determine if any AREA/CITIES were entered on the command line
		Output.debugPrint("Number of Area/Cities entered on the command line: " + (args.length - optG.getOptind()));
		for (int i = optG.getOptind(); i < args.length; i++) {
			Output.debugPrint(" - Area/City Entered: " + args[i]);
			areaCitiesList.add(args[i]);
		}

		// If we use -a [add] then add the command line items to favorites
		if (flagAddToFavorites == true) {
			Output.printColorln(Ansi.Color.YELLOW, "Adding areas/cities on command line to Favorites");
			Favorites.add(areaCitiesList);
			System.exit(0);
		}

		// Read list of favorites and add it to the list we'll display
		Output.debugPrint("Number of Area/Cities pulled from favorites: " + Favorites.getFavorites().length);
		for (String i : Favorites.getFavorites()) {
			Output.debugPrint(" - Area/City Retrieved: " + i);
			areaCitiesList.add(i);
		}

		// Loop through each AREA/CITY in our CitiesListd and get the time zone information
		for (String areaCity : areaCitiesList) {
			// Determine if area/city is valid
			if (sc.isValid(areaCity) == false) {
				Output.printColorln(Ansi.Color.RED, "'" + areaCity + "' is not a valid AREA/CITY");
				break;
			}

			// Print the City Name
			Output.printColorln(Ansi.Color.YELLOW, (areaCity.substring(areaCity.indexOf('/') + 1).toUpperCase()));

			WorldTime wt = new WorldTime(WORLD_TIME_API_ROOT_URL + areaCity);

			// If a detailed view is requested with -d, give all information and jump to next city
			if (flagDetailed == true) {
				Output.printColorln(Ansi.Color.WHITE, wt.queryValueAll(WORLD_TIME_API_ROOT_URL + areaCity));
				break;
			}

			// Display the result
			Output.printColor(Ansi.Color.WHITE, wt.queryValue("day_of_week_name") + ", ");
			Output.printColor(Ansi.Color.WHITE, wt.queryValue("monthname") + " ");
			Output.printColor(Ansi.Color.WHITE, wt.queryValue("day") + ", " + wt.queryValue("year") + "  ");
			Output.printColor(Ansi.Color.WHITE, wt.queryValue("hour") + ":");
			Output.printColor(Ansi.Color.WHITE, wt.queryValue("minute") + ":");
			Output.printColorln(Ansi.Color.WHITE, wt.queryValue("second"));

			Output.println("");
		}

	}
}