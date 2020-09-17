package com.example.gradetracker_teamk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gradetracker_teamk.Model.Assignment;
import com.example.gradetracker_teamk.Model.Course;
import com.example.gradetracker_teamk.Model.User;
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
            Toast.makeText(getApplicationContext(), "UserId Not Found.", Toast.LENGTH_LONG).show();
            //make toast that says user was unsuccessfully signed in
            finish();
        }

        addCourse = findViewById(R.id.addCourseFAB);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddCourseActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

//        courseItems.add(new CourseItems("name", "local", "grade"));
        createRecyclerItemCards();
        buildRecyclerView();

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
                infoClick(position);
                //set alert to view all info for a course
//                Toast.makeText(getApplicationContext(), "Info click.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onEditClick(int position) {
                //send user to another activity where they can edit the course they clicked

                Course clickedCourse = db.getCourseNameByCourseName(courseItems.get(position).getCourseName(), userId);

                if(clickedCourse == null) {
                    Toast.makeText(getApplicationContext(), "Course Does not exist, please refresh.", Toast.LENGTH_LONG).show();
                }
                else {
//                    Intent intent = new Intent(getApplicationContext(), EditCourseActivity.class);
//                    intent.putExtra("courseId", clickedCourse.getCourseId());
//                    startActivity(intent);
                }

                Toast.makeText(getApplicationContext(), "Edit click.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDeleteClick(int position) {
                //set alert for the user if they want to confirm to delete the clicked course
                remove(position);
//                Toast.makeText(getApplicationContext(), "Delete click.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCourseNameClick(int position) {

                Course clickedCourse = db.getCourseNameByCourseName(courseItems.get(position).getCourseName(), userId);

                Intent intent = new Intent(getApplicationContext(), viewAllAssignmentsActivity.class);
                intent.putExtra("courseId", clickedCourse.getCourseId());
                intent.putExtra("userId", userId);
                startActivity(intent);

//                Toast.makeText(getApplicationContext(), clickedCourse.getCourseName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createRecyclerItemCards() {
        List<Course> userCourses;
        userCourses = db.getCoursesByUserId(userId);

        for(Course c : userCourses) {
            courseItems.add(new CourseItems(c.getCourseName(), c.getLocation(), c.getGrade()));
        }
//        Log.d(this.getClass().toString(), courseItems.size()+"");
    }

    private void infoClick(int position) {
        String courseName = courseItems.get(position).getCourseName();

        Course courseClicked = db.getCourseNameByCourseName(courseName, userId);
        setInfoAlert(courseClicked.getCourseId());
    }

    private void remove(int position) {
        String courseName = courseItems.get(position).getCourseName();

        Course clickedCourse = db.getCourseNameByCourseName(courseName, userId);

        setDeleteAlert(position, clickedCourse);
    }

    private void setInfoAlert(int courseId) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        Course course = db.getCourseByCourseId(courseId);

        String courseInfo = "Course Name: " + course.getCourseName() + "\n" +
                            "Subject: " + course.getSubject() + "\n" +
                            "Location: " + course.getLocation() + "\n" +
                            "Instructor: " + course.getInstructor() + "\n" +
                            "Description: " + course.getDescription() + "\n";

        alert.setMessage(courseInfo);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.setCancelable(true);

        alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        alert.create().show();
    }

    private void setDeleteAlert(int position, Course course) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("Are you sure you want to delete " + course.getCourseName() + " doing so will also delete all assignments attached to said course.");

        alert.setPositiveButton("OK, Delete Course", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(Assignment assignment : db.getAssignmentByCourseId(course.getCourseId())) {
                    db.deleteAssignment(assignment);
                }
                db.deleteCourse(course);
//                Toast.makeText(getApplicationContext(), "Course and Assignments successfully deleted.", Toast.LENGTH_LONG).show();

                List<Assignment> deletedAssignments =  db.getAssignmentByCourseId(course.getCourseId());
                if(deletedAssignments.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Course and Assignments successfully deleted.", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Course deleted, but no Assignments deleted.", Toast.LENGTH_LONG).show();
                }
                courseItems.remove(position);
                courseAdapter.notifyItemRemoved(position);
            }

        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.setCancelable(true);

        alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        alert.create().show();
    }

}