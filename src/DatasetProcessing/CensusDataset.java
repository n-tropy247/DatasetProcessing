/*
 * Copyright (C) 2018 Ryan Castelli
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package DatasetProcessing;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import openCSV.CSVReader;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * A class to sort through a large CSV file
 *
 * @author: NTropy
 * @version: 11/17/2017
 */
public class CensusDataset {

    static Scanner kbReader = new Scanner(System.in);

    static int userInput = 1;
    static int countCalls = 0;

    /**
     *
     * @throws IOException
     */
    public static void main() throws IOException {
        FileWriter file1 = new FileWriter("AdultsReport.txt");
        PrintWriter output = new PrintWriter(file1);

        String fileName = "adult.csv";

        CSVReader reader = new CSVReader(new FileReader(fileName));

        List<String[]> list = reader.readAll();

        String[][] dataArr = new String[list.size()][];
        dataArr = list.toArray(dataArr);

        int rows = dataArr.length; //how many rows
        int adultCount = dataArr.length - 1;

        Adult adults[] = new Adult[rows - 1];

        for (int i = 0; i < adultCount; i++) {
            adults[i] = new Adult(Integer.parseInt(dataArr[i + 1][0]), Integer.parseInt(dataArr[i + 1][1]),
                    dataArr[i + 1][2], Double.parseDouble(dataArr[i + 1][3]), dataArr[i + 1][4], Integer.parseInt(dataArr[i + 1][5]),
                    dataArr[i + 1][6], dataArr[i + 1][7], dataArr[i + 1][8], dataArr[i + 1][9], dataArr[i + 1][10],
                    Double.parseDouble(dataArr[i + 1][11]), Double.parseDouble(dataArr[i + 1][12]), Double.parseDouble(dataArr[i + 1][13]),
                    dataArr[i + 1][14], dataArr[i + 1][15]);
        }

        while (userInput != 0) {
            System.out.println("Make your selection from the choices below:\n");
            System.out.println(" 1. Show all adults");
            System.out.println(" 2. Sort by Gender");
            System.out.println(" 3. Sort by Country");
            System.out.println(" 4. Sort by Race");
            System.out.println(" 5. Sort by Capital Gain");
            System.out.println(" 6. Sort by Capital Loss");
            System.out.println(" 7. Selection Sort by Years of Education");
            System.out.println(" 8. Bubble Sort by Years of Education");
            System.out.println(" 9. Insertion Sort by Age");
            System.out.println(" 10. Sort by Occupation");
            System.out.println(" 11. Show Years Education and Occupation");
            System.out.println(" 12. Generate a Report");
            System.out.println(" 13. Search Age");
            System.out.println(" 14. Search Years of Education");
            System.out.println(" 15. Binary Search for Capital Gain");
            System.out.println(" 16. Quick Sort by Age");
            System.out.println(" 20. Clear Terminal");
            System.out.println(" 0. Quit");

            //this is where the user puts in their selection/input
            userInput = 100;
            try {
                userInput = kbReader.nextInt();
            } catch (InputMismatchException ime) {
            }

            //this is the guts of the program
            switch (userInput) {
                case 0: //Quit
                    break;

                case 1: //summary
                    for (Adult adult : adults) {
                        System.out.println(adult.getSummary());
                    }

                case 2: //Gender sort
                    Arrays.sort(adults, Adult.AdultGenderComparator);
                    break;

                case 3: //hours sort
                    Arrays.sort(adults, Adult.AdultCountryComparator);
                    break;

                case 4:
                    Arrays.sort(adults, Adult.AdultRaceComparator);
                    break;

                case 5:
                    Arrays.sort(adults, Adult.AdultGainComparator);
                    break;

                case 6:
                    Arrays.sort(adults, Adult.AdultLossComparator);
                    break;

                case 7:
                    selectionSortYearsEducation(adults);
                    break;

                case 8:
                    bubbleSortYearsEducation(adults);
                    break;

                case 9:
                    insertionSortAge(adults);
                    break;

                case 10:
                    Arrays.sort(adults, Adult.AdultOccComparator); //Arrays.sort(cities, City.CityNameComparator);
                    break;

                case 11:
                    for (Adult adult : adults) {
                        System.out.println(adult.eduNumSummary());
                    }
                    break;

                case 12:
                    for (Adult adult : adults) {
                        output.println(adult.getSummary());
                    }
                    break;

                case 13:
                    System.out.println("What age do you want to search for?: ");
                    int ageSearch = 0;
                    try {
                        ageSearch = kbReader.nextInt();
                    } catch (InputMismatchException ime) {
                        throw new IOException("Could not get int from input", ime);
                    }

                    for (Adult adult : adults) {
                        if (adult.getAge() == ageSearch) {
                            System.out.println(adult.getSummary());
                        }
                    }
                    break;

                case 14:
                    System.out.println("How many years of education do you want to search for?: ");
                    int eduNumSearch = 0;
                    try {
                        eduNumSearch = kbReader.nextInt();
                    } catch (InputMismatchException ime) {
                        throw new IOException("Could not get int from input", ime);
                    }
                    for (int i = 0; i < adults.length; i++) {
                        if (adults[i].getEducationNum() == eduNumSearch) {
                            System.out.println(adults[i].getSummary());
                        }
                    }
                    break;

                case 15:
                    System.out.println("What Capital Gain are you looking for?");
                    try {
                        userInput = kbReader.nextInt();
                    } catch (InputMismatchException ime) {
                        throw new IOException("Could not get int from input", ime);
                    }
                    binarySearchCapitalGain(adults, userInput, 0, adults.length - 1);
                    break;

                case 16:
                    long t1 = System.nanoTime();
                    quickSortAge(adults, 0, adults.length - 1);
                    long t2 = System.nanoTime();
                    System.out.println("QuickSort ran in " + (t2 - t1) / 10E9 + " seconds.");
                    break;

                case 20:
                    System.out.println('\f');
                    break;

                case 100:
                    //nothing
                    break;

                default:
                    System.out.println("Incorrect Option. Please try again!");

            }

        }
        System.out.println("Have a nice day!");
    }

