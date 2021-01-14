/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.login;

import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.util.User;

public class LogInViewModel extends ViewModel {
    private User user;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = new User(user);
    }
}