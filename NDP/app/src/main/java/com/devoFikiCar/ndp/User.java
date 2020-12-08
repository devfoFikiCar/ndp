package com.devoFikiCar.ndp;

public class User {
    private String id = "";
    private String schoolCode = "";
    private String name = "";
    private String fullName = "";
    private boolean teacher = false;

    public User(User user) {
        this.id = user.id;
        this.schoolCode = user.schoolCode;
        this.name = user.name;
        this.fullName = user.fullName;
        this.teacher = user.teacher;
    }

    public User(String id, String schoolCode, String name, String fullName, boolean teacher) {
        this.id = id;
        this.schoolCode = schoolCode;
        this.name = name;
        this.fullName = fullName;
        this.teacher = teacher;
    }

    public User(String id, String fullName, boolean teacher) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", schoolCode='" + schoolCode + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", teacher=" + teacher +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
