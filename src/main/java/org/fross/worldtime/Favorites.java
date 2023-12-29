/******************************************************************************
 * WorldTime
 * 
 * WorldTime is a console based tool to display local times around the world
 * 
 *  Copyright (c) 2021-2024 Michael Fross
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

import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.fross.library.Output;
import org.fusesource.jansi.Ansi;

public class Favorites {
	private static Preferences prefs = Preferences.userRoot().node(Main.PREFS_FAVORITES);

	/**
	 * add(): Ad the provided cities in the string array to favorites
	 * 
	 * @param cities
	 */
	public static void add(ArrayList<String> cities) {
		for (Object i : cities.toArray()) {
			prefs.put(i.toString(), "");
		}
	}

	/**
	 * add(): Add the provided AREA/CITY to the favorites
	 * 
	 * @param city
	 */
	public static void add(String city) {
		prefs.put(city, "");
	}

	/**
	 * remove(): Remove the provided city from the favorites.
	 * 
	 * @param city
	 * @return
	 */
	public static boolean remove(String city) {
		boolean result = true;

		try {
			// Determine if the favorite exists
			if ((prefs.get(city, "-1")) == "-1") {
				result = false;
			} else {
				// Remove the city from the favorites
				prefs.remove(city);
				result = true;
			}

		} catch (Exception ex) {
			Output.printColorln(Ansi.Color.RED, "ERROR: Could not remove '" + city + "' from Favorites");
			ex.getMessage();
		}

		return result;
	}

	/**
	 * getFavorites(): Return a string array of all favorites
	 * 
	 * @return
	 */
	public static String[] getFavorites() {
		String[] locations = {};
		try {
			locations = prefs.keys();
		} catch (BackingStoreException e) {
			Output.fatalError("Could not read Favorites from preference system", 6);
		}

		return locations;
	}

	/**
	 * eraseAll(): Clear all preferences in Favorites
	 * 
	 */
	public static void eraseAll() {
		try {
			prefs.clear();
		} catch (BackingStoreException ex) {
			Output.printColorln(Ansi.Color.RED, "Could not clear favorites");
			ex.getMessage();
		}
	}
}
