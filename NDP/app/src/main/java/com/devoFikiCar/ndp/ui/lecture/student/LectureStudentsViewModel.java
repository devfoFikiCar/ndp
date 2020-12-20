/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 *
 */

package com.devoFikiCar.ndp.ui.lecture.student;

import android.util.Log;

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

public class LectureStudentsViewModel extends ViewModel {
    private Classes classes;
    private MutableLiveData<ArrayList<HashMap<String, String>>> lectureIDs = new MutableLiveData<>();

    public LectureStudentsViewModel() {
        init();
    }

    private void init() {
        setClasses();
        loadLecturesOnStart();
    }

    public void lectureUpdate(FirebaseFirestore db) {
        final DocumentReference docRef = db.collection("classes").document(this.classes.getId());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    System.out.println("Listening failed.");
                    return;
                }

                if (value != null && value.exists()) {
                    System.out.println(value.getData());
                    classSave.classes = new Classes((ArrayList<HashMap<String, String>>) value.get("lectures"), (ArrayList<HashMap<String, String>>) value.get("assignments"));
                    classSave.classes.setId(classes.getId());
                    setClasses();
                    loadLecturesOnStart();
                } else {
                    System.out.println("null");
                }
            }
        });
    }

    private void loadLecturesOnStart() {
        setLectureIDs(this.classes.getLectureIDs());
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses() {
        this.classes = new Classes(classSave.classes);
        System.out.println(this.classes.toString());
    }

    public MutableLiveData<ArrayList<HashMap<String, String>>> getLectureIDs() {
        if (lectureIDs == null) {
            return new MutableLiveData<>();
        }
        return lectureIDs;
    }

    public void setLectureIDs(ArrayList<HashMap<String, String>> lectureIDs) {
        this.lectureIDs.postValue(lectureIDs);
    }
}