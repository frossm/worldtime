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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.fross.library.Output;
import org.fusesource.jansi.Ansi;

public class FileExporter {
	File exportFile = null;
	FileWriter exportFileFW = null;

	/**
	 * FileExporter(): FileExporter Constructor
	 * 
	 * @param fileName
	 */
	public FileExporter(String fileName) {
		// Create the export file
		try {
			this.exportFile = new File(fileName);
			if (this.exportFile.createNewFile() == false) {
				Output.fatalError("Could not create export file: '" + fileName + "'", 4);
			}
		} catch (IOException ex) {
			Output.fatalError("Could not create export file: '" + fileName + "'", 4);
		}

		// Create the FileWriter object for writing to the export file
		try {
			exportFileFW = new FileWriter(this.exportFile);
		} catch (IOException ex) {
			Output.printColorln(Ansi.Color.RED, "Error writing to export file '" + fileName + "'\n" + ex.getMessage());
		}
	}

	/**
	 * canWrite(): Return true or false depending on the ability to write to the export file
	 * 
	 * @return
	 */
	public boolean canWrite() {
		try {
			return exportFile.canWrite();
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * close(): Flush and close the export file
	 */
	public void close() {
		try {
			this.exportFileFW.flush();
			this.exportFileFW.close();
		} catch (IOException ex) {
			Output.printColorln(Ansi.Color.RED, "Error closing export file: " + ex.getMessage());
		}

	}

	/**
	 * queryExportFilename(): Return the name of the export file as a string
	 * 
	 * @return
	 */
	public String queryExportFilename() {
		return this.exportFile.getPath();
	}

	/**
	 * exportFavorites(): Write to the export file - one favorite per line
	 * 
	 */
	public void exportFavorites() {
		Output.debugPrint("Exporting Favorites:");
		try {
			for (String fav : Favorites.getFavorites()) {
				Output.debugPrint("   - " + fav);
				exportFileFW.append(fav + "\n");
			}
		} catch (IOException ex) {
			Output.printColorln(Ansi.Color.RED, "Error writing to export file '" + this.queryExportFilename() + "'\n" + ex.getMessage());
		}
	}

}
