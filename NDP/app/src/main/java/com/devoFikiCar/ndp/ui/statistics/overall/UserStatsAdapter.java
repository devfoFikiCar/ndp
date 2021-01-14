/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.statistics.overall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devoFikiCar.ndp.R;

import java.util.ArrayList;

public class UserStatsAdapter extends RecyclerView.Adapter<UserStatsAdapter.UserStatsViewHolder> {
    ArrayList<UserStatsItem> arrayList;
    private OnItemClickListener mListener;

    public UserStatsAdapter(ArrayList<UserStatsItem> arrayList) {
        this.arrayList = arrayList;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        mListener = listener;
    }

    public static class UserStatsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;

        public UserStatsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvAssignmentTitleUser);

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

    @NonNull
    @Override
    public UserStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_stats_item, parent, false);
        UserStatsViewHolder userStatsViewHolder = new UserStatsViewHolder(view, mListener);
        return userStatsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserStatsViewHolder holder, int position) {
        UserStatsItem userStatsItem = arrayList.get(position);

        holder.tvTitle.setText(userStatsItem.getAssignmentTitle());
    }

    @Override
    public int getItemCount() {
        if (arrayList == null) {
            return 0;
        } else return arrayList.size();
    }
}
