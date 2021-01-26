/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.playgroundl;

public class LanguageItem {
    private final String languageName;
    private final int languageImage;

    public LanguageItem(String languageName, int languageImage) {
        this.languageName = languageName;
        this.languageImage = languageImage;
    }

    public String getLanguageName() {
        return languageName;
    }

    public int getLanguageImage() {
        return languageImage;
    }
}
