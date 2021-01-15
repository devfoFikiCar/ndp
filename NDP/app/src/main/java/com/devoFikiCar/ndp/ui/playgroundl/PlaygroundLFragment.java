/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.playgroundl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.brackeys.ui.editorkit.theme.EditorTheme;
import com.brackeys.ui.editorkit.widget.TextProcessor;
import com.brackeys.ui.language.java.JavaLanguage;
import com.brackeys.ui.language.python.PythonLanguage;
import com.devoFikiCar.ndp.PlaygroundL;
import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.api.RetrieveOutput;
import com.devoFikiCar.ndp.api.SubmitCode;
import com.devoFikiCar.ndp.async.AsyncTask;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class PlaygroundLFragment extends Fragment {

    private static final String PYTHON_CODE_START = "# code";
    private static final String FCLANG_CODE_START = "// code";
    private static final String JAVA_CODE_START = "public class Main {\n"
            + "\t\t\t\tpublic static void main(String[] args){\n"
            + "\t\t\t\t\t\t\t\t// code\n"
            + "\t\t\t\t}\n"
            + "}\n";
    private static final String TAG = PlaygroundL.class.getSimpleName();
    private static int LANGUAGE = 0;
    private PlaygroundLViewModel mViewModel;
    private Button btInput;
    private Button btRun;
    private TextProcessor etCode;
    private EditText etOutput;
    private Spinner spLanguages;
    private AlertDialog alertDialog;
    private ArrayList<LanguageItem> languageItems;
    private LanguageAdapter languageAdapter;

    public static PlaygroundLFragment newInstance() {
        return new PlaygroundLFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.playgroundl_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(PlaygroundLViewModel.class);
        btInput = root.findViewById(R.id.btInput);
        btRun = root.findViewById(R.id.btRun);

        etCode = root.findViewById(R.id.etCode);
        etOutput = root.findViewById(R.id.etOutput);

        initList();
        spLanguages = root.findViewById(R.id.spLanguages);
        languageAdapter = new LanguageAdapter(getContext(), languageItems);
        spLanguages.setAdapter(languageAdapter);
        spLanguages.setSelection(0);

        System.out.println(mViewModel.getUser().toString());

        etCode.setTextContent(FCLANG_CODE_START);
        etCode.setLanguage(new JavaLanguage());
        etCode.setColorScheme(EditorTheme.INSTANCE.getMONOKAI());

        // todo add themes everywhere you need
        SharedPreferences preferences = getContext().getSharedPreferences("theme", Context.MODE_PRIVATE);
        System.out.println(preferences.getInt("selected", -1));

        btInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Input");

                final EditText input = new EditText(getActivity());
                input.setMaxLines(5);
                alert.setView(input);

                input.setText(mViewModel.setInputText());

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mViewModel.setInputData(input.getText().toString());
                        Toast.makeText(getActivity(), "Input saved", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mViewModel.getInputData().clear();
                        Toast.makeText(getActivity(), "Input deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.show();
            }
        });

        btRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etOutput.setText("");
                switch (LANGUAGE) {
                    case 0: {
                        // fclang
                        etOutput.setText(mViewModel.runCode(etCode.getText().toString()));
                        System.out.println(etCode.getText().toString());
                        break;
                    }
                    case 71:
                    case 62: {
                        // java and python
                        APITask apiTask = new APITask();
                        apiTask.execute(new String[]{etCode.getText().toString(), mViewModel.getStdin()});
                        break;
                    }
                }
            }
        });

        spLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        Log.i(TAG, "fclang chosen");
                        LANGUAGE = 0;
                        etCode.setTextContent(FCLANG_CODE_START);
                        etCode.setLanguage(new JavaLanguage());
                        break;
                    }
                    case 1: {
                        Log.i(TAG, "python chosen");
                        etCode.setTextContent(PYTHON_CODE_START);
                        etCode.setLanguage(new PythonLanguage());
                        LANGUAGE = 71;
                        break;
                    }
                    case 2: {
                        Log.i(TAG, "java chosen");
                        etCode.setTextContent(JAVA_CODE_START);
                        etCode.setLanguage(new JavaLanguage());
                        LANGUAGE = 62;
                        break;
                    }
                    default: {
                        Log.e(TAG, "Abnormal value");
                        etCode.setTextContent(FCLANG_CODE_START);
                        etCode.setLanguage(new JavaLanguage());
                        LANGUAGE = 0;
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "Nothing selected");
                spLanguages.setSelection(0);
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

    public class APITask extends AsyncTask<String[], Integer, String> {
        @Override
        protected void onPreExecute() {
            alertDialog = new SpotsDialog.Builder()
                    .setContext(getContext())
                    .setMessage("Executing code")
                    .setCancelable(false)
                    .build();
            alertDialog.show();
            btRun.setEnabled(false);
        }

        @Override
        protected String doInBackground(String[] input) throws Exception {
            String token = SubmitCode.requestToken(input[0], LANGUAGE, input[1]);
            System.out.println(token);
            String out = RetrieveOutput.getOutput(token);
            return out;
        }

        @Override
        protected void onPostExecute(String output) {
            alertDialog.dismiss();
            btRun.setEnabled(true);
            etOutput.setText(output);
        }

        @Override
        protected void onBackgroundError(Exception e) {
            e.printStackTrace();
        }
    }
}
