package com.devoFikiCar.ndp.ui.playgroundl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.devoFikiCar.ndp.PlaygroundL;
import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.api.RetrieveOutput;
import com.devoFikiCar.ndp.api.SubmitCode;
import com.devoFikiCar.ndp.async.AsyncTask;

import net.cryptobrewery.syntaxview.SyntaxView;

import dmax.dialog.SpotsDialog;

public class PlaygroundLFragment extends Fragment {

    private static final String TAG = PlaygroundL.class.getSimpleName();
    private static int LANGUAGE = 0;
    private PlaygroundLViewModel mViewModel;
    private Button btInput;
    private Button btRun;
    private SyntaxView etCode;
    private EditText etOutput;
    private Spinner spLanguages;
    private String options[] = {"fclang", "python", "java"};
    private AlertDialog alertDialog;

    public static PlaygroundLFragment newInstance() {
        return new PlaygroundLFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.playgroundl_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(PlaygroundLViewModel.class);
        btInput = (Button) root.findViewById(R.id.btInput);
        btRun = (Button) root.findViewById(R.id.btRun);

        etCode = (SyntaxView) root.findViewById(R.id.etCode);
        etOutput = (EditText) root.findViewById(R.id.etOutput);

        spLanguages = (Spinner) root.findViewById(R.id.spLanguages);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, options);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLanguages.setAdapter(spinnerAdapter);
        spLanguages.setSelection(0);

        mViewModel.setUser();
        System.out.println(mViewModel.getUser().toString());

        etCode.setBgColor("#2b2b2b");
        //this will set the color of strings between " "
        etCode.setPrintStatmentsColor("#6a8759");
        //this will set the default code text color other than programming keywords!
        etCode.setCodeTextColor("#ffffff");
        //this will set programming keywords color like String,int,for,etc...
        etCode.setKeywordsColor("#cc7832");
        //this will set the numbers color in code | ex: return 0; 0 will be colored
        etCode.setNumbersColor("#4a85a3");
        //this will set the line number view background color at left
        etCode.setRowNumbersBgColor("#2b2b2b");
        //this will set the color of numbers in the line number view at left
        etCode.setRowNumbersColor("#cc7832");
        //this will set color of Annotations like super,@Nullable,etc ....
        etCode.setAnnotationsColor("#1932F3");
        //this will set special characters color like ;
        etCode.setSpecialCharsColor("#cc7832");
        etCode.setAutoIndent(false);


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
                        etOutput.setText(mViewModel.runCode(etCode.getCode().getText().toString()));
                        System.out.println(etCode.getCode().getText().toString());
                        break;
                    }
                    case 71: {
                        // PYTHON
                        APITask apiTask = new APITask();
                        apiTask.execute(new String[]{etCode.getCode().getText().toString(), mViewModel.getStdin()});
                        break;
                    }
                    case 62: {
                        // JAVA
                        //break;
                    }
                    default: {
                        etOutput.setText(mViewModel.runCode(etCode.getCode().getText().toString().toString()));
                        System.out.println(etCode.getCode().getText().toString());
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
                        break;
                    }
                    case 1: {
                        Log.i(TAG, "python chosen");
                        LANGUAGE = 71;
                        break;
                    }
                    case 2: {
                        Log.i(TAG, "java chosen");
                        LANGUAGE = 62;
                        break;
                    }
                    default: {
                        Log.e(TAG, "Abnormal value");
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

        etCode.checkMyCode();

        return root;
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
