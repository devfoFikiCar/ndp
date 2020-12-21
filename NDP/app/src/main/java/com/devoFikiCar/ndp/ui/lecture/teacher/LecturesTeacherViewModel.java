/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.ui.lecture.teacher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

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

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;
import dmax.dialog.SpotsDialog;

public class LecturesTeacherViewModel extends ViewModel {
    private Classes classes;
    private MutableLiveData<ArrayList<HashMap<String, String>>> lectureIDs = new MutableLiveData<>();
    private MutableLiveData<String> content = new MutableLiveData<>();

    public LecturesTeacherViewModel() {
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

    public void displayPreview(FirebaseFirestore db, int position, Context context, Context activity) {
        AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Loading lecture")
                .setCancelable(false)
                .build();

        alertDialog.show();

        final DocumentReference docRef = db.collection("lectures").document(classes.getLectureIDs().get(position).get("lectureID"));
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    System.out.println("Listening failed.");
                    alertDialog.dismiss();
                    return;
                }

                if (value != null && value.exists()) {
                    System.out.println(value.getData());
                    setContent((String) value.get("lectureText"));
                } else {
                    System.out.println("null");
                }

                alertDialog.dismiss();
            }
        });
    }

    public MutableLiveData<String> getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content.postValue(content);
    }
}