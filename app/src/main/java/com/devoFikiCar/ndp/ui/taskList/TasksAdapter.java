/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.taskList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devoFikiCar.ndp.R;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {
    ArrayList<TaskItem> arrayList;
    private OnItemClickListener mListener;

    public TasksAdapter(ArrayList<TaskItem> arrayList) {
        this.arrayList = arrayList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        TasksViewHolder tasksViewHolder = new TasksViewHolder(view, mListener);
        return tasksViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        TaskItem taskItem = arrayList.get(position);

        holder.tvNumber.setText(taskItem.getTaskNumber());
        holder.tvStatus.setText(taskItem.getTaskStatus());
    }

    @Override
    public int getItemCount() {
        if (arrayList == null)
            return 0;
        return arrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class TasksViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNumber;
        public TextView tvStatus;

        public TasksViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvTaskNumber);
            tvStatus = itemView.findViewById(R.id.tvStatusTask);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