    public static int selectionSortYearsEducation(Adult adultsToSort[]) {

        Adult temp;
        int minIndex;
        int comparisons = 0;
        for (int i = 0; i < adultsToSort.length; ++i) {
            temp = adultsToSort[i];
            minIndex = i;
            for (int j = i + 1; j < adultsToSort.length; ++j) // Find minimum
            {
                if (adultsToSort[j].getEducationNum() < temp.getEducationNum()) //salient feature
                {
                    temp = adultsToSort[j];
                    minIndex = j;
                }
                comparisons++;
            }
            adultsToSort[minIndex] = adultsToSort[i]; // swap
            adultsToSort[i] = temp;

        }
        return comparisons;
    }

    public static Long bubbleSortYearsEducation(Adult adultsToSort[]) //Bubble Sort
    {
        long comparisons = 0l;
        boolean loopSomeMore;
        do {
            loopSomeMore = false;
            for (int j = 0; j < adultsToSort.length - 1; j++) {
                if (adultsToSort[j].getEducationNum() > adultsToSort[j + 1].getEducationNum()) {
                    //swap a[j] and a[j+1]
                    Adult temp = adultsToSort[j];
                    adultsToSort[j] = adultsToSort[j + 1];
                    adultsToSort[j + 1] = temp;
                    loopSomeMore = true;
                }
                comparisons++;

            }
        } while (loopSomeMore);

        return comparisons;
    }

    public static Long insertionSortAge(Adult adultsToSort[]) { //This will do an ascending sort
        Adult adultToInsert;
        int j;
        Long comparisons = 0l;
        boolean keepGoing;
        //On kth pass, insert item k into its correct position among the first k items in the array
        for (int k = 1; k < adultsToSort.length; k++) {
            //Go backwards through the list, looking for the slot to insert a[k]
            adultToInsert = adultsToSort[k];
            j = k - 1;
            keepGoing = true;
            while ((j >= 0) && keepGoing) {
                if (adultToInsert.getAge() < adultsToSort[j].getAge()) {
                    adultsToSort[j + 1] = adultsToSort[j]; //Salient feature
                    j--;
                    if (j == -1) //special case for inserting an item at [0]
                    {
                        adultsToSort[0] = adultToInsert;
                    }
                } else //Upon leaving loop, j + 1 is the index where itemToInsert belongs
                {
                    keepGoing = false;
                    adultsToSort[j + 1] = adultToInsert;
                }
                comparisons++;
            }
        }
        return comparisons;
    }

    public static String binarySearchCapitalGain(Adult adultsToSort[], double srchVal, int lb, int ub) //recursive
    {
        if (lb > ub) {
            return "Error";
        } else {
            int mid = (lb + ub) / 2;
            if (adultsToSort[mid].getCapitalGain() == srchVal) {
                while (adultsToSort[mid].getCapitalGain() == srchVal) {
                    mid--;
                }
                mid++;
                while (adultsToSort[mid].getCapitalGain() == srchVal) {
                    System.out.println(adultsToSort[mid].getSummary());
                    mid++;
                }
            } else if (srchVal > adultsToSort[mid].getCapitalGain()) {
                return binarySearchCapitalGain(adultsToSort, srchVal, mid + 1, ub);
            } else {
                return binarySearchCapitalGain(adultsToSort, srchVal, lb, mid - 1);
            }
        }
        return null;
    }

    public static int quickSortPartition(Adult arr[], int low, int high) {
        double pivot = arr[high].getAge();
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++) {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j].getAge() <= pivot) {
                i++;
                // swap arr[i] and arr[j]
                Adult temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        Adult temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public static void quickSortAge(Adult arr[], int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is 
            now at right place */
            int pi = quickSortPartition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            quickSortAge(arr, low, pi - 1);
            quickSortAge(arr, pi + 1, high);
        }
    }
}
