/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.lectureassignment;

import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.userSave;
import com.devoFikiCar.ndp.util.User;

public class LectureAssignmentViewModel extends ViewModel {
    User user;

    public User getUser() {
        return user;
    }

    public void setUser() {
        this.user = new User(userSave.user);
    }

    public LectureAssignmentViewModel() {
        init();
    }

    private void init() {
        setUser();
    }
}