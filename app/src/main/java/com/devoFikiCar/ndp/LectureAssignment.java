/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.devoFikiCar.ndp.ui.lectureassignment.LectureAssignmentFragment;

public class LectureAssignment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecture_assignment_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LectureAssignmentFragment.newInstance())
                    .commitNow();
        }
    }
}