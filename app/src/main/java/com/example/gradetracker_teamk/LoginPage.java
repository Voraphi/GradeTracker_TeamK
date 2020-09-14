package com.example.gradetracker_teamk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gradetracker_teamk.Model.User;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;

import java.util.ArrayList;
import java.util.List;

public class LoginPage extends AppCompatActivity {
    UsersDAO db;
    private Button log_in_button;
    private EditText username;
    private EditText password;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDataBase.getInstance(getApplicationContext()).getUsersDAO();
        setContentView(R.layout.activity_login_page);

        log_in_button = findViewById(R.id.home_button);
        username = findViewById(R.id.home_text_view_2);
        password = findViewById(R.id.home_text_view_3);
        getDataBase();

        log_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check_user;
                String check_pass;

                check_user = username.getText().toString();
                check_pass = password.getText().toString();

                User current_user = db.verifyCreds(check_user, check_pass);

                if(!(current_user == null)){
                    Intent intent = new Intent(LoginPage.this, HomePage.class);
                    intent.putExtra("userId", current_user.getUserId());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect credentials, try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void getDataBase() {
        db = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUsersDAO();
    }
}
