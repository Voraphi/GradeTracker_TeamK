package com.example.gradetracker_teamk.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gradetracker_teamk.Model.Assignment;
import com.example.gradetracker_teamk.Model.Course;
import com.example.gradetracker_teamk.Model.User;

import java.util.List;

@Dao
public interface UsersDAO {
    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

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

    @Delete
    void deleteCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Query("SELECT * FROM " + AppDataBase.COURSES_TABLE + " WHERE userId = :userId")
    List<Course> getCoursesByUserId(int userId);

    @Query("SELECT * FROM " + AppDataBase.COURSES_TABLE + " WHERE courseName = :courseName and userId = :userId")
    Course getCourseNameByCourseName(String courseName, int userId);

    @Query("SELECT * FROM " + AppDataBase.COURSES_TABLE + " WHERE courseId = :courseId")
    Course getCourseByCourseId(int courseId);

    @Query("UPDATE " + AppDataBase.COURSES_TABLE + " SET grade = :grade WHERE courseId = :courseId")
    void updateGradeForCourse(String grade, int courseId);

    @Insert
    void insertAssignment(Assignment assignment);

    @Delete
    void deleteAssignment(Assignment assignment);

    @Query("SELECT * FROM " + AppDataBase.ASSIGNMENTS_TABLE + " WHERE courseId = :courseId")
    List<Assignment> getAssignmentByCourseId(int courseId);

    @Query("SELECT * FROM " + AppDataBase.ASSIGNMENTS_TABLE + " WHERE assignment = :assignment and courseId = :courseId")
    Assignment getAssignmentByAssignmentName(String assignment, int courseId);

    @Query("SELECT * FROM " + AppDataBase.ASSIGNMENTS_TABLE + " WHERE assignmentId = :assignmentId")
    Assignment getAssignmentById(int assignmentId);




}
