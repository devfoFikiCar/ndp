/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.assignments;

public class AssignmentItem {
    private final String assignmentTitle;
    private final String assignmentID;

    public AssignmentItem(String assignmentTitle, String assignmentID) {
        this.assignmentTitle = assignmentTitle;
        this.assignmentID = "Kod testa: " + assignmentID;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public String getAssignmentID() {
        return assignmentID;
    }
}
