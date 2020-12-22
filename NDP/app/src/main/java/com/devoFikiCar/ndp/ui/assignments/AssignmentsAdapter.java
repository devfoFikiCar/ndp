/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.ui.assignments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devoFikiCar.ndp.R;

import java.util.ArrayList;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.AssignmentsViewHolder>{
    ArrayList<AssignmentItem> arrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        mListener = listener;
    }

    public static class AssignmentsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvID;


        public AssignmentsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleLineAssignment);
            tvID = itemView.findViewById(R.id.tvIDLineAssignment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public AssignmentsAdapter(ArrayList<AssignmentItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AssignmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item, parent, false);
        AssignmentsViewHolder assignmentsViewHolder = new AssignmentsViewHolder(view, mListener);
        return assignmentsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentsViewHolder holder, int position) {
        AssignmentItem assignmentItem = arrayList.get(position);

        holder.tvTitle.setText(assignmentItem.getAssignmentTitle());
        holder.tvID.setText(assignmentItem.getAssignmentID());
    }

    @Override
    public int getItemCount() {
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }
}
