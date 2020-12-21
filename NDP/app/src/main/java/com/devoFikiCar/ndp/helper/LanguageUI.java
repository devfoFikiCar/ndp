/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.helper;

public class LanguageUI {
    private boolean english = true;

    public LanguageUI(boolean english) {
        this.english = english;
    }

    public String getPasswordHint() {
        return this.english ? ENG.PASSWORD_HINT : SRP.PASSWORD_HINT;
    }

    public String getUsernameHint() {
        return this.english ? ENG.USERNAME_HINT : SRP.USERNAME_HINT;
    }

    public String getLoginButton() {
        return this.english ? ENG.LOGIN_BUTTON : SRP.LOGIN_BUTTON;
    }

    public String getTeacherCheckbox() {
        return this.english ? ENG.TEACHER_CHECKBOX : SRP.TEACHER_CHECKBOX;
    }

    public String getPlayInPgLabel() {
        return this.english ? ENG.PLAY_IN_PG_LABEL : SRP.PLAY_IN_PG_LABEL;
    }

    public String getInputButton() {
        return this.english ? ENG.INPUT_BUTTON : SRP.INPUT_BUTTON;
    }

    public String getRunButton() {
        return this.english ? ENG.RUN_BUTTON : SRP.RUN_BUTTON;
    }

    public String getCreateClassButton() {
        return this.english ? ENG.CREATE_CLASS_BUTTON : SRP.CREATE_CLASS_BUTTON;
    }

    public String getLecturesButton() {
        return this.english ? ENG.LECTURES_BUTTON : SRP.LECTURES_BUTTON;
    }

    public String getAssignmentsButton() {
        return this.english ? ENG.ASSIGNMENTS_BUTTON : SRP.ASSIGNMENTS_BUTTON;
    }

    public String getPreviewButton() {
        return this.english ? ENG.PREVIEW_BUTTON : SRP.PREVIEW_BUTTON;
    }

    public String getHelpButton() {
        return this.english ? ENG.HELP_BUTTON : SRP.HELP_BUTTON;
    }

    public String getDoneButton() {
        return this.english ? ENG.DONE_BUTTON : SRP.DONE_BUTTON;
    }

    public String getTitleHint() {
        return this.english ? ENG.TITLE_HINT : SRP.TITLE_HINT;
    }

    public String getJoinButton() {
        return this.english ? ENG.JOIN_BUTTON : SRP.JOIN_BUTTON;
    }
}
