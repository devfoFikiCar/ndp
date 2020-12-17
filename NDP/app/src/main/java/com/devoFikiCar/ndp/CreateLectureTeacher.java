package com.devoFikiCar.ndp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.devoFikiCar.ndp.ui.create.lecture.CreateLectureTeacherFragment;

public class CreateLectureTeacher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_lecture_teacher_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CreateLectureTeacherFragment.newInstance())
                    .commitNow();
        }
    }
}