package com.devoFikiCar.ndp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String fullName;
    private String schoolCode;
    private boolean teacher;
    private ArrayList<Integer> enrolledIn;

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.fullName = user.fullName;
        this.schoolCode = user.schoolCode;
        this.teacher = user.teacher;
        this.enrolledIn = user.enrolledIn;
    }

    public User(String username, String password, String fullName, String schoolCode, boolean teacher, ArrayList<Integer> enrolledIn) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.schoolCode = schoolCode;
        this.teacher = teacher;
        this.enrolledIn = enrolledIn;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public boolean isTeacher() {
        return teacher;
    }

    public ArrayList<Integer> getEnrolledIn() {
        return enrolledIn;
    }

    public void setEnrolledIn(ArrayList<Integer> enrolledIn) {
        this.enrolledIn = enrolledIn;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", schoolCode='" + schoolCode + '\'' +
                ", teacher=" + teacher +
                ", enrolledIn=" + enrolledIn.toString() +
                '}';
    }
}
