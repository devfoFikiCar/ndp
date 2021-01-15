/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.assignments.teacher;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.util.Classes;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignmentsTeacherModel extends ViewModel {
    private Classes classes;
    private final MutableLiveData<ArrayList<HashMap<String, String>>> assignmentsIDs = new MutableLiveData<>();

    public AssignmentsTeacherModel() {
        init();
    }

    private void init() {
        setClasses();
        loadAssignmentsOnStart();
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses() {
        this.classes = new Classes(classSave.classes);
        System.out.println(this.classes.toString());
    }

    private void loadAssignmentsOnStart() {
        setAssignmentsIDs(this.classes.getAssignmentsIDs());
    }

    public MutableLiveData<ArrayList<HashMap<String, String>>> getAssignmentsIDs() {
        if (assignmentsIDs == null) {
            return new MutableLiveData<>();
        }
        return assignmentsIDs;
    }

    public void setAssignmentsIDs(ArrayList<HashMap<String, String>> assignmentsIDs) {
        this.assignmentsIDs.postValue(assignmentsIDs);
    }

    public void assignmentUpdate(FirebaseFirestore db) {
        final DocumentReference docRef = db.collection("classes").document(this.classes.getId());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    System.out.println("Error");
                    return;
                }

                if (value != null && value.exists()) {
                    System.out.println(value.getData());
                    classSave.classes = new Classes((ArrayList<HashMap<String, String>>) value.get("lectures"), (ArrayList<HashMap<String, String>>) value.get("assignments"));
                    classSave.classes.setId(classes.getId());
                    setClasses();
                    loadAssignmentsOnStart();
                } else {
                    System.out.println("null");
                }
            }
        });
    }
}