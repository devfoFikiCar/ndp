/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.create.assignment;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.util.AssignmentSection;
import com.devoFikiCar.ndp.util.Classes;
import com.devoFikiCar.ndp.util.TimeStorage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class CreateAssignmentTeacherViewModel extends ViewModel {
    Classes classes;

    public CreateAssignmentTeacherViewModel() {
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

    public void createAssignment(FirebaseFirestore db, Context context, String title, String txt, FragmentActivity activity) {
        AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Creating lecture")
                .setCancelable(false)
                .build();

        alertDialog.show();

        ArrayList<AssignmentSection> assignmentSectionArrayList = new ArrayList<>(formatAssignment(txt));
        if (assignmentSectionArrayList.size() == 0) {
            alertDialog.dismiss();
            return;
        }

        Map<String, Object> docData = new HashMap<>();
        docData.put("assignmentTitle", title);
        docData.put("taskNumber", assignmentSectionArrayList.size());
        for (int i = 0; i < assignmentSectionArrayList.size(); i++) {
            docData.put("taskNumber" + i, assignmentSectionArrayList.get(i).toString());
        }
        docData.put("timeStart", TimeStorage.toString1());
        docData.put("timeEnd", TimeStorage.toString2());
        docData.put("scores", new ArrayList<>());

        db.collection("assignments")
                .add(docData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("DONE");
                        classes.addAssignmentID(documentReference.getId(), title);
                        classSave.classes = new Classes(classes);

                        db.collection("classes").document(classes.getId())
                                .update("assignments", classes.getAssignmentsIDs());

                        alertDialog.dismiss();
                        System.out.println(classes.toString());

                        activity.getSupportFragmentManager().popBackStack();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("ERROR");
                        Toast.makeText(context, "Network error has occurred", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
    }

    public ArrayList<AssignmentSection> formatAssignment(String text) {
        ArrayList<AssignmentSection> out = new ArrayList<>();
        String[] sections = text.split("Text");

        try {
            for (int i = 1; i < sections.length; i++) {
                AssignmentSection assignmentSection = new AssignmentSection();
                sections[i] = sections[i].replace("\n", "");
                String[] parts = sections[i].split("\"");
                for (int j = 1; j < parts.length; j += 2) {
                    switch (j) {
                        case 1: {
                            assignmentSection.setText(parts[j]);
                            break;
                        }
                        case 3: {
                            assignmentSection.setInputExample(parts[j]);
                            break;
                        }
                        case 5: {
                            assignmentSection.setOutputExample(parts[j]);
                            break;
                        }
                        case 7: {
                            assignmentSection.setInputTask(parts[j]);
                            break;
                        }
                        case 9: {
                            assignmentSection.setOutputTask(parts[j]);
                            break;
                        }
                    }
                }
                out.add(new AssignmentSection(assignmentSection));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }

        return out;
    }
}