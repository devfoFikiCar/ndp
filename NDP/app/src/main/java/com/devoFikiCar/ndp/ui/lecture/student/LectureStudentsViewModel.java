package com.devoFikiCar.ndp.ui.lecture.student;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devoFikiCar.ndp.helper.classSave;
import com.devoFikiCar.ndp.util.Classes;

import java.util.ArrayList;
import java.util.HashMap;

public class LectureStudentsViewModel extends ViewModel {
    private Classes classes;
    private MutableLiveData<ArrayList<HashMap<String, String>>> lectureIDs = new MutableLiveData<>();

    public LectureStudentsViewModel() {
        init();
    }

    private void init() {
        setClasses();
        loadLecturesOnStart();
    }

    private void loadLecturesOnStart() {
        setLectureIDs(this.classes.getLectureIDs());
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses() {
        this.classes = new Classes(classSave.classes);
        System.out.println(this.classes.toString());
    }

    public MutableLiveData<ArrayList<HashMap<String, String>>> getLectureIDs() {
        if (lectureIDs == null) {
            return new MutableLiveData<>();
        }
        return lectureIDs;
    }

    public void setLectureIDs(ArrayList<HashMap<String, String>> lectureIDs) {
        this.lectureIDs.postValue(lectureIDs);
    }
}