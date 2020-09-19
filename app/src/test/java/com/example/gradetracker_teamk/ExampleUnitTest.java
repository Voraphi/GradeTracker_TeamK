package com.example.gradetracker_teamk;

import android.content.Context;

import com.example.gradetracker_teamk.Model.Course;
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

        assertEquals("UserName", user.getUsername());

        user.setUsername("joseMr");
        assertEquals("joseMr", user.getUsername());

        assertEquals("password", user.getPassword());

        user.setPassword("Nunya");
        assertEquals("Nunya", user.getPassword());

        int userId = user.getUserId();

        assertEquals(userId, user.getUserId());

        user.setUserId(6);
        assertEquals(6, user.getUserId());

    }

    @Test
    public void course_tests() {
        Course course = new Course("courseName", "subject", "location", "instructor", "description", 999);

        assertEquals("courseName", course.getCourseName());

        course.setCourseName("newname");
        assertEquals("newname", course.getCourseName());

        course.setLocation("newloc");
        assertEquals("newloc", course.getLocation());

        course.setInstructor("newinst");
        assertEquals("newinst", course.getInstructor());

        course.setDescription("newdesc");
        assertEquals("newdesc", course.getDescription());

        course.setCourseId(123);
        assertEquals(123, course.getCourseId());


    }



}