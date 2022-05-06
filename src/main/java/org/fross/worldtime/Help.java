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

import org.fross.library.Format;
import org.fross.library.Output;
import org.fusesource.jansi.Ansi;

public class Help {
	/**
	 * Display(): Display the program help information
	 * 
	 */
	public static void Display() {
		Output.printColor(Ansi.Color.CYAN, "\n+" + "-".repeat(Main.DISPLAY_WIDTH) + "+\n+");
		Output.printColor(Ansi.Color.WHITE, Format.CenterText(Main.DISPLAY_WIDTH, ("WorldTime   v" + Main.programVersion)));
		Output.printColor(Ansi.Color.CYAN, "+\n+");
		Output.printColor(Ansi.Color.WHITE, Format.CenterText(Main.DISPLAY_WIDTH, Main.programCopyright));
		Output.printColorln(Ansi.Color.CYAN, "+\n+" + "-".repeat(Main.DISPLAY_WIDTH) + "+");
		Output.printColorln(Ansi.Color.CYAN, Format.CenterText(Main.DISPLAY_WIDTH, "WorldTime is the console tool to display times around the world"));
		Output.printColorln(Ansi.Color.CYAN, Format.CenterText(Main.DISPLAY_WIDTH, "https://github.com/frossm/worldtime"));

		Output.printColorln(Ansi.Color.YELLOW, "\nUsage:");
		Output.printColorln(Ansi.Color.WHITE, " Standard:  java -jar worldtime.jar [options] [area/city] [area/city] [...]");
		Output.printColorln(Ansi.Color.WHITE, " Snap:      worldtime [options] [area/city]");

		Output.printColorln(Ansi.Color.YELLOW, "\nGeneral Command Line Options:");
		Output.printColorln(Ansi.Color.WHITE, " -D        Start in debug mode");
		Output.printColorln(Ansi.Color.WHITE, " -h | ?    Show this help information and exit");
		Output.printColorln(Ansi.Color.WHITE, " -v        Display version information and the latest release on GitHub, and exit");

		Output.printColorln(Ansi.Color.YELLOW, "\nManaging Favorites:");
		Output.printColorln(Ansi.Color.WHITE, " -a        Add the provided area/cities on the command line to your list");
		Output.printColorln(Ansi.Color.WHITE, " -e        Erase all saved favorites");
		Output.printColorln(Ansi.Color.WHITE, " -i FILE   Import new set of saved areas/cities from file replacing current favorites");
		Output.printColorln(Ansi.Color.WHITE, " -l        List currently saved favorites");
		Output.printColorln(Ansi.Color.WHITE, " -r        Remove the TimeZone/Cities on the commandline from favorites");
		Output.printColorln(Ansi.Color.WHITE, " -s        Show a list of supported timezones/cities and exit");
		Output.printColorln(Ansi.Color.WHITE, " -x FILE   Export your saved timezone/cities to the provided file");

		Output.printColorln(Ansi.Color.YELLOW, "\nManaging Display:");
		Output.printColorln(Ansi.Color.WHITE, " -2  Display time in 24 hour format instead of the default 12 hour format");
		Output.printColorln(Ansi.Color.WHITE, " -d  Show detailed time information");
		Output.printColorln(Ansi.Color.WHITE, " -z  Disable colorized output");

		Output.printColorln(Ansi.Color.YELLOW, "\nAreas/Cities:");
		Output.printColorln(Ansi.Color.WHITE, "  For a list of supported areas and cities, please execute with ");
		Output.printColorln(Ansi.Color.WHITE, "  the '-s' switch.  Then choose the area/city you would like to save");
		Output.printColorln(Ansi.Color.WHITE, "  to your favorites (or for use with the input command).");

		Output.printColorln(Ansi.Color.YELLOW, "\nExamples of AREA/CITY from the list of supported items:");
		Output.printColorln(Ansi.Color.WHITE, "  America/Chicago");
		Output.printColorln(Ansi.Color.WHITE, "  America/Argentina/Buenos_Aires");
		Output.printColorln(Ansi.Color.WHITE, "  Asia/Manila");
		Output.printColorln(Ansi.Color.WHITE, "  Etc/GMT-14");
		Output.printColorln(Ansi.Color.CYAN, "  (Use '-s' switch to see complete list...)");

		Output.printColorln(Ansi.Color.YELLOW, "\nNotes:");
		Output.printColorln(Ansi.Color.WHITE, "  If installed via a snap, you'll need to assign Worldtime the ability to read/write");
		Output.printColorln(Ansi.Color.WHITE, "  to your home directory via:  sudo snap connect worldtime:home");
		Output.printColorln(Ansi.Color.WHITE, "\n  Special thanks to the folks at WorldTimeAPI.org for powering this program");
		Output.printColorln(Ansi.Color.WHITE, "  with their fantastic API.  Your work is appreciated!");
	}
}
