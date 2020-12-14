package com.devoFikiCar.ndp.ui.classes.teacher;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devoFikiCar.ndp.R;

public class ClassesTeacherFragment extends Fragment {

    private MainViewModel mViewModel;

    public static ClassesTeacherFragment newInstance() {
        return new ClassesTeacherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.classes_teacher_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        return root;
    }
}