/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2021
 */

package com.devoFikiCar.ndp.ui.statistics.specific.student;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.helper.userSave;
import com.devoFikiCar.ndp.ui.statistics.specific.TaskStatsItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class StatisticsSpecificStudentViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TaskStatsItem>> taskStatsItemMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<TaskStatsItem>> getTaskStatsItemMutableLiveData() {
        return taskStatsItemMutableLiveData;
    }

    public void setTaskStatsItemMutableLiveData(ArrayList<TaskStatsItem> taskStatsItemMutableLiveData) {
        this.taskStatsItemMutableLiveData.postValue(taskStatsItemMutableLiveData);
    }

    public void getSubmission(FirebaseFirestore db, int position) {
        String assignmentID = classSave.classes.getAssignmentsIDs().get(position).get("assignmentID");
        String collectionName = userSave.user.getUsername();

        db.collection("assignments").document(assignmentID).collection(collectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getData());
                            }
                        } else {
                            System.out.println("Error");
                        }
                    }
                });
    }
}