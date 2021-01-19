/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.helper.userSave;

public class InfoFragment extends Fragment {
    private WebView webview;

    private InfoViewModel mViewModel;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.info_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(InfoViewModel.class);
        webview = (WebView) root.findViewById(R.id.webview);

        if (userSave.user.isTeacher()) {
            webview.loadUrl("file:///android_asset/teacherInfo.html");
        } else {
            webview.loadUrl("file:///android_asset/studentInfo.html");
        }

        return root;
    }
}