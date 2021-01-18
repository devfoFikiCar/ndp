/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.lectureassignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.ui.assignments.student.AssignmentsStudentFragment;
import com.devoFikiCar.ndp.ui.assignments.teacher.AssignmentsTeacherFragment;
import com.devoFikiCar.ndp.ui.lecture.student.LecturesStudentsFragment;
import com.devoFikiCar.ndp.ui.lecture.teacher.LecturesTeacherFragment;
import com.devoFikiCar.ndp.ui.statistics.overall.student.StatisticsStudentOverallFragment;
import com.devoFikiCar.ndp.ui.statistics.overall.teacher.StatisticsTeacherOverallFragment;

public class LectureAssignmentFragment extends Fragment {

    private LectureAssignmentViewModel mViewModel;

    public static LectureAssignmentFragment newInstance() {
        return new LectureAssignmentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.lecture_assignment_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(LectureAssignmentViewModel.class);

        setLecturesButton(root);
        setAssignmentsButton(root);
        setStatsButton(root);

        return root;
    }

    private void setStatsButton(View root) {
        Button btStats = root.findViewById(R.id.btStats);
        btStats.setOnClickListener(v -> {
            if (mViewModel.getUser().isTeacher()) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new StatisticsTeacherOverallFragment())
                        .addToBackStack("LectureAssignmentFragment")
                        .commit();
            } else {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new StatisticsStudentOverallFragment())
                        .addToBackStack("LectureAssignmentFragment")
                        .commit();
            }
        });
    }

    private void setAssignmentsButton(View root) {
        Button btAssignments = root.findViewById(R.id.btAssignmentsList);
        btAssignments.setOnClickListener(v -> {
            if (mViewModel.getUser().isTeacher()) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AssignmentsTeacherFragment())
                        .addToBackStack("LectureAssignmentFragment")
                        .commit();
            } else {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AssignmentsStudentFragment())
                        .addToBackStack("LectureAssignmentFragment")
                        .commit();
            }
        });
    }

    private void setLecturesButton(View root) {
        Button btLectures = root.findViewById(R.id.btLecturesList);
        btLectures.setOnClickListener(v -> {
            if (mViewModel.getUser().isTeacher()) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new LecturesTeacherFragment())
                        .addToBackStack("LectureAssignmentFragment")
                        .commit();
            } else {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new LecturesStudentsFragment())
                        .addToBackStack("LectureAssignmentFragment")
                        .commit();
            }
        });
    }
}