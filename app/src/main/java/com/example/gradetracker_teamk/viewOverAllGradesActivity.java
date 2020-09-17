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
import android.service.autofill.TextValueSanitizer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gradetracker_teamk.Model.Assignment;
import com.example.gradetracker_teamk.Model.Course;
import com.example.gradetracker_teamk.Model.User;
import com.example.gradetracker_teamk.RecyclerView.CourseAdapter;
import com.example.gradetracker_teamk.RecyclerView.CourseItems;
import com.example.gradetracker_teamk.RecyclerView.GradesAdapter;
import com.example.gradetracker_teamk.RecyclerView.GradesItem;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.AutofillService;
import android.widget.Toast;

import com.example.gradetracker_teamk.Model.Course;
import com.example.gradetracker_teamk.RecyclerView.CourseAdapter;
import com.example.gradetracker_teamk.RecyclerView.CourseItems;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;

import java.util.ArrayList;
import java.util.List;

public class viewOverAllGradesActivity extends AppCompatActivity {
    private ArrayList<GradesItem> gradesItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GradesAdapter gradesAdapter;
    private int userId;
    private TextView gpa;

    UsersDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_over_all_grades);

        db = AppDataBase.getInstance(getApplicationContext()).getUsersDAO();
        userId = getIntent().getIntExtra("userId", -1);
        gpa = findViewById(R.id.overall_text2);
        gpa.setText(calculateGPA().toString());


        createRecyclerItemCards();
        buildRecyclerView();


    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.overall_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        gradesAdapter = new GradesAdapter(gradesItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(gradesAdapter);
    }
    private void createRecyclerItemCards() {
        List<Course> userCourses;
        userCourses = db.getCoursesByUserId(userId);

        for(Course c : userCourses) {
            gradesItems.add(new GradesItem(c.getCourseName(), c.getGrade()));
        }
//        Log.d(this.getClass().toString(), courseItems.size()+"");
    }

    private Double calculateGPA() {
        List<Course> list = db.getCoursesByUserId(userId);
        Double gpaDub = 0.0;
        int count = 0;
        for(Course c : list){
            if(c.getGrade().startsWith("A")) {
                gpaDub += 4.0;
            }
            else if(c.getGrade().startsWith("B")) {
                gpaDub += 3.0;
            }
            else if(c.getGrade().startsWith("C")) {
                gpaDub += 2.0;
            }
            else if(c.getGrade().startsWith("D")) {
                gpaDub += 1.0;
            }
            else {
                gpaDub += 0.0;
            }
            count++;
        }
        return (gpaDub/count);
    }
}