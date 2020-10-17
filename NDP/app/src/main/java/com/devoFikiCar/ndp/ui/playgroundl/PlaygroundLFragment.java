package com.devoFikiCar.ndp.ui.playgroundl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devoFikiCar.fclang.StartFClang;
import com.devoFikiCar.fclang.Token;
import com.devoFikiCar.fclang.lexer.Lexer;
import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.User;
import com.devoFikiCar.ndp.ui.login.LogInFragment;
import com.devoFikiCar.ndp.ui.login.LogInViewModel;

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

        mViewModel.setUser();
        System.out.println(mViewModel.getUser().toString());

        if(mViewModel.getUser().isLight()) {
            // TODO: add light
        } else {
            etCode.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkBackground));
            etCode.setTextColor(ContextCompat.getColor(getContext(), R.color.darkVariableNames));
            etOutput.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.darkBackground));
            etOutput.setTextColor(ContextCompat.getColor(getContext(), R.color.darkVariableNames));
            root.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccentDark));
        }

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

        return root;
    }


}

/*
* etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                System.out.println("sL " + sLength + " sl " + s.length());
                if (sLength - s.length() != 0) {
                    System.out.println(s);

                    tmpCode.clear();
                    tokens.clear();
                    // todo: remove split
                    tmpCode.add(editable.toString());
                    Lexer.setCode(tmpCode);
                    tokens = Lexer.lexer();
                    Lexer.clearCode();

                    ArrayList<Integer> newLinePositions = findNewLine(editable.toString());

                    //ForegroundColorSpan red = new ForegroundColorSpan(Color.RED);
                    //SpannableStringBuilder ss = new SpannableStringBuilder(s);

                    //ss.setSpan(red,1,1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    /*
                     * fgcs ....
                     * ....
                     * s += tokens.value
                     *
                     *
                     *
SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("");
                    for (Token t : tokens) {
                            System.out.println(t.toString());
                            switch (t.key) {
                            case "EQUAL_TO":
                            case "EQUALS":
                            case "ADDITION":
                            case "SUBTRACTION":
                            case "MULTIPLICATION":
                            case "DIVISION":
                            case "DOT":
                            case "AND":
                            case "COMMA":
                            case "GREATER_EQUAL":
                            case "GREATER_THAN":
                            case "LESS_EQUAL":
                            case "LESS_THAN":
                            case "NOT_EQUAL":
                            case "NOT":
                            case "OR":
                            case "SPLIT":
                            // TODO: SIGNS
                            //sLength += t.value.length();
                            //sLength++;
                            spannableStringBuilder.append(t.value);
                            spannableStringBuilder.setSpan(darkSigns, spannableStringBuilder.length() - t.value.length(), spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                            if(spannableStringBuilder.length() < editable.toString().length() && editable.toString().charAt(spannableStringBuilder.length()) == '\n') spannableStringBuilder.append("\n");
        break;
        case "COMMENT":
        // TODO: COMMENT
        spannableStringBuilder.append(t.value);
        spannableStringBuilder.setSpan(darkComments, spannableStringBuilder.length() - t.value.length(), spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if(spannableStringBuilder.length() < editable.toString().length() && editable.toString().charAt(spannableStringBuilder.length()) == '\n') spannableStringBuilder.append("\n");
        break;
        case "PRINT":
        case "INT_T":
        case "DECIMAL_T":
        case "STRING_T":
        case "BOOL_T":
        case "IF":
        case "ELSE":
        case "GOTO":
        case "FOR":
        case "INT_MATRIX":
        case "DECIMAL_MATRIX":
        case "STRING_MATRIX":
        case "BOOL_MATRIX":
        case "INT_ARRAY":
        case "DECIMAL_ARRAY":
        case "STRING_ARRAY":
        case "BOOL_ARRAY":
        case "NEW":
        case "L_GOTO":
        // TODO: KEYWORDS
        spannableStringBuilder.append(t.value);
        spannableStringBuilder.setSpan(darkKeywords, spannableStringBuilder.length() - t.value.length(), spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if(spannableStringBuilder.length() < editable.toString().length() && editable.toString().charAt(spannableStringBuilder.length()) == '\n') spannableStringBuilder.append("\n");
        break;
        case "MAX":
        case "MIN":
        case "GET":
        case "POW":
        case "SQRT":
        case "SIZE":
        case "SET":
        case "SORT":
        case "GET_INT":
        case "GET_DECIMAL":
        case "ROW_SIZE":
        case "COLUMN_SIZE":
        case "ABS":
        case "GET_STRING":
        case "GET_BOOL":
        // TODO: METHODS
        spannableStringBuilder.append(t.value);
        spannableStringBuilder.setSpan(darkMethods, spannableStringBuilder.length() - t.value.length(), spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if(spannableStringBuilder.length() < editable.toString().length() && editable.toString().charAt(spannableStringBuilder.length()) == '\n') spannableStringBuilder.append("\n");
        break;
        case "STRING":
        // TODO: STRING
        spannableStringBuilder.append(t.value);
        spannableStringBuilder.setSpan(darkStrings, spannableStringBuilder.length() - t.value.length(), spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if(spannableStringBuilder.length() < editable.toString().length() && editable.toString().charAt(spannableStringBuilder.length()) == '\n') spannableStringBuilder.append("\n");
        break;
        case "NAME":
        // TODO: NAME
        spannableStringBuilder.append(t.value);
        spannableStringBuilder.setSpan(darkName, spannableStringBuilder.length() - t.value.length(), spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if(spannableStringBuilder.length() < editable.toString().length() && editable.toString().charAt(spannableStringBuilder.length()) == '\n') spannableStringBuilder.append("\n");
        break;
        case "BOOL":
        case "INT":
        case "DECIMAL":
        // TODO: BID
        spannableStringBuilder.append(t.value);
        spannableStringBuilder.setSpan(darkBID, spannableStringBuilder.length() - t.value.length(), spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if(spannableStringBuilder.length() < editable.toString().length() && editable.toString().charAt(spannableStringBuilder.length()) == '\n') spannableStringBuilder.append("\n");
        break;
        case "L_PARENTHESES":
        case "R_PARENTHESES":
        case "L_BRACES":
        case "R_BRACES":
        if (user.isLight()) {
        // TODO: BP
        } else {
        // TODO: SIGNS
        spannableStringBuilder.append(t.value);
        spannableStringBuilder.setSpan(darkSigns, spannableStringBuilder.length() - t.value.length(), spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if(spannableStringBuilder.length() < editable.toString().length() && editable.toString().charAt(spannableStringBuilder.length()) == '\n') spannableStringBuilder.append("\n");
        }
        break;
        case "SPACE":
        // FIX SPACE
        spannableStringBuilder.append(" ");
        break;
        }
        System.out.println(t.toString());
        sLength = spannableStringBuilder.length();
        etCode.setText(spannableStringBuilder, TextView.BufferType.EDITABLE);
        etCode.setSelection(etCode.getText().length());
        }
        }
        }
        });
* */