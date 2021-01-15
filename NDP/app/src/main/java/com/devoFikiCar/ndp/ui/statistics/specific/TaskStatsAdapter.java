/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.statistics.specific;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devoFikiCar.ndp.R;

import java.util.ArrayList;

public class TaskStatsAdapter extends RecyclerView.Adapter<TaskStatsAdapter.TaskStatsViewHolder> {
    ArrayList<TaskStatsItem> arrayList;
    private OnItemClickListener mListener;

    public TaskStatsAdapter(ArrayList<TaskStatsItem> taskStatsItems) {
        this.arrayList = taskStatsItems;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public TaskStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_stats_item, parent, false);
        return new TaskStatsViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskStatsViewHolder holder, int position) {
        TaskStatsItem taskStatsItem = arrayList.get(position);

        holder.tvNumber.setText(taskStatsItem.getTaskNumber());
        holder.tvScore.setText(taskStatsItem.getScore());
    }

    @Override
    public int getItemCount() {
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public static class TaskStatsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNumber;
        public TextView tvScore;


        public TaskStatsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvTaskNumberStats);
            tvScore = itemView.findViewById(R.id.tvScoreStats);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(position);
                    }
                }
            });
        }
    }
}
