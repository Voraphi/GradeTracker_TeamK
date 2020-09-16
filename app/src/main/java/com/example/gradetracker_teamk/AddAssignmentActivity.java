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

import com.example.gradetracker_teamk.Model.Assignment;
import com.example.gradetracker_teamk.Model.Course;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;

public class AddAssignmentActivity extends AppCompatActivity {

    private int courseId;
    private int userId;
    private EditText assignmentName;
    private EditText earnedScore;
    double earned;
    private EditText maxScore;
    int max;
    private EditText dueDate;
    private EditText assignmentDescription;
    private Button addAssignment;
    private UsersDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        courseId = getIntent().getIntExtra("courseId", -1);
        userId = getIntent().getIntExtra("userId", -1);

        db = AppDataBase.getInstance(getApplicationContext()).getUsersDAO();

        wireUp();

        addAssignment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(assignmentName.getText().toString().isEmpty() || earnedScore.getText().toString().isEmpty() ||
                        maxScore.getText().toString().isEmpty() || dueDate.getText().toString().isEmpty() ||
                        assignmentName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Make sure no fields are empty.", Toast.LENGTH_LONG).show();
                }
                else {

                    Assignment currentAssignmentName = db.getAssignmentByAssignmentName(assignmentName.getText().toString(), courseId);

                    if(currentAssignmentName == null) {
                        try{
                            earned = Double.parseDouble(earnedScore.getText().toString());
                            max = Integer.parseInt(maxScore.getText().toString());
                        }
                        catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(), "Make sure all your scores are numbers.", Toast.LENGTH_LONG).show();
                        }
                        Assignment newAssignment = new Assignment(assignmentName.getText().toString(), max, earned, assignmentDescription.getText().toString(), dueDate.getText().toString(), courseId);
                        db.inserAssignment(newAssignment);
//                        Log.d(this.getClass().toGenericString(), newCourse.toString());
                        Toast.makeText(getApplicationContext(), "Assignment Successfully created!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), viewAllCoursesActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Assignment Name Cannot Be The Same as one Previously entered.", Toast.LENGTH_LONG).show();

                    }


                }
            }
        });

    }

    private void wireUp() {
        assignmentName = findViewById(R.id.nameOfAssignmentEditText);
        earnedScore = findViewById(R.id.earnedScoreEditText);
        maxScore = findViewById(R.id.maxScoreEditText);
        dueDate = findViewById(R.id.dueDateEditText);
        assignmentDescription = findViewById(R.id.assignmentDescriptionEditText);
        addAssignment = findViewById(R.id.addAssignmentButton);

        //get course info from the database through the courseId
    }
}