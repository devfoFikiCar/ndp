/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.classes;

public class ClassItem {
    private final String classTitle;
    private final String classID;

    public ClassItem(String classTitle, String classID) {
        this.classTitle = classTitle;
        this.classID = classID;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public String getClassID() {
        return classID;
    }
}
