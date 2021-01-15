/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.statistics.overall;

public class UserStatsItem {
    private final String assignmentTitle;

    public UserStatsItem(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }
}
