package com.devoFikiCar.ndp.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.devoFikiCar.ndp.R;
import com.github.barteksc.pdfviewer.PDFView;

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
        mViewModel = new ViewModelProvider(this).get(InfoViewModel.class);
        pdfView = (PDFView) root.findViewById(R.id.pdfView);
        pdfView.fromAsset(mViewModel.getInfoPdfPath()).load();
        return root;
    }
}