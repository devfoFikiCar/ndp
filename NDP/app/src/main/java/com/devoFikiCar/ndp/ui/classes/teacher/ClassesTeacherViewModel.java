/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.classes.teacher;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.helper.userSave;
import com.devoFikiCar.ndp.util.Classes;
import com.devoFikiCar.ndp.util.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class ClassesTeacherViewModel extends ViewModel {
    private User user;
    private final MutableLiveData<ArrayList<HashMap<String, String>>> idTitles = new MutableLiveData<>();
    private final MutableLiveData<Integer> change = new MutableLiveData<>();

    public ClassesTeacherViewModel() {
        init();
    }

    private void init() {
        setUser(userSave.user);
        loadClassesOnStart();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = new User(user);
    }

    public void loadClassesOnStart() {
        setIdTitles(this.user.getEnrolledIn());
    }

    public void createClass(FirebaseFirestore db, String title, Context context) {
        AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Creating class")
                .setCancelable(false)
                .build();

        alertDialog.show();

        Map<String, Object> docData = new HashMap<>();
        docData.put("classTitle", title);
        docData.put("schoolCode", this.user.getSchoolCode());
        docData.put("teacher", this.user.getFullName());
        docData.put("students", Collections.emptyList());
        docData.put("lectures", Collections.emptyList());
        docData.put("assignments", Collections.emptyList());

        db.collection("classes")
                .add(docData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("DONE");
                        user.addEnrolledIn(documentReference.getId(), title);
                        userSave.user = new User(user);

                        Map<String, Object> newEnroll = new HashMap<>();
                        newEnroll.put("enrolledIn", Arrays.asList(user.getEnrolledIn()));
                        db.document("users/" + user.getId())
                                .update("enrolledIn", user.getEnrolledIn());

                        alertDialog.dismiss();
                        System.out.println(user.toString());
                        setIdTitles(user.getEnrolledIn());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("ERROR");
                        Toast.makeText(context, "Greska!", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
    }

    public MutableLiveData<ArrayList<HashMap<String, String>>> getIdTitles() {
        if (idTitles == null) {
            return new MutableLiveData<>();
        }
        return idTitles;
    }

    public void setIdTitles(ArrayList<HashMap<String, String>> idTitles) {
        this.idTitles.postValue(idTitles);
    }

    public void loadClass(FirebaseFirestore db, int position, Context context) {
        AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Loading data")
                .setCancelable(false)
                .build();

        alertDialog.show();

        DocumentReference docRef = db.collection("classes").document(getIdTitles().getValue().get(position).get("classID"));

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null) {
                        classSave.classes = new Classes((ArrayList<HashMap<String, String>>) documentSnapshot.get("lectures"), (ArrayList<HashMap<String, String>>) documentSnapshot.get("assignments"));
                        classSave.classes.setId(getIdTitles().getValue().get(position).get("classID"));
                        alertDialog.dismiss();
                        System.out.println(classSave.classes.toString());
                        setChange();
                    } else {
                        Toast.makeText(context, "Greska!", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                } else {
                    Toast.makeText(context, "Greska!", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
    }

    public MutableLiveData<Integer> getChange() {
        return change;
    }

    public void setChange() {
        int c = 0;
        if (getChange().getValue() != null) {
            c = getChange().getValue();
        }
        this.change.postValue(c++);
    }
}