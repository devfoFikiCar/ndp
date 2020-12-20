/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.ui.viewLecture;

import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.util.Classes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import dmax.dialog.SpotsDialog;

public class StudentViewLectureViewModel extends ViewModel {
    private Classes classes;
    private MutableLiveData<String> title = new MutableLiveData<>();
    private MutableLiveData<String> content = new MutableLiveData<>();

    public StudentViewLectureViewModel() {
        init();
    }

    private void init() {
        setClasses();
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses() {
        this.classes = new Classes(classSave.classes);
    }

    public void loadData(FirebaseFirestore db, Context context, int classPosition, Context context1) {
        AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Loading lecture")
                .setCancelable(false)
                .build();

        alertDialog.show();

        String lectureID = this.classes.getLectureIDs().get(classPosition).get("lectureID");

        DocumentReference documentReference = db.collection("lectures").document(lectureID);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println(document.getData());
                        setTitle((String) document.get("lectureTitle"));
                        setContent((String) document.get("lectureText"));
                        alertDialog.dismiss();
                    } else {
                        System.out.println("ERROR");
                        alertDialog.dismiss();
                    }
                } else {
                    System.out.println("ERROR");
                    alertDialog.dismiss();
                }
            }
        });
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.postValue(title);
    }

    public MutableLiveData<String> getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content.postValue(content);
    }
}