package com.example.gradetracker_teamk.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gradetracker_teamk.Model.User;

@Dao
public interface UsersDAO {
    @Insert
    void inesertUser(User user);

    @Query("SELECT * FROM " + AppDataBase.USERS_TABLE + " WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDataBase.USERS_TABLE + " WHERE userId = :userId")
    User getUserByUserId(int userId);

}
