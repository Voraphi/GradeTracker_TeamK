package com.example.gradetracker_teamk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gradetracker_teamk.Model.User;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;

public class HomePage extends AppCompatActivity {

    UsersDAO db;
    TextView name;
    Button viewCourses;
    Button viewOverAllGrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        db = AppDataBase.getInstance(getApplicationContext()).getUsersDAO();

        name = findViewById(R.id.username_goes_here);
        viewCourses = findViewById(R.id.courses_button);
        viewOverAllGrades = findViewById(R.id.grades_button);

        int userIdFromIntent = getIntent().getIntExtra("userid", -1);

        if(userIdFromIntent == -1) {
            //make toast that says user was unsuccessfully logged in
            finish();
        }
        else {
            User user = db.getUserByUserId(userIdFromIntent);
            name.setText(user.getName());
        }

        viewCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), viewAllCoursesActivity.class);
                intent.putExtra("userId", userIdFromIntent);
                startActivity(intent);
            }
        });

        viewOverAllGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), viewOverAllGradesActivity.class);
                intent.putExtra("userId", userIdFromIntent);
                startActivity(intent);
            }
        });






    }
}