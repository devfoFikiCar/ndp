/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.devoFikiCar.ndp.LogIn;
import com.devoFikiCar.ndp.MainActivity;
import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.helper.userSave;
import com.devoFikiCar.ndp.util.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class LogInFragment extends Fragment {

    private static final String TAG = LogIn.class.getSimpleName();
    private LogInViewModel mViewModel;
    private Button btLogIn;
    private EditText etUser;
    private EditText etPassword;
    private CheckBox cbTeacher;
    private TextView tvRegister;
    private FirebaseFirestore firestore;
    private AlertDialog alertDialog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static LogInFragment newInstance() {
        return new LogInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(LogInViewModel.class);

        btLogIn = root.findViewById(R.id.btLogIn);
        etUser = root.findViewById(R.id.etUsername);
        etPassword = root.findViewById(R.id.etPassword);
        cbTeacher = root.findViewById(R.id.cbTeacher);
        tvRegister = root.findViewById(R.id.tvPlayInPlaygroundNL);
        firestore = FirebaseFirestore.getInstance();

        preferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        if (preferences.getBoolean("saved", false)) {
            etUser.setText(preferences.getString("username", ""));
            etPassword.setText(preferences.getString("password", ""));
            cbTeacher.setChecked(preferences.getBoolean("teacher", false));
        }

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.register_dialog, null);

                AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle)
                        .setView(view)
                        .setTitle("Registracija")
                        .setPositiveButton("Registruj", null)
                        .setNegativeButton("Cancel", null)
                        .create();

                dialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimary);

                dialog.show();

                EditText etFullName = view.findViewById(R.id.etFullNameR);
                EditText etUsername = view.findViewById(R.id.etUsernameR);
                EditText etPassword1 = view.findViewById(R.id.etPasswordR);
                EditText etConfirmPassword = view.findViewById(R.id.etConfirmPasswordR);
                EditText etSchoolCode = view.findViewById(R.id.etSchoolCodeR);
                etSchoolCode.setEnabled(false);
                etSchoolCode.setHintTextColor(getResources().getColor(R.color.colorPrimary, getContext().getTheme()));
                CheckBox cbHasCode = view.findViewById(R.id.cbCodeR);

                cbHasCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        etSchoolCode.setEnabled(isChecked);
                        if (isChecked) {
                            etSchoolCode.setHintTextColor(getResources().getColor(R.color.userInput, getContext().getTheme()));
                        } else {
                            etSchoolCode.setHintTextColor(getResources().getColor(R.color.colorPrimary, getContext().getTheme()));
                        }
                    }
                });

                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.custom_button_login));

                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.custom_button_login));
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etFullName.getText().toString().length() > 3
                            && etPassword1.getText().toString().length() > 5
                            && etPassword1.getText().toString().equals(etConfirmPassword.getText().toString())) {
                            if (cbHasCode.isChecked()) {
                                if (etSchoolCode.getText().length() != 4) {
                                    Toast.makeText(getContext(), "Pogresan format koda.", Toast.LENGTH_SHORT).show();
                                } else {
                                    positiveButton.setEnabled(false);
                                    firestore.collection("users")
                                            .whereEqualTo("teacher", true)
                                            .whereEqualTo("schoolCode", etSchoolCode.getText().toString())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult().isEmpty()) {
                                                            etSchoolCode.setText("");
                                                            etSchoolCode.setHintTextColor(Color.RED);
                                                            Toast.makeText(getContext(), "Kod ne postoji", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            firestore.collection("users")
                                                                    .whereEqualTo("username", etSchoolCode.getText().toString() + etUsername.getText().toString())
                                                                    .get()
                                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                            if (task.isSuccessful()) {
                                                                                if (task.getResult().isEmpty()) {
                                                                                    System.out.println("kik2");
                                                                                    Map<String, Object> docData = new HashMap<>();
                                                                                    docData.put("fullName", etFullName.getText().toString());
                                                                                    docData.put("password", etPassword1.getText().toString());
                                                                                    docData.put("schoolCode", etSchoolCode.getText().toString());
                                                                                    docData.put("teacher", false);
                                                                                    docData.put("username", etSchoolCode.getText().toString() + etUsername.getText().toString());
                                                                                    docData.put("enrolledIn", new ArrayList<>());

                                                                                    firestore.collection("users")
                                                                                            .add(docData)
                                                                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                                                @Override
                                                                                                public void onSuccess(DocumentReference documentReference) {
                                                                                                    System.out.println("yes boi");
                                                                                                    saveLoginData((String) docData.get("username"), (String) docData.get("password"), false);
                                                                                                    preferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                                                                                                    dialog.dismiss();
                                                                                                }
                                                                                            })
                                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                    dialog.dismiss();
                                                                                                    Toast.makeText(getContext(), "Greska!", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            });
                                                                                } else {
                                                                                    etUsername.setText("");
                                                                                    etUsername.setHintTextColor(Color.RED);
                                                                                    Toast.makeText(getContext(), "Username je vec uzet", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            } else {
                                                                                System.out.println("ERROR");
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    } else {
                                                        System.out.println("ERROR");
                                                    }
                                                }
                                            });
                                }
                            } else {
                                String fullUser = "DNHU" + etUsername.getText().toString();
                                positiveButton.setEnabled(false);
                                firestore.collection("users")
                                        .whereEqualTo("username", fullUser)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.getResult().isEmpty()) {
                                                        System.out.println("kik2");
                                                        Map<String, Object> docData = new HashMap<>();
                                                        docData.put("fullName", etFullName.getText().toString());
                                                        docData.put("password", etPassword1.getText().toString());
                                                        docData.put("schoolCode", "DNHU");
                                                        docData.put("teacher", false);
                                                        docData.put("username", fullUser);
                                                        docData.put("enrolledIn", new ArrayList<>());

                                                        firestore.collection("users")
                                                                .add(docData)
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentReference documentReference) {
                                                                        System.out.println("yes boi");
                                                                        saveLoginData((String) docData.get("username"), (String) docData.get("password"), false);
                                                                        dialog.dismiss();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        dialog.dismiss();
                                                                        Toast.makeText(getContext(), "Greska!", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    } else {
                                                        etUsername.setText("");
                                                        etUsername.setHintTextColor(Color.RED);
                                                        Toast.makeText(getContext(), "Username je vec uzet", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    System.out.println("ERROR");
                                                }
                                            }
                                        });
                                positiveButton.setEnabled(true);
                            }
                        } else {
                            etFullName.setText("");
                            etFullName.setHintTextColor(Color.RED);
                            etUsername.setText("");
                            etUsername.setHintTextColor(Color.RED);
                            etPassword1.setText("");
                            etPassword1.setHintTextColor(Color.RED);
                            etConfirmPassword.setText("");
                            etConfirmPassword.setHintTextColor(Color.RED);
                        }
                    }
                });
            }
        });

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
                    wrongUserPassword();
                }

                if (etUser.getText().length() >= 4) {
                    flagUser = true;
                    Log.i(TAG, "Correct user format");
                } else {
                    flagUser = false;
                    Log.e(TAG, "Incorrect user format");
                    wrongUserPassword();
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
                                            Toast.makeText(getContext(), "Pogresne informacije", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.i(TAG, "Correct credentials");
                                            System.out.println(mViewModel.getUser().toString());
                                            userSave.user = new User(mViewModel.getUser());
                                            alertDialog.dismiss();

                                            saveLoginData(etUser.getText().toString(), etPassword.getText().toString(), cbTeacher.isChecked());

                                            Intent intent = new Intent(view.getContext(), MainActivity.class);
                                            root.getContext().startActivity(intent);
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                        alertDialog.dismiss();
                                        Log.e(TAG, "Wrong credentials");
                                        wrongUserPassword();
                                        Toast.makeText(getContext(), "Pogresne informacije", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Log.e(TAG, "Wrong input format");
                    Toast.makeText(getContext(), "Pogresne informacije", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    private void wrongPassword() {
        etPassword.setText("");
        etPassword.setHintTextColor(Color.RED);
    }

    private void wrongUser() {
        etUser.setText("");
        etUser.setHintTextColor(Color.RED);
    }

    private void wrongUserPassword() {
        wrongPassword();
        wrongUser();
    }

    private void saveLoginData(String username, String password, boolean teacher) {
        preferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = preferences.edit();

        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("teacher", teacher);
        editor.putBoolean("saved", true);
        editor.commit();

        if (preferences.getBoolean("saved", false)) {
            etUser.setText(preferences.getString("username", ""));
            etPassword.setText(preferences.getString("password", ""));
            cbTeacher.setChecked(preferences.getBoolean("teacher", false));
        }
    }
}