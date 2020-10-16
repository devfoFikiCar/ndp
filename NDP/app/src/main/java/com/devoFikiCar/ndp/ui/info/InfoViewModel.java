package com.devoFikiCar.ndp.ui.info;

import androidx.lifecycle.ViewModel;

public class InfoViewModel extends ViewModel {
    private final String infoPdfPath = "skripta.pdf";

    public String getInfoPdfPath() {
        return infoPdfPath;
    }
}