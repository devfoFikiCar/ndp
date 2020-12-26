/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.util;

public class AssignmentSection {
    private String text;
    private String inputExample;
    private String outputExample;
    private String inputTask;
    private String outputTask;

    public AssignmentSection(String text, String inputExample, String outputExample, String inputTask, String outputTask) {
        this.text = text;
        this.inputExample = inputExample;
        this.outputExample = outputExample;
        this.inputTask = inputTask;
        this.outputTask = outputTask;
    }

    public AssignmentSection() {
    }

    public AssignmentSection(AssignmentSection assignmentSection) {
        this.text = assignmentSection.text;
        this.inputExample = assignmentSection.inputExample;
        this.outputExample = assignmentSection.outputExample;
        this.inputTask = assignmentSection.inputTask;
        this.outputTask = assignmentSection.outputTask;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getInputExample() {
        return inputExample;
    }

    public void setInputExample(String inputExample) {
        this.inputExample = inputExample;
    }

    public String getOutputExample() {
        return outputExample;
    }

    public void setOutputExample(String outputExample) {
        this.outputExample = outputExample;
    }

    public String getInputTask() {
        return inputTask;
    }

    public void setInputTask(String inputTask) {
        this.inputTask = inputTask;
    }

    public String getOutputTask() {
        return outputTask;
    }

    public void setOutputTask(String outputTask) {
        this.outputTask = outputTask;
    }

    @Override
    public String toString() {
        return "AssignmentSection{" +
                "text='" + text + '\'' +
                ", inputExample='" + inputExample + '\'' +
                ", outputExample='" + outputExample + '\'' +
                ", inputTask='" + inputTask + '\'' +
                ", outputTask='" + outputTask + '\'' +
                '}';
    }
}
