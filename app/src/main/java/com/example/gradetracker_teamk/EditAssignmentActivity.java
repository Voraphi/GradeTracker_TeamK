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

import com.example.gradetracker_teamk.Model.Assignment;
import com.example.gradetracker_teamk.Model.Course;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;

public class EditAssignmentActivity extends AppCompatActivity {

    private int courseId;
    private int assignmentId;
    private int userId;
    private EditText assignmentName;
    private EditText earnedScore;
    double earned;
    private EditText maxScore;
    int max;
    private EditText dueDate;
    private EditText assignmentDescription;
    private Button editAssignment;
    private UsersDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        db = AppDataBase.getInstance(getApplicationContext()).getUsersDAO();
        wireUp();

        editAssignment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(assignmentName.getText().toString().isEmpty() || earnedScore.getText().toString().isEmpty() ||
                        maxScore.getText().toString().isEmpty() || dueDate.getText().toString().isEmpty() ||
                        assignmentName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Make sure no fields are empty.", Toast.LENGTH_LONG).show();
                }
                else {
                    try{
                        earned = Double.parseDouble(earnedScore.getText().toString());
                        max = Integer.parseInt(maxScore.getText().toString());
                    }
                    catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Make sure all your scores are numbers.", Toast.LENGTH_LONG).show();
                    }
                    Assignment assignment = db.getAssignmentById(assignmentId);
                    assignment.setEarnedScore(earned);
                    assignment.setMaxScore(max);
                    assignment.setDateDue(dueDate.getText().toString());
                    assignment.setDescription(assignmentDescription.getText().toString());


                    if (assignmentName.getText().toString().equals(assignment.getAssignment())){
                        db.updateAssignment(assignment);
                        Toast.makeText(getApplicationContext(), "Assignment Successfully updated!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }
                    else{
                        Assignment currentAssignmentName = db.getAssignmentByAssignmentName(assignmentName.getText().toString(), courseId);
                        if(currentAssignmentName == null) {
                            assignment.setAssignment(assignmentName.getText().toString());
                            assignment.setAssignment(assignmentName.getText().toString());
                            db.updateAssignment(assignment);
                            Toast.makeText(getApplicationContext(), "Assignment Successfully updated!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            intent.putExtra("userId", userId);
                            startActivity(intent);
                        }
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
        editAssignment = findViewById(R.id.editAssignmentButton);

        //get course info from the database through the courseId

        userId = getIntent().getIntExtra("userId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);
        assignmentId = getIntent().getIntExtra("assignmentId", -1);
        Assignment assignment = db.getAssignmentById(assignmentId);
        assignmentName.setText(assignment.getAssignment());
        earnedScore.setText(assignment.getEarnedScore() + "" );
        maxScore.setText(assignment.getMaxScore() + "");
        dueDate.setText(assignment.getDateDue());
        assignmentDescription.setText(assignment.getDescription());
        
    }
}