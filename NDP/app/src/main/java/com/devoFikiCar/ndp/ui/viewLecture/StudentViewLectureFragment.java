/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.ui.viewLecture;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devoFikiCar.ndp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import br.tiagohm.markdownview.MarkdownView;

public class StudentViewLectureFragment extends Fragment {

    private StudentViewLectureViewModel mViewModel;
    private TextView tvTitle;
    private MarkdownView markdownView;
    private FirebaseFirestore firestore;

    public static StudentViewLectureFragment newInstance() {
        return new StudentViewLectureFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.student_view_lecture_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(StudentViewLectureViewModel.class);

        Bundle bundle = this.getArguments();
        int classPosition = (int) bundle.get("position");

        tvTitle = (TextView) root.findViewById(R.id.tvLectureTitleView);
        markdownView = (MarkdownView) root.findViewById(R.id.markdown_view);
        firestore = FirebaseFirestore.getInstance();

        mViewModel.loadData(firestore, getContext(), classPosition, getContext());

        mViewModel.getTitle().observe(getViewLifecycleOwner(), title);
        mViewModel.getContent().observe(getViewLifecycleOwner(), content);

        return root;
    }

    final Observer<String> title = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            updateTitle(s);
        }
    };

    final Observer<String> content = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            markdownView.loadMarkdown(s);
        }
    };

    private void updateTitle(String s) {
        tvTitle.setText(s);
    }
}