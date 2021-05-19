// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 9
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;

import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.awt.Color;

/**
 * AirPollutionAnalyser analyses hourly data about PM2.5 concentration of five
 * cities in China in October, November and December in 2015.
 * Each line of the file "pollution.txt" has information about the pollution
 * level and weather in a city at a particular hour and date.
 * Data is from https://archive.ics.uci.edu/ml/datasets.php.
 * <p>
 * Core: two methods:
 * loadData
 * Loads the data from a file into a field containing an ArrayList of
 * AirPollution objects.
 * Hint: read all the lines from the file, and then take each line
 * apart into the values to pass to the AirPollution constructor.
 * findHazardousLevels
 * Prints out all the records in the ArrayList that have a
 * PM2.5 concentration 300 and over.
 * Hint: see the methods in the AirPollution class, especially getPM and toString
 * <p>
 * Completion: one method:
 * findContrastingCities
 * Compares each record in the list with every other record and finds
 * every pair of cities that having a difference of PM2.5 concentration
 * larger than 400, at the same hour on the same day.
 * For each pair, it should print cityA, cityB, hour, date, difference
 * <p>
 * Challenge: two methods
 * findDailyAverage(String city)
 * -Prints the average daily PM2.5 value for each day for the given city.
 * -Finds the longest sequence of days there the average PM2.5 is always
 * above 200 ("Very unhealthy").
 * Hint: Use an array where the index corresponds to the day of the year.
 * plotPollutionLevels
 * Makes a line plot for the daily average PM2.5 concentration data of
 * the five cities in the same figure. You may choose different colours
 * to represent different cities.
 * Hint: Make the findDailyAverage(String city) method return the array
 * for a given city.
 */

public class AirPollutionAnalyser {

    private ArrayList<AirPollution> pollutions = new ArrayList<AirPollution>();


    /**
     * CORE
     * <p>
     * Load data from the data file into the pollutions field:
     * clear the pollutions field.
     * Read lines from file
     * For each line, use Scanner to break up each line and make an AirPollution
     * adding it to the pollutions field.
     */
    public void loadData() {
        String fileName = "pollution.txt";
        File file = new File(fileName);
        pollutions.clear();
        //# YOUR CODE HERE
        try {
            long count = Files.lines(Path.of(fileName)).count();
            Scanner scan = new Scanner(Path.of(fileName));
            for (int i = 0; i < count; i++) {
                pollutions.add(new AirPollution(scan.next(), scan.next(), scan.nextInt(), scan.nextDouble(), scan.nextDouble(), scan.nextDouble()));
            }
            scan.close();
        } catch (IOException e) {
            UI.println("File reading failed: " + e);
        }
        UI.printf("Loaded %d pollution information into list\n", this.pollutions.size());
        UI.println("----------------------------");
    }


    /**
     * CORE
     * <p>
     * Prints out all the records in the ArrayList that have a PM2.5 concentration
     * 300 and over
     */
    public void findHazardousLevels() {
        UI.clearText();
        UI.println("PM2.5 Concentration 300 and above:");
        /*# YOUR CODE HERE */
        for (AirPollution e : pollutions) {
            if (e.getPM() >= 300) {
                UI.println(e);
            }
        }
        UI.println("------------------------");
    }

    /**
     * COMPLETION
     * <p>
     * Finds every pair of cities that have at the same hour on the same day
     * a difference of PM2.5 concentration larger than 400.
     * You need to compare each record in the list with every other record
     * For each pair, it should print
     * cityA, cityB, hour, date, difference
     */
    public void findContrastingCities() {
        UI.clearText();
        UI.println("Pairs of cities whose PM2.5 differs by more than 400 at the same time");
        /*# YOUR CODE HERE */
        for (AirPollution e : pollutions) {
            for (AirPollution i : pollutions) {
                if ((!e.getCity().equals(i.getCity())) && (e.getDate().equals(i.getDate())) && (e.getHour() == i.getHour()) && (e.diffPM(i) >= 400) ) {

                    UI.println("City A: " + e.getCity() + " City B: " + i.getCity() + " Hour: " + e.getHour() + " Date: " + e.getDate() + " Difference: " + e.diffPM(i));
                }
            }
        }


        UI.println("----------------------------");
    }


    // ------------------ Set up the GUI (buttons) ------------------------

    /**
     * Make buttons to let the user run the methods
     */
    public void setupGUI() {
        UI.initialise();
        UI.addButton("Load", this::loadData);
        UI.addButton("Hazardous Levels", this::findHazardousLevels);
        UI.addButton("Contrasting Cities", this::findContrastingCities);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0); // text pane only
    }

    public static void main(String[] arguments) {
        AirPollutionAnalyser obj = new AirPollutionAnalyser();
        obj.setupGUI();
    }

}
