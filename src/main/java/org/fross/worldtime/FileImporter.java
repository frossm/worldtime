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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.fross.library.Output;

public class FileImporter {
	public static ArrayList<String> readFavorites(String fileName) {
		BufferedReader reader;
		ArrayList<String> favorites = new ArrayList<String>();

		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();

			Output.debugPrintln("Importing favorites from file");
			while (line != null) {
				// Skip anything without a slash in it else add it to the return array list
				if (line.isBlank()) {
					Output.debugPrintln("  - Skiping blank line");
				} else {
					Output.debugPrintln("  - " + line);
					favorites.add(line);
				}
				// Read the next line from the input file
				line = reader.readLine();
			}

			reader.close();

		} catch (IOException ex) {
			Output.fatalError("Could not read input file: '" + fileName + "'", 7);
		}

		return favorites;
	}
}