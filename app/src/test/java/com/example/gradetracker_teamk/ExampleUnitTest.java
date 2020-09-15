package com.example.gradetracker_teamk;

import com.example.gradetracker_teamk.Model.User;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void getUser_Name() {
        User user = new User("Jesus", "UserName", "password");

        assertEquals("Jesus", user.getName());

        user.setName("jose");
        assertEquals("jose", user.getName());
    }

    @Test
    public void validatePassword() {
        User user = new User("Jesus Caballero", "voraphi", "iLikeCats6");
        user.setPassword("iDontLikeCats");

    }

}