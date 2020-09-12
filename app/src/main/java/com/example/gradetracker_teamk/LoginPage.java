package com.example.gradetracker_teamk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class LoginPage extends AppCompatActivity {
    private Button log_button;

    private EditText username;
    private EditText password;


    class User {
        private String user;
        private String pass;

        public User(String user, String pass) {
            this.user = user;
            this.pass = pass;
        }
        public String getUser() {
            return user;
        }
        public void setUser(String user) {
            this.user = user;
        }
        public String getPass() {
            return pass;
        }
        public void setPass(String pass) {
            this.pass = pass;
        }
    }
    public static List<User> users = new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        users.add(new User("userone", "passone"));
        users.add(new User("usertwo", "passtwo"));
        users.add(new User("userthree", "passthree"));

        log_button = findViewById(R.id.home_button);
        username = findViewById(R.id.home_text_view_2);
        password = findViewById(R.id.home_text_view_3);

        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(LoginPage.this, MainActivity.class);
                    startActivity(intent);
            }
        });
    }

    boolean check_fields(EditText s1, EditText s2){
        String str1 = s1.getText().toString();
        String str2 = s2.getText().toString();
        for(User i:users){
            if (i.getUser().equals(str1)){
                if(i.getPass().equals(str2)){
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
