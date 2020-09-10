package com.example.gradetracker_teamk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Application;
import android.os.Bundle;

import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDataBase.getInstance(getApplicationContext());

    }


}