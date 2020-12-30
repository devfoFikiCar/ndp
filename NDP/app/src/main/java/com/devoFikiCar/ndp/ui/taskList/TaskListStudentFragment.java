/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.ui.taskList;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devoFikiCar.ndp.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class TaskListStudentFragment extends Fragment {

    private TaskListStudentViewModel mViewModel;
    private FirebaseFirestore firestore;

    public static TaskListStudentFragment newInstance() {
        return new TaskListStudentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_list_student_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(TaskListStudentViewModel.class);
        firestore = FirebaseFirestore.getInstance();

        Bundle bundle = this.getArguments();
        int assignmentPosition = (int) bundle.get("position");

        mViewModel.loadTasks(firestore, getContext(), assignmentPosition);

        return root;
    }
}