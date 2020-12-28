/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.ui.create.assignment;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brackeys.ui.editorkit.theme.EditorTheme;
import com.brackeys.ui.editorkit.widget.TextProcessor;
import com.brackeys.ui.language.markdown.MarkdownLanguage;
import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.util.TimeStorage;
import com.google.firebase.firestore.FirebaseFirestore;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;

public class CreateAssignmentTeacherFragment extends Fragment {

    private CreateAssignmentTeacherViewModel mViewModel;
    private static final String ASSIGNMENT_TEXT = "Text:\"Here goes task text\"\nExample input:\"Here goes input\"\nExample output:\"Example output\"\n" +
            "Task input:\"Here goes test input\"\nTask output:\"Here goes expected output to task input\"";
    private TextProcessor etMarkdown;
    private Button btHelp;
    private Button btPreview;
    private Button btDone;
    private Button btTime;
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
        etMarkdown.setLanguage(new MarkdownLanguage());
        etMarkdown.setColorScheme(EditorTheme.INSTANCE.getVISUAL_STUDIO_2013());
        etMarkdown.setTextContent(ASSIGNMENT_TEXT);

        btHelp = (Button) root.findViewById(R.id.btHelpAssignment);
        etTitle = (EditText) root.findViewById(R.id.etTitleLectureAssignment);

        btDone = (Button) root.findViewById(R.id.btCreateLectureDoneAssignment);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimeStorage.wrongDate()) {
                    Toast.makeText(getContext(), "Wrong date format", Toast.LENGTH_SHORT);
                } else {
                    mViewModel.createAssignment(firestore, getContext(), etTitle.getText().toString(), etMarkdown.getText().toString(), getActivity());
                }
            }
        });

        btPreview = (Button) root.findViewById(R.id.btPreviewAssignment);
        btPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Preview");

                final MarkdownView markdownView = new MarkdownView(getContext());
                markdownView.addStyleSheet(new Github());
                String tmp = formatAssignment(etMarkdown.getText().toString());
                if (tmp.equals("ERROR_CODE")) {
                    tmp = "Formatting error.";
                }
                markdownView.loadMarkdown(tmp);
                alert.setView(markdownView);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nothing
                    }
                });

                alert.show();
            }
        });

        btTime = (Button) root.findViewById(R.id.btTimeAssignments);
        btTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDateDialog timeDateDialog = new TimeDateDialog();
                timeDateDialog.show(getActivity().getSupportFragmentManager(), "test");
            }
        });

        return root;
    }

    public String formatAssignment(String text) {
        String[] sections = text.split("Text");
        StringBuilder output = new StringBuilder();

        try {
            for (int i = 1; i < sections.length; i++) {
                sections[i] = sections[i].replace("\n", "");
                String[] parts = sections[i].split("\"");
                for (int j = 0; j < parts.length; j++) System.out.println(j + " " + parts[j]);
                output.append("## Task ").append(i).append("\n");
                output.append("***\n");
                for (int j = 1; j < parts.length; j += 2) {
                    switch (j) {
                        case 1: {
                            output.append(parts[j]).append("\n");
                            break;
                        }
                        case 3: {
                            output.append("\n### Example input\n***\n");
                            output.append(parts[j]).append("\n");
                            break;
                        }
                        case 5: {
                            output.append("\n### Example output\n***\n");
                            output.append(parts[j]).append("\n");
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            output.append("ERROR_CODE");
        }

        return output.toString();
    }
}