package com.example.gradetracker_teamk.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradetracker_teamk.R;

import java.util.ArrayList;

public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.ViewHolder> {
    private ArrayList<GradesItem> list;
    private OnItemClickedListener listener;

    public interface OnItemClickedListener {
        void onInfoClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);
        void onCourseNameClick(int position);

    }

    public void setOnItemClickListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseNameView;
        public TextView gradeView;

        public ViewHolder(@NonNull View itemView, final OnItemClickedListener listener) {
            super(itemView);
            courseNameView = itemView.findViewById(R.id.course);
            gradeView = itemView.findViewById(R.id.grade);

        }
    }

    public GradesAdapter(ArrayList<GradesItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_items2, parent, false);
        ViewHolder vh = new ViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GradesItem currentItem = list.get(position);

        holder.courseNameView.setText(currentItem.getCourse_name());
        holder.gradeView.setText(currentItem.getGrade());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
