/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.settings;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.brackeys.ui.editorkit.theme.EditorTheme;
import com.brackeys.ui.editorkit.widget.TextProcessor;
import com.brackeys.ui.language.python.PythonLanguage;
import com.devoFikiCar.ndp.R;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {
    private SettingsViewModel mViewModel;
    private Spinner spThemes;
    private TextProcessor tpPreview;
    private String[] themes = {"Dracula", "Monkai", "Obsidian", "Ladies night", "Tomorrow night", "Visual studio 2013"};
    private String tvText = "# Import another python script\n" +
            "import vacations as v\n" +
            "\n" +
            "# Initialize the month list\n" +
            "months = [\"January\", \"February\", \"March\", \"April\", \"May\", \"June\",\n" +
            "          \"July\", \"August\", \"September\", \"October\", \"November\", \"December\"]\n" +
            "# Initial flag variable to print summer vacation one time\n" +
            "flag = 0\n" +
            "\n" +
            "# Iterate the list using for loop\n" +
            "for month in months:\n" +
            "    if month == \"June\" or month == \"July\":\n" +
            "        if flag == 0:\n" +
            "            print(\"Now\",v.vacation1)\n" +
            "            flag = 1\n" +
            "    elif month == \"December\":\n" +
            "            print(\"Now\",v.vacation2)\n" +
            "    else:\n" +
            "        print(\"The current month is\",month)";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        preferences = getContext().getSharedPreferences("theme", Context.MODE_PRIVATE);
        editor = preferences.edit();

        int selected = preferences.getInt("selected", 0);

        spThemes = root.findViewById(R.id.spThemes);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.settings_spinner_row, themes);
        spThemes.setAdapter(arrayAdapter);
        spThemes.setSelection(selected);

        tpPreview = root.findViewById(R.id.tpPreview);
        tpPreview.setEnabled(false);
        tpPreview.setLanguage(new PythonLanguage());
        switch (selected) {
            case 0:
                tpPreview.setColorScheme(EditorTheme.INSTANCE.getDARCULA());
                break;
            case 1:
                tpPreview.setColorScheme(EditorTheme.INSTANCE.getMONOKAI());
                break;
            case 2:
                tpPreview.setColorScheme(EditorTheme.INSTANCE.getOBSIDIAN());
                break;
            case 3:
                tpPreview.setColorScheme(EditorTheme.INSTANCE.getLADIES_NIGHT());
                break;
            case 4:
                tpPreview.setColorScheme(EditorTheme.INSTANCE.getTOMORROW_NIGHT());
                break;
            case 5:
                tpPreview.setColorScheme(EditorTheme.INSTANCE.getVISUAL_STUDIO_2013());
                break;
        }
        tpPreview.setTextContent(tvText);
        spThemes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("selected", position).commit();
                switch (position) {
                    case 0:
                        tpPreview.setColorScheme(EditorTheme.INSTANCE.getDARCULA());
                        break;
                    case 1:
                        tpPreview.setColorScheme(EditorTheme.INSTANCE.getMONOKAI());
                        break;
                    case 2:
                        tpPreview.setColorScheme(EditorTheme.INSTANCE.getOBSIDIAN());
                        break;
                    case 3:
                        tpPreview.setColorScheme(EditorTheme.INSTANCE.getLADIES_NIGHT());
                        break;
                    case 4:
                        tpPreview.setColorScheme(EditorTheme.INSTANCE.getTOMORROW_NIGHT());
                        break;
                    case 5:
                        tpPreview.setColorScheme(EditorTheme.INSTANCE.getVISUAL_STUDIO_2013());
                        break;
                }
                tpPreview.setTextContent(tvText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return root;
    }
}