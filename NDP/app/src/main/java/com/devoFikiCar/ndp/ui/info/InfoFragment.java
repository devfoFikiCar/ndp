package com.devoFikiCar.ndp.ui.info;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devoFikiCar.ndp.R;
import com.github.barteksc.pdfviewer.PDFView;

/*
* TODO:
*  - add two pdfs one for students one for techers
*  - add title eg. Info for students
*  - fix layout
* */

public class InfoFragment extends Fragment {
    private PDFView pdfView;

    private InfoViewModel mViewModel;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.info_fragment, container, false);
        pdfView = (PDFView) root.findViewById(R.id.pdfView);
        pdfView.fromAsset("skripta.pdf").load();
        return root;
    }
}