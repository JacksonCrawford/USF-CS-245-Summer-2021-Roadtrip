# Roadtrip.java
***
*The instructions state to do a pdf or text file. I have it stored as a markdown for GitHub's README for easy viewing, but have included a pdf copy as well :)*

## Class overview

### Roadtrip

This class reads the given csv files, gets the user input, and passes it all to the classes below that do the actual calculations in the main method.

### RouteFinder

This class does all the heavy lifting. It takes departure, destination, and relevant list from the csv files as input in order to perform the calculations

### TownNode

This is the node that I used to store data on each individual town. That data consists of the town name, miles to get there, minutes to get there, all relative to path (type: TownNode).

### AttrNode

This is a simpler version of the TownNode that stores locational data about information taken from the attractions.csv file.

### ArrayList

This is my implementation of a Java ArrayList (without an iterator).

### List

Interface for ArrayList methods.


#### Notes

I have noticed that this implementation does sometimes result in an infinite loop with some combinations. I can't seem to figure out what is causing the bug, but please let me know if you see it :)