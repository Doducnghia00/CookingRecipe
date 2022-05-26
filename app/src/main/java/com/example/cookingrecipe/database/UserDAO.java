package com.example.cookingrecipe.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cookingrecipe.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user")
    List<User> getUser();

    @Query("DELETE FROM user")
    void deleteAll();

    @Insert
    void insertUser(User user);
}
