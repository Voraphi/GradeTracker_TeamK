package com.example.gradetracker_teamk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gradetracker_teamk.Model.Course;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;

public class EditCourseActivity extends AppCompatActivity {

    private UsersDAO db;
    private int courseId;
    private int userId;
    private Button editButton;
    private EditText courseName;
    private EditText subject;
    private EditText location;
    private EditText instructor;
    private EditText courseDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        db = AppDataBase.getInstance(getApplicationContext()).getUsersDAO();
        wireUp();

        editButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(courseName.getText().toString().isEmpty() || subject.getText().toString().isEmpty() ||
                        location.getText().toString().isEmpty() || instructor.getText().toString().isEmpty() || courseDescription.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Make sure no fields are empty.", Toast.LENGTH_LONG).show();
                }
                else {
                    Course course = db.getCourseByCourseId(courseId);
                    course.setSubject(subject.getText().toString());
                    course.setDescription(courseDescription.getText().toString());
                    course.setLocation(location.getText().toString());
                    course.setInstructor(instructor.getText().toString());

                    if (courseName.getText().toString().equals(course.getCourseName())){
                        db.updateCourse(course);
                        Toast.makeText(getApplicationContext(), "Course Successfully updated!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                    else{
                        Course currentCourseName = db.getCourseNameByCourseName(courseName.getText().toString(), userId);
                        if(currentCourseName == null) {
                            course.setCourseName(courseName.getText().toString());
                            db.updateCourse(course);
                            Toast.makeText(getApplicationContext(), "Course Successfully updated!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            intent.putExtra("userId", userId);
                            startActivity(intent);
                        }
                    }


                }
            }
        });
    }
    private void wireUp(){
        courseName = findViewById(R.id.nameOfCourseEditText);
        subject = findViewById(R.id.subjectEditText);
        location = findViewById(R.id.locationEditText);
        courseDescription = findViewById(R.id.courseDescriptionEditText);
        editButton = findViewById(R.id.editCourseButton);
        instructor = findViewById(R.id.instructorEditText);

        userId = getIntent().getIntExtra("userId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);
        Course course = db.getCourseByCourseId(courseId);
        courseName.setText(course.getCourseName());
        subject.setText(course.getSubject());
        location.setText(course.getLocation());
        courseDescription.setText(course.getDescription());
        instructor.setText(course.getInstructor());

    }
}