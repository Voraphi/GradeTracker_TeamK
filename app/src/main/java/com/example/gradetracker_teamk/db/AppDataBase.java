package com.example.gradetracker_teamk.db;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gradetracker_teamk.Model.Assignment;
import com.example.gradetracker_teamk.Model.Course;
import com.example.gradetracker_teamk.Model.User;

@Database(entities = {User.class, Course.class, Assignment.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public static final String DB_NAME = "GRADE_TRACKER_DATABASE";
    public static final String USERS_TABLE = "USERS_TABLE";
    public static final String COURSES_TABLE = "COURSES_TABLE";
    public static final String ASSIGNMENTS_TABLE = "ASSIGNMENTS_TABLE";

    private static AppDataBase instance;

    public abstract UsersDAO getUsersDAO();

    public static synchronized AppDataBase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDataBase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
