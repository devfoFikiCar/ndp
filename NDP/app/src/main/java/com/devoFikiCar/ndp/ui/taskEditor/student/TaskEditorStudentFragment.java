/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2021
 */

package com.devoFikiCar.ndp.ui.taskEditor.student;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.ui.playgroundl.LanguageAdapter;
import com.devoFikiCar.ndp.ui.playgroundl.LanguageItem;

import java.util.ArrayList;

public class TaskEditorStudentFragment extends Fragment {

    private TaskEditorStudentViewModel mViewModel;
    private Spinner spLanguages;
    private ArrayList<LanguageItem> languageItems;
    private LanguageAdapter languageAdapter;

    public static TaskEditorStudentFragment newInstance() {
        return new TaskEditorStudentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_editor_student_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(TaskEditorStudentViewModel.class);

        initList();
        spLanguages = (Spinner) root.findViewById(R.id.spLanguagesT);
        languageAdapter = new LanguageAdapter(getContext(), languageItems);
        spLanguages.setAdapter(languageAdapter);
        spLanguages.setSelection(0);

        return root;
    }

    private void initList() {
        languageItems = new ArrayList<>();
        languageItems.add(new LanguageItem("Fclang", R.drawable.ic_fclang));
        languageItems.add(new LanguageItem("Python", R.drawable.ic_python));
        languageItems.add(new LanguageItem("Java", R.drawable.ic_java));
    }
}