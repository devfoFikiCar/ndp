/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.statistics.specific.student;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.ui.statistics.specific.TaskStatsItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class StatisticsSpecificStudentViewModel extends ViewModel {
    private  MutableLiveData<ArrayList<TaskStatsItem>> taskStatsItemMutableLiveData = new MutableLiveData<>();
    private  MutableLiveData<String> score = new MutableLiveData<>();
    private  MutableLiveData<ArrayList<com.devoFikiCar.ndp.util.Task>> tasks = new MutableLiveData<>();
    private  MutableLiveData<ArrayList<SubmissionCore>> submissions = new MutableLiveData<>();

    public MutableLiveData<ArrayList<TaskStatsItem>> getTaskStatsItemMutableLiveData() {
        return taskStatsItemMutableLiveData;
    }

    public void setTaskStatsItemMutableLiveData(ArrayList<TaskStatsItem> taskStatsItemMutableLiveData) {
        this.taskStatsItemMutableLiveData.postValue(taskStatsItemMutableLiveData);
    }

    public MutableLiveData<String> getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score.postValue(score);
    }

    public MutableLiveData<ArrayList<com.devoFikiCar.ndp.util.Task>> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<com.devoFikiCar.ndp.util.Task> tasks) {
        this.tasks.postValue(tasks);
    }

    public String getTaskExpectedOutput(int position) {
        return tasks.getValue().get(tasks.getValue().size() - position - 1).getOutputTask();
    }

    public MutableLiveData<ArrayList<SubmissionCore>> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(ArrayList<SubmissionCore> submissions) {
        this.submissions.postValue(submissions);
    }

    public void getSubmission(FirebaseFirestore db, int position, String username) {
        String assignmentID = classSave.classes.getAssignmentsIDs().get(position).get("assignmentID");
        String collectionName = username;


        db.collection("assignments").document(assignmentID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                System.out.println(document.getData());

                                long numberOfTasks = (long) document.get("taskNumber");
                                ArrayList<com.devoFikiCar.ndp.util.Task> tmp = new ArrayList<>();
                                for (int i = 0; i < numberOfTasks; i++) {
                                    tmp.add(new com.devoFikiCar.ndp.util.Task((String) document.get("taskNumber" + i)));
                                    System.out.println(tmp.get(i).toString());
                                }
                                setTasks(tmp);

                                db.collection("assignments").document(assignmentID).collection(collectionName)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    String tmp = "";
                                                    int currentTaskNumber = 0;
                                                    ArrayList<TaskStatsItem> arrayList = new ArrayList<>();
                                                    ArrayList<SubmissionCore> tmpSC = new ArrayList<>();

                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        System.out.println(document.getData());

                                                        tmp = (String) document.get("score");
                                                        String[] tmpS = tmp.split("\\|");
                                                        arrayList.add(new TaskStatsItem("Task " + (currentTaskNumber + 1), tmpS[currentTaskNumber]));

                                                        tmpSC.add(new SubmissionCore((String) document.get("code"), (String) document.get("output")));

                                                        currentTaskNumber++;
                                                    }
                                                    setScore(calculateScore(tmp));
                                                    setTaskStatsItemMutableLiveData(arrayList);
                                                    setSubmissions(tmpSC);
                                                } else {
                                                    System.out.println("Error");
                                                }
                                            }
                                        });

                            } else {
                                System.out.println("Error");
                            }
                        } else {
                            System.out.println("Error");
                        }
                    }
                });
    }

    private String calculateScore(String s) {
        String[] split = s.split("\\|");
        double points = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("")) {
                points += Double.valueOf(split[i]);
            }
        }
        return "Score: " + decimalFormat.format(points);
    }

    public void displayDetails(FragmentActivity activity, Context context, int position) {
        SubmissionDialog submissionDialog = new SubmissionDialog();
        Bundle bundle = new Bundle();
        bundle.putString("code", getSubmissions().getValue().get(position).getCode());
        bundle.putString("output", getSubmissions().getValue().get(position).getOutput());
        bundle.putString("expectedOutput", getTaskExpectedOutput(position));
        submissionDialog.setArguments(bundle);
        submissionDialog.show(activity.getSupportFragmentManager(), "test");
    }
}