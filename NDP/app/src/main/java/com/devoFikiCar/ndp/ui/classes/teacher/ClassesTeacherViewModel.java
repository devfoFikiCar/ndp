package com.devoFikiCar.ndp.ui.classes.teacher;

import android.app.AlertDialog;
import android.content.Context;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.User;
import com.devoFikiCar.ndp.helper.userSave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class ClassesTeacherViewModel extends ViewModel {
    private User user;
    private MutableLiveData<ArrayList<HashMap<String, String>>> idTitles = new MutableLiveData<>();

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

                        // TODO: don't over ride
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
                        Toast.makeText(context, "Network error has occurred", Toast.LENGTH_SHORT);
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
}