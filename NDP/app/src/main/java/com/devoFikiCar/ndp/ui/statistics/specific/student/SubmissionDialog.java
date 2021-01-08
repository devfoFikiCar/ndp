/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2021
 */

package com.devoFikiCar.ndp.ui.statistics.specific.student;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.brackeys.ui.editorkit.theme.EditorTheme;
import com.brackeys.ui.editorkit.widget.TextProcessor;
import com.brackeys.ui.language.java.JavaLanguage;
import com.brackeys.ui.language.python.PythonLanguage;
import com.devoFikiCar.ndp.R;

public class SubmissionDialog extends AppCompatDialogFragment {
    private TextProcessor tpCode;
    private EditText etOutput;
    private EditText etExpectedOutput;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_submission_dialog, null);
        Bundle bundle = this.getArguments();

        builder.setView(view)
                .setTitle("Task submission.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing
                    }
                });

        tpCode = (TextProcessor) view.findViewById(R.id.tpCodeDialog);
        etOutput = (EditText) view.findViewById(R.id.etDialogOutput);
        etExpectedOutput = (EditText) view.findViewById(R.id.etDialogExpectedOutput);

        etOutput.setEnabled(false);
        etExpectedOutput.setEnabled(false);

        tpCode.setTextContent(bundle.getString("code"));
        System.out.println(bundle.getString("code"));
        tpCode.setLanguage(new PythonLanguage());
        tpCode.setColorScheme(EditorTheme.INSTANCE.getMONOKAI());

        etOutput.setText(bundle.getString("output"));
        System.out.println(bundle.getString("output"));
        etExpectedOutput.setText(bundle.getString("expectedOutput"));
        System.out.println(bundle.getString("expectedOutput"));

        return builder.create();
    }
}
