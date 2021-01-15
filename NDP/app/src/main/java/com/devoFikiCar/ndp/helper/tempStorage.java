/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.helper;

import com.devoFikiCar.ndp.util.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class tempStorage {
    public static ArrayList<Task> tasks = new ArrayList<>();
    public static List<String> solutions = Arrays.asList(new String[10000]);
    public static int LANGUAGE_ID = 0;
    public static int assignmentPosition = 0;
    public static Calendar tempCalendarEnd = Calendar.getInstance();
}
