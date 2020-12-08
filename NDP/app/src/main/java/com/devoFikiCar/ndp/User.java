package com.devoFikiCar.ndp;

public class User {
    private String schoolCode = "";
    private String name = "";
    private String fullName = "";
    private boolean teacher = false;

    public User(User user) {
        this.schoolCode = user.schoolCode;
        this.name = user.name;
        this.fullName = user.fullName;
        this.teacher = user.teacher;
    }

    public User(String schoolCode, String name, String fullName, boolean teacher) {
        this.schoolCode = schoolCode;
        this.name = name;
        this.fullName = fullName;
        this.teacher = teacher;
    }

    public User(String fullName, boolean teacher) {
        this.fullName = fullName;
        this.teacher = teacher;
        if (fullName != null) {
            this.schoolCode = fullName.substring(0, 4);
            this.name = fullName.substring(4);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                ", schoolCode='" + schoolCode + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", teacher=" + teacher +
                '}';
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isTeacher() {
        return teacher;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }
}
