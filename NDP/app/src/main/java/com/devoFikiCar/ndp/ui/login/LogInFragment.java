/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.devoFikiCar.ndp.LogIn;
import com.devoFikiCar.ndp.MainActivity;
import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.util.User;
import com.devoFikiCar.ndp.helper.userSave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;

public class LogInFragment extends Fragment {

    private static final String TAG = LogIn.class.getSimpleName();
    private LogInViewModel mViewModel;
    private Button btLogIn;
    private EditText etUser;
    private EditText etPassword;
    private CheckBox cbTeacher;
    private TextView tvPlayInPlaygroundNL;
    private FirebaseFirestore firestore;
    private AlertDialog alertDialog;

    public static LogInFragment newInstance() {
        return new LogInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(LogInViewModel.class);

        btLogIn = (Button) root.findViewById(R.id.btLogIn);
        etUser = (EditText) root.findViewById(R.id.etUsername);
        etPassword = (EditText) root.findViewById(R.id.etPassword);
        cbTeacher = (CheckBox) root.findViewById(R.id.cbTeacher);
        tvPlayInPlaygroundNL = (TextView) root.findViewById(R.id.tvPlayInPlaygroundNL);
        firestore = FirebaseFirestore.getInstance();

        btLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Login button clicked");
                boolean flagUser = false;
                boolean flagPassword = false;

                if (etPassword.getText().length() >= 4) {
                    flagPassword = true;
                    Log.i(TAG, "Correct password format");
                } else {
                    flagPassword = false;
                    Log.e(TAG, "Incorrect password format");
                    wrongPassword();
                }

                if (etUser.getText().length() >= 4) {
                    flagUser = true;
                    Log.i(TAG, "Correct user format");
                } else {
                    flagUser = false;
                    Log.e(TAG, "Incorrect user format");
                    wrongUser();
                }

                if (flagPassword && flagUser) {
                    alertDialog = new SpotsDialog.Builder()
                            .setContext(getContext())
                            .setMessage("Checking credentials")
                            .setCancelable(false)
                            .build();
                    alertDialog.show();

                    firestore = FirebaseFirestore.getInstance();

                    firestore.collection("users")
                            .whereEqualTo("username", etUser.getText().toString())
                            .whereEqualTo("password", etPassword.getText().toString())
                            .whereEqualTo("teacher", cbTeacher.isChecked())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        boolean flag = true;
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            mViewModel.setUser(new User(document.get("username").toString(), document.get("password").toString(), document.get("fullName").toString(),
                                                    document.get("schoolCode").toString(), (boolean) document.get("teacher"), (ArrayList<HashMap<String, String>>) document.get("enrolledIn")));
                                            mViewModel.getUser().setId(document.getId());
                                            flag = false;
                                        }
                                        if (flag) {
                                            alertDialog.dismiss();
                                            Log.e(TAG, "Wrong credentials");
                                            wrongUserPassword();
                                            Toast.makeText(getContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.i(TAG, "Correct credentials");
                                            System.out.println(mViewModel.getUser().toString());
                                            userSave.user = new User(mViewModel.getUser());
                                            alertDialog.dismiss();
                                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                                            root.getContext().startActivity(intent);
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                        alertDialog.dismiss();
                                        Log.e(TAG, "Wrong credentials");
                                        wrongUserPassword();
                                        Toast.makeText(getContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Log.e(TAG, "Wrong input format");
                    Toast.makeText(getContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void wrongPassword() {
        etPassword.setText("");
        etPassword.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
    }

    private void wrongUser() {
        etUser.setText("");
        etUser.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
    }

    private void wrongUserPassword() {
        wrongPassword();
        wrongUser();
    }
}