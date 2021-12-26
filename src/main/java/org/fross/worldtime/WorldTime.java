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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fross.library.Output;
import org.fross.library.URLOperations;
import org.fusesource.jansi.Ansi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WorldTime {
	static final String[] DAY_OF_WEEK_LONG = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
	static final String[] DAY_OF_WEEK_SHORT = { "Sun", "Mon", "Tue", "Wed", "Thurs", "Fri", "Sat" };
	static final String[] MONTH_NAME_LONG = { "", "January", "February", "March", "April", "May", "June", "July", "August", "Sept", "October", "November",
			"December" };
	HashMap<String, String> cityInfo = new HashMap<>();

	public WorldTime(String url) {
		String apiResult = null;
		try {
			// Call the WorldTime API
			Output.debugPrint("Calling API: '" + url + "'");
			apiResult = URLOperations.ReadURL(url);
		} catch (Exception ex) {
			Output.printColorln(Ansi.Color.RED, "ERROR: Could not read from WorldTimeAPI: " + url);
		}

		// Convert the JSON to a hashmap
		try {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();

			@SuppressWarnings("unchecked")
			Map<String, Object> gsonMap = gson.fromJson(apiResult, Map.class);

			// Loop through the <String,Object> map and convert it to a <String, String> hashmap
			for (Map.Entry<String, Object> i : gsonMap.entrySet()) {
				String key = i.getKey();
				try {
					cityInfo.put(key, gsonMap.get(key).toString());

				} catch (NullPointerException ex) {
					cityInfo.put(key, "-");
				}
			}
		} catch (Exception ex) {
			Output.printColorln(Ansi.Color.RED, "ERROR: Could not convert API results to HashMap");
		}

		// Build the Month/Day/Year/Hour/Minute/Second values and place them in the hashmap
		Pattern pattern = Pattern.compile("(\\d\\d\\d\\d)-(\\d+)-(\\d+)T(\\d\\d):(\\d+):(\\d+)\\..*");
		Matcher m = pattern.matcher(this.queryValue("datetime"));
		if (m.find()) {
			Output.debugPrint("DateTime:" + m.group(0));
			Output.debugPrint("Year:\t" + m.group(1));
			Output.debugPrint("Month:\t" + m.group(2));
			Output.debugPrint("Day:\t" + m.group(3));
			Output.debugPrint("Hour:\t" + m.group(4));
			Output.debugPrint("Minute:\t" + m.group(5));
			Output.debugPrint("Second:\t" + m.group(6));

			cityInfo.put("year", m.group(1));
			cityInfo.put("month", m.group(2));
			cityInfo.put("monthname", MONTH_NAME_LONG[Integer.parseInt(m.group(2))]);
			cityInfo.put("day", m.group(3));
			cityInfo.put("hour", m.group(4));
			cityInfo.put("minute", m.group(5));
			cityInfo.put("second", m.group(6));

			// Add name for the current day of the week
			int dow = (int) (Float.parseFloat(this.queryValue("day_of_week")));
			cityInfo.put("day_of_week_name", this.queryDayOfWeekLong(dow));

		}

	}

	public String queryValue(String key) {
		return cityInfo.get(key);
	}

	// TODO: Shoudln't need to make another call - pretty up the hashmap values
	public String queryValueAll(String url) {
		String result = null;
		try {
			result = URLOperations.ReadURL(url + ".txt");
		} catch (Exception ex) {
			Output.printColorln(Ansi.Color.RED, "ERROR: Error reading the WorldTimeAPI for detailed data");
		}

		return result;
	}

	public String queryDayOfWeekLong(int i) {
		return DAY_OF_WEEK_LONG[i];
	}

	public String queryDayOfWeekShort(int i) {
		return DAY_OF_WEEK_SHORT[i];
	}

	public String queryMonthNameShort(int i) {
		return MONTH_NAME_LONG[i];
	}

}
