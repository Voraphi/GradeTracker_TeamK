package com.example.gradetracker_teamk.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gradetracker_teamk.Model.Course;
import com.example.gradetracker_teamk.Model.User;

import java.util.List;

@Dao
public interface UsersDAO {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM " + AppDataBase.USERS_TABLE + " WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDataBase.USERS_TABLE + " WHERE userId = :userId")
    User getUserByUserId(int userId);

    @Query("SELECT * FROM " + AppDataBase.USERS_TABLE + " WHERE username = :username and password = :password")
    User verifyCreds(String username, String password);

    @Query("SELECT password FROM " + AppDataBase.USERS_TABLE + " WHERE username = :username")
    String getPassByUser(String username);

    @Insert
    void insertCourse(Course course);

    @Query("SELECT * FROM " + AppDataBase.COURSES_TABLE + " WHERE userId = :userId")
    List<Course> getCoursesByUserId(int userId);

}
