/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2021
 */

package com.devoFikiCar.ndp.ui.taskList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.api.RetrieveOutput;
import com.devoFikiCar.ndp.api.SubmitCode;
import com.devoFikiCar.ndp.async.AsyncTask;
import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.helper.tempStorage;
import com.devoFikiCar.ndp.helper.userSave;
import com.devoFikiCar.ndp.util.Classes;
import com.devoFikiCar.ndp.util.Task;
import com.devoFikiCar.ndp.util.User;
import com.google.android.gms.common.api.Batch;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class TaskListStudentViewModel extends ViewModel {
    private Classes classes;
    private User user;
    private MutableLiveData<ArrayList<Task>> tasks = new MutableLiveData<>();
    private AlertDialog alertDialog;
    private Context context;
    private FirebaseFirestore db;
    private FragmentActivity activity;

    public TaskListStudentViewModel() {
        init();
    }

    private void init() {
        setClasses();
        setUser();
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses() {
        this.classes = new Classes(classSave.classes);
    }

    public User getUser() {
        return user;
    }

    public void setUser() {
        this.user = new User(userSave.user);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public MutableLiveData<ArrayList<Task>> getTasks() {
        if (tasks == null) {
            return new MutableLiveData<>();
        }
        return tasks;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks.postValue(tasks);
    }

    public void loadTasks(FirebaseFirestore db, Context context, int assignmentPosition) {
        AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Loading tasks")
                .setCancelable(false)
                .build();

        alertDialog.show();

        String assignmentID = this.classes.getAssignmentsIDs().get(assignmentPosition).get("assignmentID");
        System.out.println(assignmentID);

        DocumentReference docRef = db.collection("assignments").document(assignmentID);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println(document.getData());
                        long numberOfTasks = (long) document.get("taskNumber");
                        ArrayList<Task> tmp = new ArrayList<>();

                        for (int i = 0; i < numberOfTasks; i++) {
                            tmp.add(new Task((String) document.get("taskNumber" + i)));
                            System.out.println(tmp.get(i).toString());
                        }

                        setTasks(tmp);
                        tempStorage.tasks = new ArrayList<>(tmp);
                    } else {
                        System.out.println("ERROR");
                    }
                    alertDialog.dismiss();
                } else {
                    System.out.println("ERROR");
                    alertDialog.dismiss();
                }
            }
        });
    }

    public void submit(FirebaseFirestore db, int numberOfTasks, Context context, FragmentActivity activity) {
        setContext(context);
        setDb(db);
        setActivity(activity);
        String[] input = new String[2 * tempStorage.tasks.size()];
        for (int i = 0; i < tempStorage.tasks.size(); i++) {
            input[i * 2] = tempStorage.solutions.get(i);
            input[i * 2 + 1] = tempStorage.tasks.get(i).getInputTask();
        }
        APITask apiTask = new APITask();
        apiTask.execute(input);
    }

    public class APITask extends AsyncTask<String[], Integer, String[]> {
        @Override
        protected void onPreExecute() {
            alertDialog = new SpotsDialog.Builder()
                    .setContext(context)
                    .setMessage("Submitting code")
                    .setCancelable(false)
                    .build();
            alertDialog.show();
        }

        @Override
        protected String[] doInBackground(String[] input) throws Exception {
            String[] out = new String[tempStorage.tasks.size()];
            for (int i = 0; i < tempStorage.tasks.size(); i++) {
                String token = SubmitCode.requestToken(input[i * 2], tempStorage.LANGUAGE_ID, input[i * 2 + 1]);
                System.out.println(token);
                out[i] = RetrieveOutput.getOutput(token);
            }
            return out;
        }

        @Override
        protected void onPostExecute(String[] output) {
            System.out.println("POST EXECUTE");

            alertDialog.setTitle("Calculating score");
            double[] results = new double[output.length];
            for (int i = 0; i < output.length; i++) {
                results[i] = StringSimilarityApproximation.similarity(tempStorage.tasks.get(i).getOutputTask(), output[i]) * 100;
            }

            StringBuilder points = new StringBuilder();
            for (double d : results) {
                points.append(d);
                points.append("|");
            }
            String pointsToDB = points.toString();

            alertDialog.setTitle("Saving submissions and scores");

            // todo
            //  1. get current array of scores ( try not to overlap with other user)                DONE
            //  2. add your score                                                                   DONE
            //  3. update object                                                                    DONE
            //  4. make sub-collections
            //  5. add submissions and outputs to it
            //  6. clean UI and memory

            // 1
            String assignmentID = classes.getAssignmentsIDs().get(tempStorage.assignmentPosition).get("assignmentID");

            DocumentReference docRef = db.collection("assignments").document(assignmentID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot != null) {
                            ArrayList<HashMap<String, String>> scores = (ArrayList<HashMap<String, String>>) documentSnapshot.get("scores");

                            // 2
                            scores.add(new HashMap<>());
                            scores.get(scores.size() - 1).put(user.getUsername(), pointsToDB);

                            // 3
                            db.collection("assignments").document(assignmentID)
                                    .update("scores", scores);

                            // 4 and 5
                            WriteBatch batch = db.batch();
                            for (int i = 0; i < tempStorage.tasks.size(); i++) {
                                System.out.println("here");
                                Map<String, Object> docData = new HashMap<>();
                                docData.put("code", tempStorage.solutions.get(i));
                                docData.put("output", output[i]);
                                docData.put("score", pointsToDB);

                                DocumentReference documentReference = db.collection("assignment").document(assignmentID)
                                        .collection(user.getUsername()).document();

                                batch.set(documentReference, docData);
                            }

                            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // 6
                                        tempStorage.solutions = Arrays.asList(new String[10000]);
                                        alertDialog.dismiss();
                                        activity.getSupportFragmentManager().popBackStack();
                                    } else {
                                        alertDialog.dismiss();
                                        System.out.println("ERROR");
                                        Toast.makeText(context, "Error has occurred try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            alertDialog.dismiss();
                            System.out.println("ERROR");
                            Toast.makeText(context, "Error has occurred try again!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        alertDialog.dismiss();
                        System.out.println("ERROR");
                        Toast.makeText(context, "Error has occurred try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        protected void onBackgroundError(Exception e) {
            e.printStackTrace();
        }
    }
}