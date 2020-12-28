/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.util;

import androidx.annotation.NonNull;

import java.util.Calendar;

// Do not look at this disgusting code ever again

public class TimeStorage {
    private static int min1 = 0;
    private static int hour1 = 0;
    private static int day1 = 0;
    private static int month1 = 0;
    private static int year1 = 0;

    private static int min2 = 0;
    private static int hour2 = 0;
    private static int day2 = 0;
    private static int month2 = 0;
    private static int year2 = 0;

    public static String toString1() {
        return "" + min1 + ":" + hour1 + " " + day1 + "-" + month1 + "-" + year1;
    }

    public static String toString2() {
        return "" + min2 + ":" + hour2 + " " + day2 + "-" + month2 + "-" + year2;
    }

    public static boolean wrongDate() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, year1);
            calendar.add(Calendar.MONTH, month1);
            calendar.add(Calendar.DAY_OF_MONTH, day1);
            calendar.add(Calendar.MINUTE, min1);
            calendar.add(Calendar.HOUR_OF_DAY, hour1);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.add(Calendar.YEAR, year2);
            calendar1.add(Calendar.MONTH, month2);
            calendar1.add(Calendar.DAY_OF_MONTH, day2);
            calendar1.add(Calendar.MINUTE, min2);
            calendar1.add(Calendar.HOUR_OF_DAY, hour2);

            System.out.println(calendar1.getTimeInMillis());
            System.out.println(calendar.getTimeInMillis());
            return calendar1.getTimeInMillis() <= calendar.getTimeInMillis();
        } catch (Exception ex) {
            ex.printStackTrace();
            return true;
        }
    }

    public static int getMin1() {
        return min1;
    }

    public static void setMin1(int min1) {
        TimeStorage.min1 = min1;
    }

    public static int getHour1() {
        return hour1;
    }

    public static void setHour1(int hour1) {
        TimeStorage.hour1 = hour1;
    }

    public static int getDay1() {
        return day1;
    }

    public static void setDay1(int day1) {
        TimeStorage.day1 = day1;
    }

    public static int getMonth1() {
        return month1;
    }

    public static void setMonth1(int month1) {
        TimeStorage.month1 = month1;
    }

    public static int getYear1() {
        return year1;
    }

    public static void setYear1(int year1) {
        TimeStorage.year1 = year1;
    }

    public static int getMin2() {
        return min2;
    }

    public static void setMin2(int min2) {
        TimeStorage.min2 = min2;
    }

    public static int getHour2() {
        return hour2;
    }

    public static void setHour2(int hour2) {
        TimeStorage.hour2 = hour2;
    }

    public static int getDay2() {
        return day2;
    }

    public static void setDay2(int day2) {
        TimeStorage.day2 = day2;
    }

    public static int getMonth2() {
        return month2;
    }

    public static void setMonth2(int month2) {
        TimeStorage.month2 = month2;
    }

    public static int getYear2() {
        return year2;
    }

    public static void setYear2(int year2) {
        TimeStorage.year2 = year2;
    }
}
