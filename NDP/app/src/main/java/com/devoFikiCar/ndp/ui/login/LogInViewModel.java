package com.devoFikiCar.ndp.ui.login;

import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.User;

public class LogInViewModel extends ViewModel {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(String fullName, String id, boolean teacher) {
        user = new User(id, fullName, teacher);
    }
}