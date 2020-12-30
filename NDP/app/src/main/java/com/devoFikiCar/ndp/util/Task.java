/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.util;

public class Task {
    private String text;
    private String inputExample;
    private String outputExample;
    private String inputTask;
    private String outputTask;
    private static final String CODE = "FIKICARCODE";

    public Task(String s) {
        String[] parts = s.split(CODE);
        this.text = parts[1];
        this.inputExample = parts[2];
        this.outputExample = parts[3];
        this.inputTask = parts[4];
        this.outputTask = parts[5];
    }

    public Task(String text, String inputExample, String outputExample, String inputTask, String outputTask) {
        this.text = text;
        this.inputExample = inputExample;
        this.outputExample = outputExample;
        this.inputTask = inputTask;
        this.outputTask = outputTask;
    }

    public Task(Task task) {
        this.text = task.text;
        this.inputExample = task.inputExample;
        this.outputExample = task.outputExample;
        this.inputTask = task.inputTask;
        this.outputTask = task.outputTask;
    }

    public String getText() {
        return text;
    }

    public String getInputExample() {
        return inputExample;
    }

    public String getOutputExample() {
        return outputExample;
    }

    public String getInputTask() {
        return inputTask;
    }

    public String getOutputTask() {
        return outputTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "text='" + text + '\'' +
                ", inputExample='" + inputExample + '\'' +
                ", outputExample='" + outputExample + '\'' +
                ", inputTask='" + inputTask + '\'' +
                ", outputTask='" + outputTask + '\'' +
                '}';
    }
}
