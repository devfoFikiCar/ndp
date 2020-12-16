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