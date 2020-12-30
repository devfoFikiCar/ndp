/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.ui.taskList;

import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.helper.tempStorage;
import com.devoFikiCar.ndp.util.Classes;
import com.devoFikiCar.ndp.util.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class TaskListStudentViewModel extends ViewModel {
    private Classes classes;
    private MutableLiveData<ArrayList<Task>> tasks = new MutableLiveData<>();

    public TaskListStudentViewModel() {
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

    public MutableLiveData<ArrayList<Task>> getTasks() {
        if (tasks == null) {
            return new MutableLiveData<>();
        }
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks.postValue(tasks);
    }

    public void loadTasks(FirebaseFirestore db, Context context, int assignmentPosition) {
        AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Loading tasks")
                .setCancelable(false)
                .build();

        alertDialog.show();

        String assignmentID = this.classes.getAssignmentsIDs().get(assignmentPosition).get("assignmentID");
        System.out.println(assignmentID);

        DocumentReference docRef = db.collection("assignments").document(assignmentID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println(document.getData());
                        long numberOfTasks = (long) document.get("taskNumber");
                        ArrayList<Task> tmp = new ArrayList<>();

                        for (int i = 0; i < numberOfTasks; i++) {
                            tmp.add(new Task((String) document.get("taskNumber" + i)));
                            System.out.println(tmp.get(i).toString());
                        }

                        setTasks(tmp);
                        tempStorage.tasks = new ArrayList<>(tmp);
                    } else {
                        System.out.println("ERROR");
                    }
                    alertDialog.dismiss();
                } else {
                    System.out.println("ERROR");
                    alertDialog.dismiss();
                }
            }
        });
    }
}