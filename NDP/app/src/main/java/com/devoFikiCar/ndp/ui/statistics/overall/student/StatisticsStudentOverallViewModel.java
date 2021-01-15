/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.statistics.overall.student;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.ui.statistics.overall.UserStatsItem;
import com.devoFikiCar.ndp.util.Classes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticsStudentOverallViewModel extends ViewModel {
    private Classes classes;
    private MutableLiveData<ArrayList<UserStatsItem>> userStatsItems = new MutableLiveData<>();

    public StatisticsStudentOverallViewModel() {
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

    public MutableLiveData<ArrayList<UserStatsItem>> getUserStatsItems() {
        return userStatsItems;
    }

    public void setUserStatsItems(ArrayList<UserStatsItem> userStatsItems) {
        this.userStatsItems.postValue(userStatsItems);
    }

    public void getAssignmentsWithTotalScore(FirebaseFirestore db, Context context, String username) {
        List<String> ids = new ArrayList<>();

        for (int i = 0; i < this.classes.getAssignmentsIDs().size(); i++) {
            ids.add(this.classes.getAssignmentsIDs().get(i).get("assignmentID"));
        }

        db.collection("assignments").whereIn(FieldPath.documentId(), ids)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<UserStatsItem> arrayList = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getData());

                                ArrayList<HashMap<String, String>> scores = (ArrayList<HashMap<String, String>>) document.get("scores");
                                String title = (String) document.get("assignmentTitle");

                                boolean flag = false;
                                for (int i = 0; i < scores.size(); i++) {
                                    if (scores.get(i).containsKey(username) && !flag) {
                                        arrayList.add(new UserStatsItem(title, calculateScore(scores.get(i).get(username))));
                                        flag = true;
                                    }
                                }
                                if (!flag) {
                                    arrayList.add(new UserStatsItem(title, "Did not finish."));
                                }
                            }

                            setUserStatsItems(arrayList);
                        } else {
                            System.out.println("Error.");
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
}