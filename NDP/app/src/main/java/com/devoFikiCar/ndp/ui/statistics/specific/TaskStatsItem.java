/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.statistics.specific;

public class TaskStatsItem {
    private final String taskNumber;
    private final String score;

    public TaskStatsItem(String taskNumber, String score) {
        this.taskNumber = taskNumber;
        this.score = score;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public String getScore() {
        return score;
    }
}
