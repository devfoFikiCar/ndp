package com.devoFikiCar.ndp.ui.classes.student;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.User;
import com.devoFikiCar.ndp.helper.userSave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class ClassesStudentViewModel extends ViewModel {
    private User user;
    private MutableLiveData<ArrayList<HashMap<String, String>>> idTitles = new MutableLiveData<>();

    public ClassesStudentViewModel() {
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

    private void loadClassesOnStart() {
        setIdTitles(this.user.getEnrolledIn());
    }


    public void joinClass(FirebaseFirestore db, String id, Context context) {
        AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Joining class")
                .setCancelable(false)
                .build();

        alertDialog.show();

        DocumentReference docRef = db.collection("classes").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println(document.getData());
                        user.addEnrolledIn(id, document.get("classTitle").toString());
                        userSave.user = new User(user);

                        Map<String, Object> newEnroll = new HashMap<>();
                        newEnroll.put("enrolledIn", Arrays.asList(user.getEnrolledIn()));
                        db.document("users/" + user.getId())
                                .update("enrolledIn", user.getEnrolledIn());

                        ArrayList<String> students = new ArrayList<>();
                        students = new ArrayList((ArrayList<String>) document.get("students"));
                        students.add(user.getUsername());
                        db.collection("classes").document(id)
                                .update("students", students);

                        alertDialog.dismiss();
                        System.out.println(user.toString());
                        setIdTitles(user.getEnrolledIn());
                    } else {
                        System.out.println("Wrong id");
                        Toast.makeText(context, "Wrong id", Toast.LENGTH_SHORT);
                        alertDialog.dismiss();
                    }
                } else {
                    System.out.println("ERROR");
                    Toast.makeText(context, "Network error has occurred", Toast.LENGTH_SHORT);
                    alertDialog.dismiss();
                }
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