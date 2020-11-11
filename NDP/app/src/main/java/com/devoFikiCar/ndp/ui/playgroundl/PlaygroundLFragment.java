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

import java.util.Arrays;
import java.util.List;

public class PlaygroundLFragment extends Fragment {

    private PlaygroundLViewModel mViewModel;
    private Button btInput;
    private Button btRun;
    private EditText etCode;
    private EditText etOutput;
    private Spinner spLanguages;
    private String options[] = {"fclang", "python", "java"};
    private static final String TAG = PlaygroundL.class.getSimpleName();

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

        etCode = (EditText) root.findViewById(R.id.etCode);
        etOutput = (EditText) root.findViewById(R.id.etOutput);

        spLanguages = (Spinner) root.findViewById(R.id.spLanguages);

        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,  options);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLanguages.setAdapter(spinnerAdapter);
        spLanguages.setSelection(0);

        mViewModel.setUser();
        System.out.println(mViewModel.getUser().toString());


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
                etOutput.setText(mViewModel.runCode(etCode.getText().toString()));
                System.out.println(etCode.getText());
            }
        });

        spLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: {
                        Log.i(TAG, "fclang chosen");
                        break;
                    }
                    case 1: {
                        Log.i(TAG, "python chosen");
                        break;
                    }
                    case 2: {
                        Log.i(TAG, "java chosen");
                        break;
                    }
                    default: {
                        Log.e(TAG, "Abnormal value");
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


}
