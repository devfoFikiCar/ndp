package com.devoFikiCar.ndp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String schoolCode;
    private boolean teacher;
    private ArrayList<String> enrolledIn;

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
        this.fullName = user.fullName;
        this.schoolCode = user.schoolCode;
        this.teacher = user.teacher;
        if (user.enrolledIn == null) {
            this.enrolledIn = new ArrayList<>();
        } else {
            this.enrolledIn = user.enrolledIn;
        }
        this.id = user.id;
    }

    public User(String username, String password, String fullName, String schoolCode, boolean teacher, ArrayList<String> enrolledIn) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.schoolCode = schoolCode;
        this.teacher = teacher;
        this.enrolledIn = enrolledIn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<String> getEnrolledIn() {
        return new ArrayList(this.enrolledIn);
    }

    public void setEnrolledIn(ArrayList<String> enrolledIn) {
        this.enrolledIn = enrolledIn;
    }

    public void addEnrolledIn(String id) {
        if (this.enrolledIn == null) {
            this.enrolledIn = new ArrayList<>();
        }
        this.enrolledIn.add(id);
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
