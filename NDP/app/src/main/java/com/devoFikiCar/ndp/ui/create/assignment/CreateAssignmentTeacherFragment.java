/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.create.assignment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.brackeys.ui.editorkit.theme.EditorTheme;
import com.brackeys.ui.editorkit.widget.TextProcessor;
import com.brackeys.ui.language.markdown.MarkdownLanguage;
import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.util.TimeStorage;
import com.google.firebase.firestore.FirebaseFirestore;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;

public class CreateAssignmentTeacherFragment extends Fragment {

    private static final String ASSIGNMENT_TEXT = "Text:\"Here goes task text\"\nExample input:\"Here goes input\"\nExample output:\"Example output\"\n" +
            "Task input:\"Here goes test input\"\nTask output:\"Here goes expected output to task input\"";
    private CreateAssignmentTeacherViewModel mViewModel;
    private TextProcessor etMarkdown;
    private Button btHelp;
    private Button btPreview;
    private Button btDone;
    private Button btTime;
    private FirebaseFirestore firestore;
    private EditText etTitle;
    private int selected = -1;

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

        etMarkdown = root.findViewById(R.id.etMarkdownAssignment);
        SharedPreferences preferences = getContext().getSharedPreferences("theme", Context.MODE_PRIVATE);
        selected = preferences.getInt("selected", 0);

        etMarkdown.setLanguage(new MarkdownLanguage());
        switch (selected) {
            case 0:
                etMarkdown.setColorScheme(EditorTheme.INSTANCE.getDARCULA());
                break;
            case 1:
                etMarkdown.setColorScheme(EditorTheme.INSTANCE.getMONOKAI());
                break;
            case 2:
                etMarkdown.setColorScheme(EditorTheme.INSTANCE.getOBSIDIAN());
                break;
            case 3:
                etMarkdown.setColorScheme(EditorTheme.INSTANCE.getLADIES_NIGHT());
                break;
            case 4:
                etMarkdown.setColorScheme(EditorTheme.INSTANCE.getTOMORROW_NIGHT());
                break;
            case 5:
                etMarkdown.setColorScheme(EditorTheme.INSTANCE.getVISUAL_STUDIO_2013());
                break;
        }
        etMarkdown.setTextContent(ASSIGNMENT_TEXT);;

        btHelp = root.findViewById(R.id.btHelpAssignment);
        etTitle = root.findViewById(R.id.etTitleLectureAssignment);

        btDone = root.findViewById(R.id.btCreateLectureDoneAssignment);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimeStorage.wrongDate()) {
                    Toast.makeText(getContext(), "Wrong date format", Toast.LENGTH_SHORT).show();
                } else {
                    mViewModel.createAssignment(firestore, getContext(), etTitle.getText().toString(), etMarkdown.getText().toString(), getActivity());
                }
            }
        });

        btPreview = root.findViewById(R.id.btPreviewAssignment);
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

        btTime = root.findViewById(R.id.btTimeAssignments);
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