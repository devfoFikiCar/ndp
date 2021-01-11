/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2021
 */

package com.devoFikiCar.ndp.ui.statistics.specific.teacher;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.ui.statistics.specific.TaskStatsAdapter;
import com.devoFikiCar.ndp.ui.statistics.specific.TaskStatsItem;
import com.devoFikiCar.ndp.ui.statistics.specific.student.StatisticsSpecificStudentFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StatisticsSpecificTeacherFragment extends Fragment {

    private StatisticsSpecificTeacherViewModel mViewModel;
    private RecyclerView recyclerView;
    private TaskStatsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<TaskStatsItem> taskStatsItems = new ArrayList<>();
    private FirebaseFirestore firestore;
    private Button btDone;
    private String username;
    private int position = 0;

    public static StatisticsSpecificTeacherFragment newInstance() {
        return new StatisticsSpecificTeacherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.statistics_specific_teacher_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(StatisticsSpecificTeacherViewModel.class);

        Bundle bundle = this.getArguments();
        position = bundle.getInt("position");

        firestore = FirebaseFirestore.getInstance();
        buildRecyclerView(root);

        mViewModel.getSubmission(firestore, position);

        btDone = root.findViewById(R.id.btOkStatsSpecificTeacher);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        mViewModel.getTaskStatsItemMutableLiveData().observe(getViewLifecycleOwner(), taskStatsList);

        return root;
    }

    private void buildRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.rvTasksListStatsSpecificTeacher);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new TaskStatsAdapter(taskStatsItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TaskStatsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int pos) {
                // mViewModel.displayDetails(getActivity(), getContext(), position);
                System.out.println("clicked");
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("username", mViewModel.getStudentName(pos));

                StatisticsSpecificStudentFragment statisticsSpecificStudentFragment = new StatisticsSpecificStudentFragment();
                statisticsSpecificStudentFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, statisticsSpecificStudentFragment)
                        .commit();


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
}