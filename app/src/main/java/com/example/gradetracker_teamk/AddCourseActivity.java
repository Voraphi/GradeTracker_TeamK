package com.example.gradetracker_teamk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gradetracker_teamk.Model.Course;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;

public class AddCourseActivity extends AppCompatActivity {

    private int userId;
    private EditText nameOfCourse;
    private EditText subject;
    private EditText location;
    private EditText instructor;
    private EditText courseDescription;
    private Button addCourse;
    private UsersDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        userId = getIntent().getIntExtra("userId", -1);

        db = AppDataBase.getInstance(getApplicationContext()).getUsersDAO();

        wireUp();

        addCourse.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(nameOfCourse.getText().toString().isEmpty() || subject.getText().toString().isEmpty() ||
                        location.getText().toString().isEmpty() || instructor.getText().toString().isEmpty() ||
                        courseDescription.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Make sure no fields are empty.", Toast.LENGTH_LONG).show();
                }
                else {

                    Course currentCourseName = db.getCourseNameByCourseName(nameOfCourse.getText().toString(), userId);

                    if(currentCourseName == null) {
                        Course newCourse = new Course(nameOfCourse.getText().toString(), subject.getText().toString(), location.getText().toString(), instructor.getText().toString(), courseDescription.getText().toString(), userId);
                        db.insertCourse(newCourse);
                        Log.d(this.getClass().toGenericString(), newCourse.toString());
                        Toast.makeText(getApplicationContext(), "Course Successfully created!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Course Name Cannot Be The Same as one Previously entered.", Toast.LENGTH_LONG).show();

                    }


                }
            }
        });

    }

    private void wireUp() {
        nameOfCourse = findViewById(R.id.nameOfCourseEditText);
        subject = findViewById(R.id.subjectEditText);
        location = findViewById(R.id.locationEditText);
        instructor = findViewById(R.id.instructorEditText);
        courseDescription = findViewById(R.id.courseDescriptionEditText);
        addCourse = findViewById(R.id.addCourseButton);

        //get course info from the database through the courseId

    }
}