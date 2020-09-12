package com.example.gradetracker_teamk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.autofill.AutofillService;

import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;

public class viewOverAllGradesActivity extends AppCompatActivity {

    UsersDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_over_all_grades);

        db = AppDataBase.getInstance(getApplicationContext()).getUsersDAO();


    }
}