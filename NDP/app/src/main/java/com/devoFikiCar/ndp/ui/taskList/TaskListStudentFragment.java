/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.taskList;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.helper.tempStorage;
import com.devoFikiCar.ndp.ui.taskEditor.student.TaskEditorStudentFragment;
import com.devoFikiCar.ndp.util.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class TaskListStudentFragment extends Fragment {

    private TaskListStudentViewModel mViewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private TasksAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private final ArrayList<TaskItem> taskItems = new ArrayList<>();
    final Observer<ArrayList<Task>> tasksList = new Observer<ArrayList<Task>>() {
        @Override
        public void onChanged(ArrayList<Task> tasks) {
            if (taskItems != null) {
                taskItems.clear();
            }
            for (int i = 0; i < tasks.size(); i++) {
                taskItems.add(new TaskItem("Task " + (i + 1), "IN PROGRESS"));
            }
            adapter.notifyDataSetChanged();
        }
    };
    private Button btSubmit;
    private TextView tvTimeLeft;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 6000000;
    private long timeLeftInMillisecondsCheck = timeLeftInMilliseconds;
    final Observer<Long> timeLeftVM = new Observer<Long>() {
        @Override
        public void onChanged(Long aLong) {
            if (aLong != timeLeftInMillisecondsCheck) {
                timeLeftInMilliseconds = aLong;
                timeLeftInMillisecondsCheck = timeLeftInMilliseconds;
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                startTimer();
                updateTimer();
            }
        }
    };
    private int assignmentPosition = -1;
    final Observer<ArrayList<HashMap<String, String>>> scoresData = new Observer<ArrayList<HashMap<String, String>>>() {
        @Override
        public void onChanged(ArrayList<HashMap<String, String>> hashMaps) {
            if (hashMaps != null) {
                if (btSubmit != null) {
                    for (HashMap<String, String> d : hashMaps) {
                        if (d.containsKey(mViewModel.getUser().getUsername())) {
                            btSubmit.setEnabled(false);
                            System.out.println(d.toString());
                            if (assignmentPosition != -1) {
                                mViewModel.openSpecificStudentStatistics(assignmentPosition, getActivity());
                            }
                        }
                    }
                }
            }
        }
    };

    public static TaskListStudentFragment newInstance() {
        return new TaskListStudentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_list_student_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(TaskListStudentViewModel.class);
        firestore = FirebaseFirestore.getInstance();

        Bundle bundle = this.getArguments();
        assignmentPosition = (int) bundle.get("position");
        tempStorage.assignmentPosition = assignmentPosition;

        mViewModel.loadTasks(firestore, getContext(), assignmentPosition, getActivity());

        buildRecyclerView(root);

        tvTimeLeft = root.findViewById(R.id.tvTimeLeft);
        updateTimer();

        btSubmit = root.findViewById(R.id.btSubmitTasks);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempStorage.tempCalendarEnd.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
                    mViewModel.submit(firestore, tempStorage.solutions.size(), getContext(), getActivity());
                } else {
                    Toast.makeText(getContext(), "Assignment has finished", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        mViewModel.getTasks().observe(getViewLifecycleOwner(), tasksList);
        mViewModel.getTimeLeftInMilliseconds().observe(getViewLifecycleOwner(), timeLeftVM);
        mViewModel.getScoresData().observe(getViewLifecycleOwner(), scoresData);

        return root;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                // nothing
            }
        }.start();
    }

    private void updateTimer() {
        int hours = (int) (timeLeftInMilliseconds / 1000) / 3600;
        int minutes = (int) ((timeLeftInMilliseconds / 1000) % 3600) / 60;
        tvTimeLeft.setText(hours + " hours and " + minutes + " minutes left");
        if (hours == 0 && minutes < 10) {
            tvTimeLeft.setTextColor(Color.RED);
        }
    }

    private void buildRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.rvTasksList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new TasksAdapter(taskItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TasksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("clicked");
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);

                TaskEditorStudentFragment taskEditorStudentFragment = new TaskEditorStudentFragment();
                taskEditorStudentFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, taskEditorStudentFragment)
                        .addToBackStack("TaskListStudentFragment")
                        .commit();
            }
        });
    }
}