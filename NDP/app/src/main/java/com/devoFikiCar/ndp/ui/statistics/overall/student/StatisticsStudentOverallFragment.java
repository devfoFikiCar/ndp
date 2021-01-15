/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.statistics.overall.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.helper.userSave;
import com.devoFikiCar.ndp.ui.statistics.overall.UserStatsAdapter;
import com.devoFikiCar.ndp.ui.statistics.overall.UserStatsItem;
import com.devoFikiCar.ndp.ui.statistics.specific.student.StatisticsSpecificStudentFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StatisticsStudentOverallFragment extends Fragment {

    private StatisticsStudentOverallViewModel mViewModel;
    private FirebaseFirestore firestore;
    private String username;
    private RecyclerView recyclerView;
    private UserStatsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<UserStatsItem> userStatsItems = new ArrayList<>();
    private Button btDone;

    public static StatisticsStudentOverallFragment newInstance() {
        return new StatisticsStudentOverallFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.statistics_student_overall_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(StatisticsStudentOverallViewModel.class);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            username = bundle.getString("username", userSave.user.getUsername());
        } else {
            username = userSave.user.getUsername();
        }

        firestore = FirebaseFirestore.getInstance();
        mViewModel.getAssignmentsWithTotalScore(firestore, getContext(), username);

        buildRecyclerView(root);

        btDone = root.findViewById(R.id.btOkStatsOverallUser);
        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        mViewModel.getUserStatsItems().observe(getViewLifecycleOwner(), userStatsList);

        return root;
    }

    private void buildRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.rvTasksListStatsOverallStudent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new UserStatsAdapter(userStatsItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new UserStatsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                System.out.println("Clicked: " + position);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("username", username);

                StatisticsSpecificStudentFragment statisticsSpecificStudentFragment = new StatisticsSpecificStudentFragment();
                statisticsSpecificStudentFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, statisticsSpecificStudentFragment)
                        .addToBackStack("StatisticsStudentOverallFragment")
                        .commit();
            }
        });
    }

    final Observer<ArrayList<UserStatsItem>> userStatsList = new Observer<ArrayList<UserStatsItem>>() {
        @Override
        public void onChanged(ArrayList<UserStatsItem> userStatsItems1) {
            if (userStatsItems != null) {
                userStatsItems.clear();
            }
            for (int i = 0; i < userStatsItems1.size(); i++) {
                userStatsItems.add(userStatsItems1.get(i));
            }
            adapter.notifyDataSetChanged();
        }
    };
}