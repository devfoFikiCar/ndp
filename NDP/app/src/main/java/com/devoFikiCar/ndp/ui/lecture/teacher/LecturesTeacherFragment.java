package com.devoFikiCar.ndp.ui.lecture.teacher;

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
import com.devoFikiCar.ndp.ui.create.lecture.CreateLectureTeacherFragment;
import com.devoFikiCar.ndp.ui.lecture.LectureItem;
import com.devoFikiCar.ndp.ui.lecture.LecturesAdapter;
import com.devoFikiCar.ndp.ui.lecture.student.LecturesStudentsFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class LecturesTeacherFragment extends Fragment {

    private LecturesTeacherViewModel mViewModel;
    private RecyclerView recyclerView;
    private LecturesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<LectureItem> lectureItems = new ArrayList<>();
    private FirebaseFirestore firestore;
    private Button btCreateLecture;

    public static LecturesTeacherFragment newInstance() {
        return new LecturesTeacherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.lectures_teacher_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(LecturesTeacherViewModel.class);
        firestore = FirebaseFirestore.getInstance();
        mViewModel.lectureUpdate(firestore);

        buildRecyclerView(root);

        setUpButton(root);

        mViewModel.getLectureIDs().observe(getViewLifecycleOwner(), lecturesList);

        return root;
    }

    private void setUpButton(View root) {
        btCreateLecture = (Button) root.findViewById(R.id.btLecturesListTeacher);
        btCreateLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new CreateLectureTeacherFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void buildRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.rvLectureTeacher);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new LecturesAdapter(lectureItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new LecturesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("clicked");
            }
        });
    }

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
}