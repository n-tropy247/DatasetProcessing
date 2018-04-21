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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import openCSV.CSVReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.UIManager.LookAndFeelInfo;

import java.util.Arrays;
import java.util.List;

/**
 * A class to sort through a large CSV file
 *
 * @author: NTropy
 * @version: 11/17/2017
 */
public class CensusDataset extends JPanel {

    private static ActionEvent sendOverride;

    private static Adult adults[];

    private static CSVReader reader;

    private static FileWriter file1;

    private static int option;

    private static List<String[]> list;

    private static JButton jbtnSend;

    private static JFrame jfrm;

    private static JTextArea jtaDisplay;

    private static JScrollPane jscrlp;

    private static JTextField jtfInput;

    private static PrintWriter output;

    private static String input;

    private static String[] summaryArr;

    /**
     * Creates JFrame and populate array from CSV
     *
     * @param args
     */
    public static void main(String args[]) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exe) {
            System.err.println(exe);
        }

        option = 0;

        jfrm = new JFrame("Airport Queue");
        jfrm.setLayout(new BorderLayout()); //sets layout based on borders
        jfrm.setSize(700, 600); //sets size

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //gets screen dimensions

        double screenWidth = screenSize.getWidth(); //width of screen
        double screenHeight = screenSize.getHeight(); //height of screen

        jfrm.setLocation((int) screenWidth / 2 - 250, (int) screenHeight / 2 - 210); //sets location of chat to center

        jtaDisplay = new JTextArea(20, 40); //size of display
        jtaDisplay.setEditable(false); //display not editable
        jtaDisplay.setLineWrap(true); //lines wrap down

        jscrlp = new JScrollPane(jtaDisplay); //makes dispaly scrollable

        jtfInput = new JTextField(30); //sets character width of input field

        jbtnSend = new JButton("Send"); //sets button text

        jbtnSend.addActionListener(new handler()); //adds listener to button

        KeyListener key = new handler(); //adds handler for 'enter' key

        jtfInput.addKeyListener(key); //adds keylistener for 'enter'
        jfrm.add(jscrlp, BorderLayout.PAGE_START); //adds scrollable display to main frame

        sendOverride = new ActionEvent(jbtnSend, 1001, "Send"); //allows key to trigger same method as button

        JPanel p1 = new JPanel(); //panel for input/button

        p1.setLayout(new FlowLayout()); //flow layout for input/button
        p1.add(jtfInput, BorderLayout.LINE_END); //adds input to panel
        p1.add(jbtnSend, BorderLayout.LINE_END); //adds button to panel

        jfrm.add(p1, BorderLayout.PAGE_END); //add button/input to main frame

        jfrm.setVisible(true); //makes frame visible

        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //kills application on close

        try {
            file1 = new FileWriter("AdultsReport.txt");
        } catch (IOException e) {
            System.err.println(e);
        }
        output = new PrintWriter(file1);

        String fileName = "adult.csv";

        try {
            reader = new CSVReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }

        try {
            list = reader.readAll();
        } catch (IOException e) {
            System.err.println(e);
        }

        String[][] dataArr = new String[list.size()][];
        dataArr = list.toArray(dataArr);

        int rows = dataArr.length; //how many rows
        int adultCount = dataArr.length - 1;

        adults = new Adult[rows - 1];

        for (int i = 0; i < adultCount; i++) {
            adults[i] = new Adult(Integer.parseInt(dataArr[i + 1][0]), Integer.parseInt(dataArr[i + 1][1]),
                    dataArr[i + 1][2], Double.parseDouble(dataArr[i + 1][3]), dataArr[i + 1][4], Integer.parseInt(dataArr[i + 1][5]),
                    dataArr[i + 1][6], dataArr[i + 1][7], dataArr[i + 1][8], dataArr[i + 1][9], dataArr[i + 1][10],
                    Double.parseDouble(dataArr[i + 1][11]), Double.parseDouble(dataArr[i + 1][12]), Double.parseDouble(dataArr[i + 1][13]),
                    dataArr[i + 1][14], dataArr[i + 1][15]);
        }

        summaryArr = new String[rows - 1];

        int iter = 0;
        for (Adult adult : adults) {
            summaryArr[iter] = adult.getSummary();
            iter++;
        }

        jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                + "\n2. Sort by Gender"
                + "\n3. Sort by Country"
                + "\n4. Sort by Race"
                + "\n5. Sort by Capital Gain"
                + "\n6. Sort by Capital Loss"
                + "\n7. Selection Sort by Years of Education"
                + "\n8. Bubble Sort by Years of Education"
                + "\n9. Insertion Sort by Age"
                + "\n10. Sort by Occupation"
                + "\n11. Show Years Education and Occupation"
                + "\n12. Generate a Report"
                + "\n13. Search Age"
                + "\n14. Search Years of Education"
                + "\n15. Binary Search for Capital Gain"
                + "\n16. Quick Sort by Age");
    }

    /**
     * Selection sort by years education
     *
     * @param adultsToSort
     */
    private static void selectionSortYearsEducation(Adult adultsToSort[]) {

        Adult temp;
        int minIndex;
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
            }
            adultsToSort[minIndex] = adultsToSort[i]; // swap
            adultsToSort[i] = temp;

        }
    }

    /**
     * Bubble sort by years of education
     *
     * @param adultsToSort
     */
    private static void bubbleSortYearsEducation(Adult adultsToSort[]) //Bubble Sort
    {
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

            }
        } while (loopSomeMore);
    }

    /**
     * Insertion sort by age
     *
     * @param adultsToSort
     */
    private static void insertionSortAge(Adult adultsToSort[]) { //This will do an ascending sort
        Adult adultToInsert;
        int j;
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
            }
        }
    }

    /**
     * Binary search by capital gain
     *
     * @param adultsToSort
     * @param srchVal
     * @param lb
     * @param ub
     * @return results of search
     */
    private static String binarySearchCapitalGain(Adult adultsToSort[], double srchVal, int lb, int ub) //recursive
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
                    jtaDisplay.setText(jtaDisplay.getText() + "\n" + adultsToSort[mid].getSummary());
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

    /**
     * Quick sort by age
     *
     * @param arr
     * @param low
     * @param high
     */
    private static void quickSortAge(Adult arr[], int low, int high) {
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

    /**
     * Quick sort partitions for quick sort
     *
     * @param arr
     * @param low
     * @param high
     * @return midpoint for next partition
     */
    private static int quickSortPartition(Adult arr[], int low, int high) {
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

    /**
     * Handles input and output to JFrame
     */
    private static class handler implements ActionListener, KeyListener {

        /**
         * On button press prompt GUI or input
         *
         * @param ae
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (ae.getActionCommand().equals("Send")) {
                input = jtfInput.getText();
                jtaDisplay.setText(jtaDisplay.getText() + "\nYou: " + input);
                switch (option) {
                    case 0:
                        if (!input.equals("")) {
                            switch (Integer.valueOf(input)) {
                                case 1: //summary
                                    for (String summary : summaryArr) {
                                        jtaDisplay.setText(jtaDisplay.getText() + "\n" + summary);
                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            System.err.println(e);
                                        }
                                        jfrm.repaint();
                                    }
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;
                                case 2: //Gender sort
                                    Arrays.sort(adults, Adult.AdultGenderComparator);
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;
                                case 3: //hours sort
                                    Arrays.sort(adults, Adult.AdultCountryComparator);
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;
                                case 4:
                                    Arrays.sort(adults, Adult.AdultRaceComparator);
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;

                                case 5:
                                    Arrays.sort(adults, Adult.AdultGainComparator);
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;

                                case 6:
                                    Arrays.sort(adults, Adult.AdultLossComparator);
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;

                                case 7:
                                    selectionSortYearsEducation(adults);
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;

                                case 8:
                                    bubbleSortYearsEducation(adults);
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;

                                case 9:
                                    insertionSortAge(adults);
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;

                                case 10:
                                    Arrays.sort(adults, Adult.AdultOccComparator);
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;

                                case 11:
                                    for (Adult adult : adults) {
                                        jtaDisplay.setText(jtaDisplay.getText() + "\n" + adult.eduNumSummary());
                                    }
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;
                                case 12:
                                    for (Adult adult : adults) {
                                        output.println(adult.getSummary());
                                    }
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;
                                case 13:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nWhat age do you want to search for?: ");
                                    option = 1;
                                    break;

                                case 14:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nHow many years of education do you want to search for?: ");
                                    option = 2;
                                    break;

                                case 15:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nWhat Capital Gain are you looking for?");
                                    option = 3;
                                    break;

                                case 16:
                                    long t1 = System.nanoTime();
                                    quickSortAge(adults, 0, adults.length - 1);
                                    long t2 = System.nanoTime();
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nQuickSort ran in " + (t2 - t1) / 10E9 + " seconds.");
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;
                                default:
                                    jtaDisplay.setText(jtaDisplay.getText() + "\n\"" + input + "\" is not recognized as a valid input.");
                                    jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                            + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                            + "\n2. Sort by Gender"
                                            + "\n3. Sort by Country"
                                            + "\n4. Sort by Race"
                                            + "\n5. Sort by Capital Gain"
                                            + "\n6. Sort by Capital Loss"
                                            + "\n7. Selection Sort by Years of Education"
                                            + "\n8. Bubble Sort by Years of Education"
                                            + "\n9. Insertion Sort by Age"
                                            + "\n10. Sort by Occupation"
                                            + "\n11. Show Years Education and Occupation"
                                            + "\n12. Generate a Report"
                                            + "\n13. Search Age"
                                            + "\n14. Search Years of Education"
                                            + "\n15. Binary Search for Capital Gain"
                                            + "\n16. Quick Sort by Age");
                                    break;
                            }
                        }

                        break;
                    case 1:
                        for (Adult adult : adults) {
                            if (adult.getAge() == Integer.valueOf(input)) {
                                jtaDisplay.setText(jtaDisplay.getText() + "\n" + adult.getSummary());
                            }
                        }
                        jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                + "\n2. Sort by Gender"
                                + "\n3. Sort by Country"
                                + "\n4. Sort by Race"
                                + "\n5. Sort by Capital Gain"
                                + "\n6. Sort by Capital Loss"
                                + "\n7. Selection Sort by Years of Education"
                                + "\n8. Bubble Sort by Years of Education"
                                + "\n9. Insertion Sort by Age"
                                + "\n10. Sort by Occupation"
                                + "\n11. Show Years Education and Occupation"
                                + "\n12. Generate a Report"
                                + "\n13. Search Age"
                                + "\n14. Search Years of Education"
                                + "\n15. Binary Search for Capital Gain"
                                + "\n16. Quick Sort by Age");
                        option = 0;
                        break;
                    case 2:
                        for (Adult adult : adults) {
                            if (adult.getEducationNum() == Integer.valueOf(input)) {
                                jtaDisplay.setText(jtaDisplay.getText() + "\n" + adult.getSummary());
                            }
                        }
                        jtaDisplay.setText(jtaDisplay.getText() + "\nMake your selection from the choices below:"
                                + "\n1. Show all adults (MAY TAKE A LONG TIME!)"
                                + "\n2. Sort by Gender"
                                + "\n3. Sort by Country"
                                + "\n4. Sort by Race"
                                + "\n5. Sort by Capital Gain"
                                + "\n6. Sort by Capital Loss"
                                + "\n7. Selection Sort by Years of Education"
                                + "\n8. Bubble Sort by Years of Education"
                                + "\n9. Insertion Sort by Age"
                                + "\n10. Sort by Occupation"
                                + "\n11. Show Years Education and Occupation"
                                + "\n12. Generate a Report"
                                + "\n13. Search Age"
                                + "\n14. Search Years of Education"
                                + "\n15. Binary Search for Capital Gain"
                                + "\n16. Quick Sort by Age");
                        option = 0;
                        break;
                    case 3:
                        binarySearchCapitalGain(adults, Integer.valueOf(input), 0, adults.length - 1);
                        break;
                }
            }
            jtfInput.setText("");
            jfrm.repaint();
        }

        /**
         * Enter key counts as send button
         *
         * @param ke
         */
        @Override
        public void keyPressed(KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                actionPerformed(sendOverride);
            }
        }

        /**
         * Necessary override
         *
         * @param ke
         */
        @Override
        public void keyTyped(KeyEvent ke) {
        }

        /**
         * Necessary override
         *
         * @param ke
         */
        @Override
        public void keyReleased(KeyEvent ke) {
        }
    }
}
