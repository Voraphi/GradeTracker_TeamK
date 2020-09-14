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

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private ArrayList<CourseItems> list;
    private OnItemClickedListener listener;

    public interface OnItemClickedListener {
        void onInfoClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);

    }

    public void setOnItemClickListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseNameView;
        public TextView courseLocationView;
        public TextView gradeView;
        public ImageView info_icon;
        public ImageView edit_icon;
        public ImageView delete_icon;

        public ViewHolder(@NonNull View itemView, final OnItemClickedListener listener) {
            super(itemView);
            courseNameView = itemView.findViewById(R.id.course);
            courseLocationView = itemView.findViewById(R.id.location);
            gradeView = itemView.findViewById(R.id.grade);
            info_icon = itemView.findViewById(R.id.image_info);
            edit_icon = itemView.findViewById(R.id.image_edit);
            delete_icon = itemView.findViewById(R.id.image_delete);

            info_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onInfoClick(position);
                        }
                    }
                }
            });

            edit_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });

            delete_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public CourseAdapter(ArrayList<CourseItems> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_items, parent, false);
        ViewHolder vh = new ViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseItems currentItem = list.get(position);

        holder.courseNameView.setText(currentItem.getCourseName());
        holder.courseLocationView.setText(currentItem.getLocation());
        holder.gradeView.setText(currentItem.getGrade());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
