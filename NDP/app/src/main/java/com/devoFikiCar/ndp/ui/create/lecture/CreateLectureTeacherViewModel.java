/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 *
 */

package com.devoFikiCar.ndp.ui.create.lecture;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.util.Classes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class CreateLectureTeacherViewModel extends ViewModel {
    Classes classes;

    public CreateLectureTeacherViewModel() {
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

    public void createLecture(FirebaseFirestore db, Context context, String title, String lecture, FragmentActivity activity) {
        AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Creating lecture")
                .setCancelable(false)
                .build();

        alertDialog.show();

        Map<String, Object> docData = new HashMap<>();
        docData.put("lectureTitle", title);
        docData.put("lectureText", lecture);

        db.collection("lectures")
                .add(docData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("DONE");
                        classes.addLectureID(documentReference.getId(), title);
                        classSave.classes = new Classes(classes);

                        db.collection("classes").document(classes.getId())
                                .update("lectures", classes.getLectureIDs());

                        alertDialog.dismiss();
                        System.out.println(classes.toString());

                        activity.getSupportFragmentManager().popBackStack();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("ERROR");
                        Toast.makeText(context, "Network error has occurred", Toast.LENGTH_SHORT);
                        alertDialog.dismiss();
                    }
                });
    }
}