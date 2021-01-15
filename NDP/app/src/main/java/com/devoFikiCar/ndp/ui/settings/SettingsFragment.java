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

import com.brackeys.ui.editorkit.widget.TextProcessor;
import com.devoFikiCar.ndp.R;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private Spinner spThemes;
    private TextProcessor tpPreview;
    private String[] themes = {"Dracula", "Monkai", "Obsidian", "Ladies night", "Tomorrow night", "Visual studio 2013"};
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, themes);
        spThemes.setAdapter(arrayAdapter);
        spThemes.setSelection(selected);

        spThemes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("selected", position).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tpPreview = root.findViewById(R.id.tpPreview);

        return root;
    }
}