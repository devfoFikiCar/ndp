/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2021
 */

package com.devoFikiCar.ndp.ui.statistics.specific.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.ui.statistics.specific.TaskStatsAdapter;
import com.devoFikiCar.ndp.ui.statistics.specific.TaskStatsItem;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StatisticsSpecificStudentFragment extends Fragment {

    private StatisticsSpecificStudentViewModel mViewModel;
    private RecyclerView recyclerView;
    private TaskStatsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<TaskStatsItem> taskStatsItems = new ArrayList<>();
    private FirebaseFirestore firestore;
    private TextView tvScore;

    public static StatisticsSpecificStudentFragment newInstance() {
        return new StatisticsSpecificStudentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.statistics_specific_student_fragment, container, false);

        Bundle bundle = this.getArguments();
        int position = bundle.getInt("position");

        mViewModel = new ViewModelProvider(this).get(StatisticsSpecificStudentViewModel.class);
        firestore = FirebaseFirestore.getInstance();
        mViewModel.getSubmission(firestore, position);

        buildRecyclerView(root);

        tvScore = root.findViewById(R.id.tvScoreStatsSpecificStudent);

        mViewModel.getScore().observe(getViewLifecycleOwner(), scoreUpdate);
        mViewModel.getTaskStatsItemMutableLiveData().observe(getViewLifecycleOwner(), taskStatsList);

        return root;
    }

    private void buildRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.rvTasksListStatsSpecificStudent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new TaskStatsAdapter(taskStatsItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TaskStatsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                System.out.println("clicked");
            }
        });
    }

    final Observer<ArrayList<TaskStatsItem>> taskStatsList = new Observer<ArrayList<TaskStatsItem>>() {
        @Override
        public void onChanged(ArrayList<TaskStatsItem> taskStatsItems1) {
            if (taskStatsItems != null) {
                taskStatsItems.clear();
            }
            for (int i = 0; i < taskStatsItems1.size(); i++) {
                taskStatsItems.add(taskStatsItems1.get(i));
            }
            adapter.notifyDataSetChanged();
        }
    };

    final Observer<String> scoreUpdate = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            if (tvScore != null && s != null) {
                tvScore.setText(s);
            }
        }
    };
}