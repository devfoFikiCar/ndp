/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2021
 */

package com.devoFikiCar.ndp.ui.taskEditor.student;

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
import android.widget.Spinner;

import com.brackeys.ui.editorkit.widget.TextProcessor;
import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.helper.tempStorage;
import com.devoFikiCar.ndp.ui.playgroundl.LanguageAdapter;
import com.devoFikiCar.ndp.ui.playgroundl.LanguageItem;

import java.util.ArrayList;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;

public class TaskEditorStudentFragment extends Fragment {

    private TaskEditorStudentViewModel mViewModel;
    private Button btInput;
    private Button btRun;
    private Spinner spLanguages;
    private ArrayList<LanguageItem> languageItems;
    private LanguageAdapter languageAdapter;
    private TextProcessor etCode;
    private TextProcessor etOutput;
    private Button btOk;
    private Button btText;

    public static TaskEditorStudentFragment newInstance() {
        return new TaskEditorStudentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_editor_student_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(TaskEditorStudentViewModel.class);

        Bundle bundle = this.getArguments();
        int position = bundle.getInt("position");

        initList();
        spLanguages = (Spinner) root.findViewById(R.id.spLanguagesT);
        languageAdapter = new LanguageAdapter(getContext(), languageItems);
        spLanguages.setAdapter(languageAdapter);
        spLanguages.setSelection(0);

        btOk = root.findViewById(R.id.btOkCodeT);
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btText = root.findViewById(R.id.btTaskTextT);
        btText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Task text");

                final MarkdownView markdownView = new MarkdownView(getContext());
                markdownView.addStyleSheet(new Github());
                markdownView.loadMarkdown(tempStorage.tasks.get(position).toMarkdown(position + 1));
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

        return root;
    }

    private void initList() {
        languageItems = new ArrayList<>();
        languageItems.add(new LanguageItem("Fclang", R.drawable.ic_fclang));
        languageItems.add(new LanguageItem("Python", R.drawable.ic_python));
        languageItems.add(new LanguageItem("Java", R.drawable.ic_java));
    }
}