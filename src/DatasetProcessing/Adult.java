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

import java.util.Comparator;
import java.util.Random;

/**
 * A class to sort through a large CSV file
 * 
 * @author: NTropy
 * @version: 2.0
 */
public class Adult
{
    Random myGenerator = new Random();

    private final double capitalGain;
    private final double capitalLoss;
    private final double fnlwgt;
    private final double hoursPerWeek;
    
    private final int age;
    private final int educationNum;
    private final int number;
    
    private final String education;
    private final String maritalStatus;
    private final String nativeCountry;
    private final String occupation;
    private final String race;
    private final String relationship;
    private final String sex;
    private final String workclass;
    private final String yearlyIncomeBracket;  

    /**
     * Constructor for each Adult object from the CSV
     * @param num
     * @param a
     * @param w
     * @param f
     * @param edu
     * @param edun
     * @param ms
     * @param occupation
     * @param relationship
     * @param race
     * @param sex
     * @param capitalGain
     * @param fatal
     * @param closs
     * @param slat
     * @param slong 
     */
    public Adult(int num,  int a,   String w,  double f,    String edu,    int edun,    
    String ms,   String occupation,   String relationship,  String race,   
    String sex, double capitalGain,  double fatal, double closs,    
    String slat,  String slong)
    {
        this.number = num;
        this.age = a;
        this.workclass = w;
        this.fnlwgt = f;
        this.education = edu;
        this.educationNum = edun;
        this.maritalStatus = ms;
        this.occupation = occupation;
        this.relationship = relationship;
        this.race = race;
        this.sex = sex;
        this.capitalGain = capitalGain;
        this.capitalLoss = fatal;
        this.hoursPerWeek = closs;
        this.nativeCountry = slat;
        this.yearlyIncomeBracket = slong;
    }

    public int getNumber (){ return number; }

    public int getAge (){ return age; }

    public String getWorkclass (){ return workclass; }

    public double getFnlwgt (){ return fnlwgt; }

    public String getEducation (){ return education; }

    public int getEducationNum (){ return educationNum; }

    public String getMaritalStatus (){ return maritalStatus; }

    public String getOccupation (){ return occupation; }

    public String getRelationship (){ return relationship; }

    public String getRace (){ return race; }

    public String getSex (){ return sex; }

    public double getCapitalGain (){ return capitalGain; }

    public double getCapitalLoss (){ return capitalLoss; }

    public double getHoursPerWeek (){ return hoursPerWeek; }

    public String getNativeCountry (){ return nativeCountry; }

    public String getYearlyIncomeBracket (){ return yearlyIncomeBracket; }

    public String getSummary()
    {
        return("Number: " + getNumber()
            + "\nAge: " + getAge()
            + "\nCapitalGain: " + getCapitalGain()
            + "\nCapitalLoss: " + getCapitalLoss()
            + "\nRace: " + getRace()
            + "\nGender: " + getSex()
            + "\nYears of Education: " + getEducationNum()
            + "\nOccupation: " + getOccupation()
            + "\nNative Country: " + getNativeCountry()
            + "\n");
    }

    public String eduNumSummary() {
        return("Years of Education: " + getEducationNum()
            + "\nOccupation: " + getOccupation()
            + "\n");
    }

    public static Comparator<Adult> AdultCountryComparator = (Adult adult1, Adult adult2) -> {
        String adultCountry1 = adult1.getNativeCountry().toUpperCase();
        String adultCountry2 = adult2.getNativeCountry().toUpperCase();
        
        //ascending order
        return adultCountry1.compareTo(adultCountry2);
    };

    public static Comparator<Adult> AdultGenderComparator = (Adult adult1, Adult adult2) -> {
        String adultGender1 = adult1.getSex().toUpperCase();
        String adultGender2 = adult2.getSex().toUpperCase();
        
        //ascending order
        return adultGender1.compareTo(adultGender2);
    };

    public static Comparator<Adult> AdultRaceComparator = (Adult adult1, Adult adult2) -> {
        String adultRace1 = adult1.getRace().toUpperCase();
        String adultRace2 = adult2.getRace().toUpperCase();
        
        //ascending order
        return adultRace1.compareTo(adultRace2);
    };

    public static Comparator<Adult> AdultGainComparator = (Adult adult1, Adult adult2) -> {
        double adultGain1 = adult1.getCapitalGain();
        double adultGain2 = adult2.getCapitalGain();
        
        //ascending
        if(adultGain1 > adultGain2)
            return 1;
        else if(adultGain1 < adultGain2)
            return -1;
        else if(adultGain1 == adultGain2)
            return 0;
        return 0;
    };

    public static Comparator<Adult> AdultLossComparator = (Adult adult1, Adult adult2) -> {
        double adultLoss1 = adult1.getCapitalLoss();
        double adultLoss2 = adult2.getCapitalLoss();
        
        //ascending
        if(adultLoss1 > adultLoss2)
            return 1;
        else if(adultLoss1 < adultLoss2)
            return -1;
        else if(adultLoss1 == adultLoss2)
            return 0;
        return 0;
    };

    public static Comparator<Adult> AdultAgeComparator = (Adult adult1, Adult adult2) -> {
        double adultAge1 = adult1.getAge();
        double adultAge2 = adult2.getAge();
        
        //ascending
        if(adultAge1 > adultAge2)
            return 1;
        else if(adultAge1 < adultAge2)
            return -1;
        else if(adultAge1 == adultAge2)
            return 0;
        return 0;
    };

    public static Comparator<Adult> AdultEduComparator = (Adult adult1, Adult adult2) -> {
        double adultEdu1 = adult1.getEducationNum();
        double adultEdu2 = adult2.getEducationNum();
        
        //ascending
        if(adultEdu1 > adultEdu2)
            return 1;
        else if(adultEdu1 < adultEdu2)
            return -1;
        else if(adultEdu1 == adultEdu2)
            return 0;
        return 0;
    };

    public static Comparator<Adult> AdultOccComparator = (Adult adult1, Adult adult2) -> {
        String adultOcc1 = adult1.getOccupation().toUpperCase();
        String adultOcc2 = adult2.getOccupation().toUpperCase();
        
        //ascending
        return adultOcc1.compareTo(adultOcc2);
    };
}
