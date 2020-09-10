package com.example.gradetracker_teamk.db;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.gradetracker_teamk.Model.User;

@Dao
public interface UsersDAO {
    @Insert
    void inesertUser(User user);
}
