/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.statistics.overall.teacher;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.ui.statistics.overall.UserStatsItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StatisticsTeacherOverallViewModel extends ViewModel {
    private MutableLiveData<ArrayList<UserStatsItem>> userStatsItemMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<UserStatsItem>> getUserStatsItemMutableLiveData() {
        return userStatsItemMutableLiveData;
    }

    public void setUserStatsItemMutableLiveData(ArrayList<UserStatsItem> userStatsItemMutableLiveData) {
        this.userStatsItemMutableLiveData.postValue(userStatsItemMutableLiveData);
    }

    public void listStudents(FirebaseFirestore db, Context context) {
        String classID = classSave.classes.getId();

        db.collection("classes").document(classID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println(task.getResult());
                            DocumentSnapshot documentSnapshot = task.getResult();
                            ArrayList<String> students = (ArrayList<String>) documentSnapshot.get("students");
                            ArrayList<UserStatsItem> tmp = new ArrayList<>();
                            for (int i = 0; i < students.size(); i++) {
                                tmp.add(new UserStatsItem(students.get(i), "Za vise imformacija klikni."));
                            }
                            setUserStatsItemMutableLiveData(tmp);
                        } else {
                            System.out.println("Error.");
                        }
                    }
                });
    }
}