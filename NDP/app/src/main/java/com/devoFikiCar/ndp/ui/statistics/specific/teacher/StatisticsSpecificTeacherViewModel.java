/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2021
 */

package com.devoFikiCar.ndp.ui.statistics.specific.teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.ui.statistics.specific.TaskStatsItem;
import com.devoFikiCar.ndp.ui.taskList.TaskItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class StatisticsSpecificTeacherViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TaskStatsItem>> taskStatsItemMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> student = new MutableLiveData<>();

    public MutableLiveData<ArrayList<TaskStatsItem>> getTaskStatsItemMutableLiveData() {
        return taskStatsItemMutableLiveData;
    }

    public void setTaskStatsItemMutableLiveData(ArrayList<TaskStatsItem> taskStatsItemMutableLiveData) {
        this.taskStatsItemMutableLiveData.postValue(taskStatsItemMutableLiveData);
    }

    public MutableLiveData<ArrayList<String>> getStudent() {
        return student;
    }

    public void setStudent(ArrayList<String> student) {
        this.student.postValue(student);
    }

    public String getStudentName(int position) {
        return this.student.getValue().get(position);
    }

    public void getSubmission(FirebaseFirestore db, int position) {
        String classId = classSave.classes.getId();
        String assignmentID = classSave.classes.getAssignmentsIDs().get(position).get("assignmentID");

        db.collection("classes").document(classId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println(task.getResult());
                            DocumentSnapshot documentSnapshot = task.getResult();
                            ArrayList<String> tmp = (ArrayList<String>) documentSnapshot.get("students");
                            setStudent(tmp);

                            final DocumentReference docRef = db.collection("assignments").document(assignmentID);
                            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if (error != null) {
                                        System.out.println("Listening failed");
                                        return;
                                    }

                                    if (value != null && value.exists()) {
                                        System.out.println(value.getData());
                                        ArrayList<HashMap<String, String>> scores = (ArrayList<HashMap<String, String>>) value.get("scores");
                                        ArrayList<TaskStatsItem> tmp = new ArrayList<>();
                                        for (int i = 0; i < student.getValue().size(); i++) {
                                            int finalI = i;
                                            final boolean[] hasIt = {false};
                                            scores.forEach((pair) -> {
                                                if (pair.containsKey(student.getValue().get(finalI))) {
                                                    hasIt[0] = true;
                                                }
                                            });
                                            if (hasIt[0]) {
                                                String points = "";
                                                for (int j = 0; j < scores.size(); j++) {
                                                    if (scores.get(j).containsKey(student.getValue().get(i))) {
                                                        points = scores.get(j).get(student.getValue().get(i));
                                                    }
                                                }
                                                tmp.add(new TaskStatsItem(student.getValue().get(i), calculatePoints(points)));
                                            } else {
                                                tmp.add(new TaskStatsItem(student.getValue().get(i), "Did not finish"));
                                            }
                                        }
                                        taskStatsItemMutableLiveData.setValue(tmp);
                                    } else {
                                        System.out.println("Error");
                                    }
                                }
                            });

                        } else {
                            System.out.println("Error");
                        }
                    }
                });
    }

    private String calculatePoints(String s) {
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
}