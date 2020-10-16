package com.devoFikiCar.ndp.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.User;

public class LogInViewModel extends ViewModel {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(boolean isTeacher, String userID, boolean isLight) {
        LogInViewModel.user = new User(isTeacher, userID, isLight);
    }
}