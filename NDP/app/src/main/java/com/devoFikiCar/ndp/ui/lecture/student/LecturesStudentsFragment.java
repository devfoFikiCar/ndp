/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.lecture.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.ui.lecture.LectureItem;
import com.devoFikiCar.ndp.ui.lecture.LecturesAdapter;
import com.devoFikiCar.ndp.ui.viewLecture.StudentViewLectureFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class LecturesStudentsFragment extends Fragment {

    private LectureStudentsViewModel mViewModel;
    private RecyclerView recyclerView;
    private LecturesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private final ArrayList<LectureItem> lectureItems = new ArrayList<>();
    final Observer<ArrayList<HashMap<String, String>>> lecturesList = new Observer<ArrayList<HashMap<String, String>>>() {
        @Override
        public void onChanged(ArrayList<HashMap<String, String>> hashMaps) {
            if (lectureItems != null) {
                lectureItems.clear();
            }
            for (int i = 0; i < hashMaps.size(); i++) {
                lectureItems.add(new LectureItem(hashMaps.get(i).get("lectureTitle")));
            }
            adapter.notifyDataSetChanged();
        }
    };
    private FirebaseFirestore firestore;

    public static LecturesStudentsFragment newInstance() {
        return new LecturesStudentsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.lectures_students_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(LectureStudentsViewModel.class);
        firestore = FirebaseFirestore.getInstance();
        mViewModel.lectureUpdate(firestore);

        buildRecyclerView(root);

        mViewModel.getLectureIDs().observe(getViewLifecycleOwner(), lecturesList);

        return root;
    }

    private void buildRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.rvLectureStudent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new LecturesAdapter(lectureItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new LecturesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("clicked");
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);

                StudentViewLectureFragment studentViewLectureFragment = new StudentViewLectureFragment();
                studentViewLectureFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, studentViewLectureFragment)
                        .addToBackStack("LecturesStudentsFragment")
                        .commit();
            }
        });
    }
}