/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.ui.lectureassignment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.devoFikiCar.ndp.LecturesStudents;
import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.ui.lecture.student.LecturesStudentsFragment;
import com.devoFikiCar.ndp.ui.lecture.teacher.LecturesTeacherFragment;

public class LectureAssignmentFragment extends Fragment {

    private LectureAssignmentViewModel mViewModel;
    private Button btLectures;
    private Button btAssignments;

    public static LectureAssignmentFragment newInstance() {
        return new LectureAssignmentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.lecture_assignment_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(LectureAssignmentViewModel.class);

        btLectures = (Button) root.findViewById(R.id.btLecturesList);
        btAssignments = (Button) root.findViewById(R.id.btAssignmentsList);

        btLectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.getUser().isTeacher()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new LecturesTeacherFragment())
                            .commit();
                } else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new LecturesStudentsFragment())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        btAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.getUser().isTeacher()) {

                } else {

                }
            }
        });

        return root;
    }
}