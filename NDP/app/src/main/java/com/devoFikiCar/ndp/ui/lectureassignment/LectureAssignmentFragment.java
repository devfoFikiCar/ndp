package com.devoFikiCar.ndp.ui.lectureassignment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devoFikiCar.ndp.R;

public class LectureAssignmentFragment extends Fragment {

    private LectureAssignmentViewModel mViewModel;

    public static LectureAssignmentFragment newInstance() {
        return new LectureAssignmentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lecture_assignment_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(LectureAssignmentViewModel.class);

        return view;
    }
}