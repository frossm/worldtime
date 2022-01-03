# WorldTime

*The console based tool to display times around the world*

<img align="right" width="300" src="https://github.com/frossm/worldtime/blob/master/graphics/WorldTimeLogo500.png">Worldtime is a simple command line tool to display the current local times in various locations around the globe.  There are a lot of very nice web-based tools to do this, and much nicer than this little program, but I wanted something simple that can be executed quickly on the command line.  It also needed to be able to store the areas/cities that I use most frequently and handle the various daylight saving time implementations that exist

Overall, I'm happy with this little tool and not sure what updates or enhancements I'd make, but if anyone has ideas, please post them on the GitHub discussion board and I'll do my best to implement.

## Program Setup
If you are using this as the worldtime.jar file, then you'll need to have Java installed.  WorldTime is written in Java so it can run on most operating systems.  To execute the program, use the following command from the console:

    java -jar worldtime.jar [options] [area/city] [area/city] [...]

If you have saved favorites, you don't need any area/city items, and I don't use any options with normal use.  I've also aliased this so that 
**worldtime** will run the above.  Some shells will even allow you to define ".jar" as an executable extension.

Please note that is nothing to install.  Everything is contained in executable JAR file.  To remove WorldTime, just delete the file.  Simple.

If you are using this as a snap, then you do not have to have java installed as it will be contained in the snap package.  To install WorldTime, you'll simply need to execute:

    snap install worldtime
    
Then to execute WorldTime:

    worldtime [options] [area/city] [area/city] [...]

You should not need any special snap permissions to run, although without assigning the snap more file system access permissions, you may only be able to export your favorites to your home directory.  I'll need to look into this.

## Program Options

#### General Command Line Options
|Option|Description|
|------|-----------|
|-D|Start in Debug Mode.  Not really useful to an end user, but if you are curious what's going on behind the scenes...|
|-h <br>-?|Display the program help information|
|-v|Show the program version and also check GitHub for the latest released version.  If you are using a SNAP, then you are most likely on the latest version and perhaps newer than the GitHub release|

#### Managing Favorites
|Option|Description|
|------|-----------|
|-a|Add the AREA/CITY(s) provided on the command line to the favorites.  These are stored in the Java preferences system and the location varies by OS|
|-e|Erase all saved favorites|
|-i FILE|Import a new set of favorites from an external file.  Existing favorites will be replaced by the imported set.  The format is simply one AREA/CITY per line|
|-l|List the current saved favorites|
|-r AREA/CITY|Remove the AREA/CITY on the command line from the favorites.  Currently only the first AREA/CITY entered will be removed.  This will be updated later to remove all AREA/CITY items on the command line|
|-s|Show all supported AREA/CITY combinations.  This is very important as you'll probably start with `-s` to determine which ones you'd like to add to your favorites|
|-x FILE|Export the current favorites to the file provided.  The format is simply one AREA/CITY per line|

#### Managing Display Options
|Option|Description|
|------|-----------|
|-2|Show time display in 24 hour format instead of the default 12 hour method|
|-d|Show detailed time information for each city provided and/or are in your favorites|
|-z|By default, the output of WorldTime used color.  When run with the `-z` switch, colorized output is disabled. Please note that some options are processed in the order they are entered and may contain color output.  Therefore, to ensure no color is output, please make the `-z` the first option|


### SNAP Details

[![worldtime](https://snapcraft.io//quoter/badge.svg)](https://snapcraft.io/worldtime)

I would encourage anyone with a supported Linux platform to install WorldTime as a snap.  See [Snapcraft Homepage](https://snapcraft.io) for more information. You can download, install, and keep the application up to date automatically by installing the snap via :

`sudo snap install worldtime`  (Assuming snap is installed.  Ubuntu and most of it's derivatives have it installed by default)

This will install the application into a sandbox where it is separate from other applications.  Java is even included in the SNAP package so you don't even have to have it elsewhere.

[![Get it from the Snap Store](https://snapcraft.io/static/images/badges/en/snap-store-black.svg)](https://snapcraft.io/worldtime)

## Feedback

This is obviously a very simple tool that I needed for myself and wanted to make it available to anyone who might find it useful.  If you have thoughts or suggestions, please add a discussion item to the GitHub WorldTime discussion forum.

worldtime at fross dot org.

## License

[The MIT License](https://opensource.org/licenses/MIT)

Copyright 2011-2022 by Michael Fross

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


