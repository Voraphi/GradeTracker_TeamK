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
import com.example.gradetracker_teamk.RecyclerView.AssignmentAdapter;
import com.example.gradetracker_teamk.RecyclerView.AssignmentItems;
import com.example.gradetracker_teamk.RecyclerView.CourseAdapter;
import com.example.gradetracker_teamk.RecyclerView.CourseItems;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class viewAllAssignmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AssignmentAdapter assignmentAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private UsersDAO db;
    private ArrayList<AssignmentItems> assignmentItems = new ArrayList<>();
    private int userId;
    private int courseId;
    private FloatingActionButton addAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_assignments);

        db = AppDataBase.getInstance(getApplicationContext()).getUsersDAO();

        userId = getIntent().getIntExtra("userId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);

        if(userId == -1 || courseId == -1) {
            //make toast that says user was unsuccessfully signed in
            finish();
        }

        addAssignment = findViewById(R.id.addAssignmentFAB);

        addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddAssignmentActivity.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

//        courseItems.add(new CourseItems("name", "local", "grade"));
        createRecyclerItemCards();
        buildRecyclerView();

    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.viewRecyclerViewAssignments);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        assignmentAdapter = new AssignmentAdapter(assignmentItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(assignmentAdapter);

        assignmentAdapter.setOnItemClickListener(new AssignmentAdapter.OnItemClickedListener() {
            @Override
            public void onInfoClick(int position) {
                //set alert to view all info for a course
//                Toast.makeText(getApplicationContext(), "Info click.", Toast.LENGTH_LONG).show();
                infoClick(position);
            }

            @Override
            public void onEditClick(int position) {
                //send user to another activity where they can edit the course they clicked

                Assignment clickedAssignment = db.getAssignmentByAssignmentName(assignmentItems.get(position).getAssignment(), courseId);

                if(clickedAssignment == null) {
                    Toast.makeText(getApplicationContext(), "Assignment Does not exist, please refresh.", Toast.LENGTH_LONG).show();
                }
                else {
//                    Intent intent = new Intent(getApplicationContext(), EditCourseActivity.class);
//                    intent.putExtra("courseId", clickedCourse.getCourseId());
//                    startActivity(intent);
                }

//                Toast.makeText(getApplicationContext(), "Edit click.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDeleteClick(int position) {
                //set alert for the user if they want to confirm to delete the clicked course
                remove(position);
//                Toast.makeText(getApplicationContext(), "Delete click.", Toast.LENGTH_LONG).show();
            }

        });
    }



    private void createRecyclerItemCards() {
        List<Assignment> courseAssignments;
        courseAssignments = db.getAssignmentByCourseId(courseId);

        for(Assignment a : courseAssignments) {
            assignmentItems.add(new AssignmentItems(a.getAssignment(), a.getDateDue(), a.getEarnedScore(), a.getMaxScore()));
        }
//        Log.d(this.getClass().toString(), courseItems.size()+"");
    }

    private void infoClick(int position) {
        String assignment = assignmentItems.get(position).getAssignment();

        Assignment assignmentClicked = db.getAssignmentByAssignmentName(assignment, courseId);
        setInfoAlert(assignmentClicked.getAssignmentId());
    }

    private void remove(int position) {
        String assignment = assignmentItems.get(position).getAssignment();

        Assignment clickedAssignment = db.getAssignmentByAssignmentName(assignment, courseId);

        setDeleteAlert(position, clickedAssignment);
    }

    private void setInfoAlert(int assignmentId) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        Assignment assignment = db.getAssignmentById(assignmentId);

        String assignmentInfo = "Assignment: " + assignment.getAssignment() + "\n" +
                "Due Date: " + assignment.getDateDue() + "\n" +
                "Earned Score: " + assignment.getEarnedScore() + "\n" +
                "Max Score: " + assignment.getMaxScore() + "\n" +
                "Description: " + assignment.getDescription() + "\n";

        alert.setMessage(assignmentInfo);

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

    private void setDeleteAlert(int position, Assignment assignment) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("Are you sure you want to delete " + assignment.getAssignment() + " assignment?");

        alert.setPositiveButton("OK, Delete Assignment", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Assignment deletedAssignment = db.getAssignmentById(assignment.getAssignmentId());
                db.deleteAssignment(deletedAssignment);
                updateCourseGrade(getApplicationContext(), courseId);

                Toast.makeText(getApplicationContext(), "Assignment successfully deleted.", Toast.LENGTH_LONG).show();

                assignmentItems.remove(position);
                assignmentAdapter.notifyItemRemoved(position);
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

    public static void updateCourseGrade(Context context, int courseId) {
        UsersDAO db = AppDataBase.getInstance(context).getUsersDAO();
        List<Assignment> assignmentList = db.getAssignmentByCourseId(courseId);

        if(!assignmentList.isEmpty()) {

            double earnedScore = 0;
            int maxScore = 0;

            for(Assignment assignment : assignmentList) {
                earnedScore += assignment.getEarnedScore();
                maxScore += assignment.getMaxScore();
            }

            double gradeDouble =  (earnedScore / maxScore) * 100;
            String gradeString = "N/A";

            if(gradeDouble >= 90) {
                gradeString = "A";

                if(gradeDouble < 94) {
                    gradeString += "-";
                }
                else if(gradeDouble >= 97) {
                    gradeString += "+";
                }
            }
            else if(gradeDouble < 60) {
                gradeString = "F";
            }
            else {
                if(gradeDouble >= 80) {
                    gradeString = "B";

                    if(gradeDouble < 84) {
                        gradeString += "-";
                    }
                    else if(gradeDouble >= 87) {
                        gradeString += "+";
                    }
                }
                else if(gradeDouble >= 70) {
                    gradeString = "C";

                    if(gradeDouble < 74) {
                        gradeString += "-";
                    }
                    else if(gradeDouble >= 77) {
                        gradeString += "+";
                    }
                }
                else {
                    gradeString = "D";

                    if(gradeDouble < 64) {
                        gradeString += "-";
                    }
                    else if(gradeDouble >= 67) {
                        gradeString += "+";
                    }
                }
            }

            Course updatedCourse = db.getCourseByCourseId(courseId);
            updatedCourse.setGrade(gradeString);
            db.updateCourse(updatedCourse);

        }
    }

}