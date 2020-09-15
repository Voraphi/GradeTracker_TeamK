package com.example.gradetracker_teamk;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.gradetracker_teamk.Model.User;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

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
    public void writeUserAndReadInList() throws Exception {
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


    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.gradetracker_teamk", appContext.getPackageName());
    }
}