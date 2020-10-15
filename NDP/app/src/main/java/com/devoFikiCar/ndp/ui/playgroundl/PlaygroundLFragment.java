package com.devoFikiCar.ndp.ui.playgroundl;

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

import com.devoFikiCar.fclang.StartFClang;
import com.devoFikiCar.ndp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PlaygroundLFragment extends Fragment {

    private PlaygroundLViewModel mViewModel;
    private Button btInput;
    private Button btRun;
    private EditText etCode;
    private EditText etOutput;
    private ArrayList<String> inputCode = new ArrayList<>();
    private ArrayList<String> output = new ArrayList<>();
    private Queue<Object> inputData = new LinkedList<>();

    public static PlaygroundLFragment newInstance() {
        return new PlaygroundLFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.playgroundl_fragment, container, false);
        btInput = (Button) root.findViewById(R.id.btInput);
        btRun = (Button) root.findViewById(R.id.btRun);

        etCode = (EditText) root.findViewById(R.id.etCode);
        etOutput = (EditText) root.findViewById(R.id.etOutput);

        btInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Input");

                final EditText input = new EditText(getActivity());
                input.setMaxLines(5);
                alert.setView(input);

                String inputText = "";


                for (Object o : inputData) {
                    inputText += (String) o;
                    inputText += "\n";
                }

                input.setText(inputText);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputData.clear();
                        for (String s1 : input.getText().toString().split("\n"))
                            inputData.add((Object) s1);
                        Toast.makeText(getActivity(), "Input saved", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputData.clear();
                        Toast.makeText(getActivity(), "Input deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.show();
            }
        });

        btRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearPlaygroundL();
                inputCode.addAll(Arrays.asList(etCode.getText().toString().split("\n")));
                output = StartFClang.startFClang(inputCode, new LinkedList<>(inputData));
                String out = "";
                for (String s : output) {
                    out += s;
                    out += "\n";
                }
                etOutput.setText(out);
                System.out.println(etCode.getText());
            }
        });
        return root;
    }

    private void clearPlaygroundL() {
        etOutput.setText("");
        inputCode.clear();
        output.clear();
    }
}