package com.devoFikiCar.ndp.ui.login;

import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.User;

public class LogInViewModel extends ViewModel {
    private User user;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = new User(user);
    }
}