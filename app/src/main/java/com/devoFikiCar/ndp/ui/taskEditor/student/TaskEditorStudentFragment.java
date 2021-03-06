/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.taskEditor.student;

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
import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.TaskEditorStudent;
import com.devoFikiCar.ndp.api.RetrieveOutput;
import com.devoFikiCar.ndp.api.SubmitCode;
import com.devoFikiCar.ndp.async.AsyncTask;
import com.devoFikiCar.ndp.helper.tempStorage;
import com.devoFikiCar.ndp.ui.playgroundl.LanguageAdapter;
import com.devoFikiCar.ndp.ui.playgroundl.LanguageItem;

import java.util.ArrayList;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;
import dmax.dialog.SpotsDialog;

public class TaskEditorStudentFragment extends Fragment {

    private static final String PYTHON_CODE_START = "# code";
    private static final String FCLANG_CODE_START = "// code";
    private static final String JAVA_CODE_START = "public class Main {\n"
            + "\t\t\t\tpublic static void main(String[] args){\n"
            + "\t\t\t\t\t\t\t\t// code\n"
            + "\t\t\t\t}\n"
            + "}\n";
    private static final String TAG = TaskEditorStudent.class.getSimpleName();
    private static int LANGUAGE = 0;
    private TaskEditorStudentViewModel mViewModel;
    private Button btInput;
    private Button btRun;
    private Spinner spLanguages;
    private ArrayList<LanguageItem> languageItems;
    private LanguageAdapter languageAdapter;
    private TextProcessor etCode;
    private EditText etOutput;
    private Button btOk;
    private Button btText;
    private AlertDialog alertDialog;
    private int selected = -1;

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

        btInput = root.findViewById(R.id.btInputT);
        btRun = root.findViewById(R.id.btRunT);

        etCode = root.findViewById(R.id.etCodeT);
        etOutput = root.findViewById(R.id.etOutputT);

        initList();
        spLanguages = root.findViewById(R.id.spLanguagesT);
        languageAdapter = new LanguageAdapter(getContext(), languageItems);
        spLanguages.setAdapter(languageAdapter);
        spLanguages.setSelection(0);

        System.out.println(mViewModel.getUser().toString());

        SharedPreferences preferences = getContext().getSharedPreferences("theme", Context.MODE_PRIVATE);
        selected = preferences.getInt("selected", 0);

        etCode.setLanguage(new JavaLanguage());
        switch (selected) {
            case 0:
                etCode.setColorScheme(EditorTheme.INSTANCE.getDARCULA());
                break;
            case 1:
                etCode.setColorScheme(EditorTheme.INSTANCE.getMONOKAI());
                break;
            case 2:
                etCode.setColorScheme(EditorTheme.INSTANCE.getOBSIDIAN());
                break;
            case 3:
                etCode.setColorScheme(EditorTheme.INSTANCE.getLADIES_NIGHT());
                break;
            case 4:
                etCode.setColorScheme(EditorTheme.INSTANCE.getTOMORROW_NIGHT());
                break;
            case 5:
                etCode.setColorScheme(EditorTheme.INSTANCE.getVISUAL_STUDIO_2013());
                break;
        }
        if (tempStorage.solutions.size() > position && tempStorage.solutions.get(position) != null && tempStorage.solutions.get(position).length() > 0) {
            etCode.setTextContent(tempStorage.solutions.get(position));
        } else {
            etCode.setTextContent(FCLANG_CODE_START);
        }

        btInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Ulaz");

                final EditText input = new EditText(getActivity());
                input.setMaxLines(5);
                alert.setView(input);

