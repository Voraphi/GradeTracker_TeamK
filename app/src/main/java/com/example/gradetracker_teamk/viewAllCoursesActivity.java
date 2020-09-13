package com.example.gradetracker_teamk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.gradetracker_teamk.Model.Course;
import com.example.gradetracker_teamk.RecyclerView.CourseAdapter;
import com.example.gradetracker_teamk.RecyclerView.CourseItems;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class viewAllCoursesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private UsersDAO db;
    private ArrayList<CourseItems> courseItems = new ArrayList<>();
    private int userId;
    private FloatingActionButton addCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_courses);

        db = AppDataBase.getInstance(getApplicationContext()).getUsersDAO();

        userId = getIntent().getIntExtra("userId", -1);

        if(userId == -1) {
            //make toast that says user was unsuccessfully signed in
            finish();
        }

    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.viewRecyclerViewCourses);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        courseAdapter = new CourseAdapter(courseItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(courseAdapter);

        courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickedListener() {

            @Override
            public void onInfoClick(int position) {
                //set alert to view all info for a course
            }

            @Override
            public void onEditClick(int position) {
                //send user to another activity where they can edit the course they clicked
            }

            @Override
            public void onDeleteClick(int position) {
                //set alert for the user if they want to confirm to delete the clicked course
            }
        });
    }

    private void createRecyclerItemCards() {
        List<Course> userCourses = new ArrayList<>();
        userCourses = db.getCoursesByUserId(userId);

        for(Course c : userCourses) {
            courseItems.add(new CourseItems(c.getCourseName(), c.getLocation(), c.getGrade()));
        }

    }


}