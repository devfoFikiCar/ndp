/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 *
 */

package com.devoFikiCar.ndp.util;

import java.util.ArrayList;
import java.util.HashMap;

public class Classes {
    private String id;
    private ArrayList<HashMap<String, String>> lectureIDs;
    private ArrayList<HashMap<String, String>> assignmentsIDs;

    public Classes() {
        lectureIDs = new ArrayList<>();
        assignmentsIDs = new ArrayList<>();
    }

    public Classes(ArrayList<HashMap<String, String>> lectureIDs, ArrayList<HashMap<String, String>> assignmentsIDs) {
        this.lectureIDs = new ArrayList(lectureIDs);
        this.assignmentsIDs = new ArrayList(assignmentsIDs);
    }

    public Classes(Classes classes) {
        this.lectureIDs = new ArrayList(classes.lectureIDs);
        this.assignmentsIDs = new ArrayList(classes.assignmentsIDs);
        this.id = classes.id;
    }

    public ArrayList<HashMap<String, String>> getLectureIDs() {
        return lectureIDs;
    }

    public void setLectureIDs(ArrayList<HashMap<String, String>> lectureIDs) {
        this.lectureIDs = lectureIDs;
    }

    public ArrayList<HashMap<String, String>> getAssignmentsIDs() {
        return assignmentsIDs;
    }

    public void setAssignmentsIDs(ArrayList<HashMap<String, String>> assignmentsIDs) {
        this.assignmentsIDs = assignmentsIDs;
    }

    public void addAssignmentID(String id) {
        if (this.assignmentsIDs == null) {
            this.assignmentsIDs = new ArrayList<>();
        }
        assignmentsIDs.add(new HashMap<>());
        assignmentsIDs.get(assignmentsIDs.size() - 1).put("assignmentID", id);
    }

    public void addLectureID(String id, String title) {
        if (this.lectureIDs == null) {
            this.lectureIDs = new ArrayList<>();
        }
        lectureIDs.add(new HashMap<>());
        lectureIDs.get(lectureIDs.size() - 1).put("lectureID", id);
        lectureIDs.get(lectureIDs.size() - 1).put("lectureTitle", title);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "lectureIDs=" + lectureIDs.toString() +
                ", assignmentsIDs=" + assignmentsIDs.toString() +
                '}';
    }
}
