/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.ui.create.assignment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.brackeys.ui.editorkit.widget.TextProcessor;
import com.devoFikiCar.ndp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import br.tiagohm.markdownview.MarkdownView;

public class CreateAssignmentTeacherFragment extends Fragment {

    private CreateAssignmentTeacherViewModel mViewModel;
    private static final String ASSIGNMENT_TEXT = "Text:\"Here goes task text\"\nExample input:\"Here goes input\"\nExample output:\"\"" +
            "Task input:\"Here goes test input\"\nTask output:\"Here goes expected output to task input\"";
    private TextProcessor etMarkdown;
    private Button btHelp;
    private Button btPreview;
    private Button btDone;
    private FirebaseFirestore firestore;
    private EditText etTitle;

    public static CreateAssignmentTeacherFragment newInstance() {
        return new CreateAssignmentTeacherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.create_assignment_teacher_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(CreateAssignmentTeacherViewModel.class);
        firestore = FirebaseFirestore.getInstance();

        etMarkdown = (TextProcessor) root.findViewById(R.id.etMarkdownAssignment);
        etMarkdown.setTextContent(ASSIGNMENT_TEXT);

        btHelp = (Button) root.findViewById(R.id.btHelpAssignment);
        btPreview = (Button) root.findViewById(R.id.btPreviewAssignment);
        etTitle = (EditText) root.findViewById(R.id.etTitleLectureAssignment);

        btDone = (Button) root.findViewById(R.id.btCreateLectureDoneAssignment);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return root;
    }
}