                input.setText(mViewModel.setInputText());

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mViewModel.setInputData(input.getText().toString());
                        Toast.makeText(getActivity(), "Ulaz sacuvan", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mViewModel.getInputData().clear();
                        Toast.makeText(getActivity(), "Ulaz obrisan", Toast.LENGTH_SHORT).show();
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

        btOk = root.findViewById(R.id.btOkCodeT);
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(etCode.getText().toString());
                tempStorage.solutions.set(position, etCode.getText().toString());
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btText = root.findViewById(R.id.btTaskTextT);
        btText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Postavka");

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

        spLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                view.findViewById(R.id.imgview).setVisibility(View.VISIBLE);
                switch (position1) {
                    case 0: {
                        Log.i(TAG, "fclang chosen");
                        LANGUAGE = 0;
                        tempStorage.LANGUAGE_ID = LANGUAGE;

                        etCode.setLanguage(new JavaLanguage());
                        switch (selected) {
                            case 0:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getDARCULA());
                                break;
                            case 1:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getMONOKAI());
                                break;
                            case 2:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getOBSIDIAN());
                                break;
                            case 3:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getLADIES_NIGHT());
                                break;
                            case 4:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getTOMORROW_NIGHT());
                                break;
                            case 5:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getVISUAL_STUDIO_2013());
                                break;
                        }

                        if (tempStorage.solutions.size() > position && tempStorage.solutions.get(position) != null && tempStorage.solutions.get(position).length() > 0) {
                            etCode.setTextContent(tempStorage.solutions.get(position));
                        } else {
                            etCode.setTextContent(FCLANG_CODE_START);
                        }

                        break;
                    }
                    case 1: {
                        Log.i(TAG, "python chosen");

                        etCode.setLanguage(new PythonLanguage());
                        switch (selected) {
                            case 0:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getDARCULA());
                                break;
                            case 1:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getMONOKAI());
                                break;
                            case 2:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getOBSIDIAN());
                                break;
                            case 3:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getLADIES_NIGHT());
                                break;
                            case 4:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getTOMORROW_NIGHT());
                                break;
                            case 5:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getVISUAL_STUDIO_2013());
                                break;
                        }

                        if (tempStorage.solutions.size() > position && tempStorage.solutions.get(position) != null && tempStorage.solutions.get(position).length() > 0) {
                            etCode.setTextContent(tempStorage.solutions.get(position));
                        } else {
                            etCode.setTextContent(PYTHON_CODE_START);
                        }
                        LANGUAGE = 71;
                        tempStorage.LANGUAGE_ID = LANGUAGE;
                        break;
                    }
                    case 2: {
                        Log.i(TAG, "java chosen");

                        etCode.setLanguage(new JavaLanguage());
                        switch (selected) {
                            case 0:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getDARCULA());
                                break;
                            case 1:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getMONOKAI());
                                break;
                            case 2:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getOBSIDIAN());
                                break;
                            case 3:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getLADIES_NIGHT());
                                break;
                            case 4:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getTOMORROW_NIGHT());
                                break;
                            case 5:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getVISUAL_STUDIO_2013());
                                break;
                        }

                        if (tempStorage.solutions.size() > position && tempStorage.solutions.get(position) != null && tempStorage.solutions.get(position).length() > 0) {
                            etCode.setTextContent(tempStorage.solutions.get(position));
                        } else {
                            etCode.setTextContent(JAVA_CODE_START);
                        }
                        LANGUAGE = 62;
                        tempStorage.LANGUAGE_ID = LANGUAGE;
                        break;
                    }
                    default: {
                        Log.e(TAG, "Abnormal value");

                        etCode.setLanguage(new JavaLanguage());
                        switch (selected) {
                            case 0:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getDARCULA());
                                break;
                            case 1:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getMONOKAI());
                                break;
                            case 2:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getOBSIDIAN());
                                break;
                            case 3:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getLADIES_NIGHT());
                                break;
                            case 4:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getTOMORROW_NIGHT());
                                break;
                            case 5:
                                etCode.setColorScheme(EditorTheme.INSTANCE.getVISUAL_STUDIO_2013());
                                break;
                        }

                        if (tempStorage.solutions.size() > position && tempStorage.solutions.get(position) != null && tempStorage.solutions.get(position).length() > 0) {
                            etCode.setTextContent(tempStorage.solutions.get(position));
                        } else {
                            etCode.setTextContent(FCLANG_CODE_START);
                        }
                        LANGUAGE = 0;
                        tempStorage.LANGUAGE_ID = LANGUAGE;
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
                    .setMessage("Izvrsavanje koda")
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