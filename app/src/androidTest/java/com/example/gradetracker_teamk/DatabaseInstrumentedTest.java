package com.example.gradetracker_teamk;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.gradetracker_teamk.Model.Assignment;
import com.example.gradetracker_teamk.Model.Course;
import com.example.gradetracker_teamk.Model.User;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {

    private UsersDAO usersDAO;
    private AppDataBase appDataBase;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        appDataBase = Room.inMemoryDatabaseBuilder(context, AppDataBase.class).build();
        usersDAO = appDataBase.getUsersDAO();
    }

    @After
    public void closeDb() throws IOException {
        appDataBase.close();
    }

    @Test
    public void userUnitTest() throws Exception {
        User user = new User("Jesus Caballero", "voraphi", "iLikeCats6");
        user.setUsername("josemr");
        usersDAO.insertUser(user);
        User byName = usersDAO.getUserByUsername("voraphi");
        assertNull(byName);
        User verifyCreds = usersDAO.verifyCreds("voraphi", user.getPassword());
        assertNull(verifyCreds);
        byName = usersDAO.getUserByUsername("josemr");
        assertEquals("josemr", byName.getUsername());
        verifyCreds = usersDAO.verifyCreds(user.getUsername(), user.getPassword());
        assertNotNull(verifyCreds);

        User userById = new User("Josey", "newUserName", "coolPassWord");
        userById.setUserId(69);
        usersDAO.insertUser(userById);
        userById = usersDAO.getUserByUsername("newUserName");
        User verifyByIdNotNull = usersDAO.getUserByUserId(userById.getUserId());
        assertNotNull(verifyByIdNotNull);

        usersDAO.deleteUser(userById);

        User verifyByIdNull = usersDAO.getUserByUserId(verifyByIdNotNull.getUserId());
        assertNull(verifyByIdNull);
    }

    @Test
    public void courseUnitTest() throws Exception {
        User user = new User("course Test", "courseTestUserName", "courseTestPassWord");

        usersDAO.insertUser(user);

        User userInDB = usersDAO.getUserByUsername("courseTestUserName");

        Course c1 = new Course("Course1", "Subject1", "Location1", "Instructor1", "Description1", userInDB.getUserId());
        Course c2 = new Course("Course2", "Subject2", "Location2", "Instructor2", "Description2", userInDB.getUserId());
        Course c3 = new Course("Course3", "Subject3", "Location3", "Instructor3", "Description3", userInDB.getUserId());

        usersDAO.insertCourse(c1);
        usersDAO.insertCourse(c2);
        usersDAO.insertCourse(c3);

        List<Course> userCourses = usersDAO.getCoursesByUserId(userInDB.getUserId());

        for (Course c : userCourses) {
            assertEquals(c.getCourseName(), usersDAO.getCourseNameByCourseName(c.getCourseName(), userInDB.getUserId()).getCourseName());
        }

        for (Course c : userCourses) {
            c.setInstructor("Dr. C");
            usersDAO.updateCourse(c);
        }

        userCourses = usersDAO.getCoursesByUserId(userInDB.getUserId());

        for (Course c : userCourses) {
            assertEquals("Dr. C", c.getInstructor());
        }

        for (Course c : userCourses) {
            usersDAO.deleteCourse(c);
        }

        userCourses = usersDAO.getCoursesByUserId(userInDB.getUserId());

        for (Course c : userCourses) {
            assertNull(c);
        }



    }

    @Test
    public void assignmentUnitTest() throws Exception {
        User user = new User("Assignment Test", "assignmentUserName", "assignmentTestPassWord");

        usersDAO.insertUser(user);

        User userInDB = usersDAO.getUserByUsername("assignmentUserName");

        Course c1 = new Course("Course1", "Subject1", "Location1", "Instructor1", "Description1", userInDB.getUserId());

        usersDAO.insertCourse(c1);

        Course courseInDB = usersDAO.getCourseNameByCourseName(c1.getCourseName(), userInDB.getUserId());

        Assignment assignment1 = new Assignment("Hw1", 100, 100, "problems", "09/18", courseInDB.getCourseId());

        usersDAO.insertAssignment(assignment1);

        List<Assignment> assignmentList = usersDAO.getAssignmentByCourseId(courseInDB.getCourseId());

        courseInDB.setGrade(viewAllAssignmentsActivity.updateCourseGradeUnitTest(assignmentList));

        usersDAO.updateCourse(courseInDB);

        courseInDB = usersDAO.getCourseNameByCourseName(c1.getCourseName(), userInDB.getUserId());

        assertEquals("A+", courseInDB.getGrade());

        Assignment assignment2 = new Assignment("Hw2", 100, 0, "problems", "09/18", courseInDB.getCourseId());

        usersDAO.insertAssignment(assignment2);

        assignmentList = usersDAO.getAssignmentByCourseId(courseInDB.getCourseId());

        courseInDB.setGrade(viewAllAssignmentsActivity.updateCourseGradeUnitTest(assignmentList));

        usersDAO.updateCourse(courseInDB);

        courseInDB = usersDAO.getCourseNameByCourseName(c1.getCourseName(), userInDB.getUserId());

        assertEquals("F", courseInDB.getGrade());

        Assignment delete = usersDAO.getAssignmentByAssignmentName("Hw2", courseInDB.getCourseId());

        usersDAO.deleteAssignment(delete);


        List<Assignment> assignmentList2 = usersDAO.getAssignmentByCourseId(courseInDB.getCourseId());

        courseInDB = usersDAO.getCourseNameByCourseName(c1.getCourseName(), userInDB.getUserId());

        courseInDB.setGrade(viewAllAssignmentsActivity.updateCourseGradeUnitTest(assignmentList2));

        usersDAO.updateCourse(courseInDB);

        courseInDB = usersDAO.getCourseNameByCourseName(c1.getCourseName(), userInDB.getUserId());

        assertEquals(1, assignmentList2.size());
        assertEquals("A+", courseInDB.getGrade());

    }

    @Test
    public void validatePassword() {
        Context context = ApplicationProvider.getApplicationContext();
        User user = new User("Jesus Kaba Lero", "fifi", "idontlikecats");
        user.setPassword("iDontLikeCats6");
        usersDAO.insertUser(user);

        assertTrue(Register.validatePasswordUnitTest(user.getPassword()));

    }


}