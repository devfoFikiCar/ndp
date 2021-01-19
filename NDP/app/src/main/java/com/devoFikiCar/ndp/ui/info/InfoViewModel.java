/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.info;

import androidx.lifecycle.ViewModel;

public class InfoViewModel extends ViewModel {
    private final String infoPdfPathStudent = "IntroductionStudent.pdf";
    private final String infoPdfPathTeacher = "IntroductionTeacher.pdf";

    public String getInfoPdfPathStudent() {
        return infoPdfPathStudent;
    }

    public String getInfoPdfPathTeacher() {
        return infoPdfPathTeacher;
    }
}