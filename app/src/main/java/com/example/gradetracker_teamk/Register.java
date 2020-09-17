package com.example.gradetracker_teamk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gradetracker_teamk.Model.User;
import com.example.gradetracker_teamk.db.AppDataBase;
import com.example.gradetracker_teamk.db.UsersDAO;

public class Register extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private EditText first;
    private EditText second;
    private Button register;
    private UsersDAO db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = AppDataBase.getInstance(getApplicationContext()).getUsersDAO();

        username = findViewById(R.id.register_input1);
        password = findViewById(R.id.register_input2);
        register = findViewById(R.id.register_button);
        first = findViewById(R.id.register_first_name);
        second = findViewById(R.id.register_last_name);

        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                boolean empty = false;

                if (username.getText().toString().equals("") ||
                    password.getText().toString().equals("") ||
                    first.getText().toString().equals("") ||
                    second.getText().toString().equals("")) {
                    empty = true;
                }

                if(empty){
                    Toast.makeText(getApplicationContext(), "Make sure no fields are empty.", Toast.LENGTH_LONG).show();
                }
                else {
                    if (validatePassword(getApplicationContext(), password.getText().toString())) {

                        User temp = db.getUserByUsername(username.getText().toString());
                        if (temp == null) {
                            String name = (first.getText().toString() + " " + second.getText().toString());
                            db.insertUser(new User(name, username.getText().toString(), password.getText().toString()));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Username not available, try another", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Boolean validatePassword(Context context, String password) {
        if (!(password.length() > 5)) {
            Toast.makeText(context, "Password must be length 6 or more", Toast.LENGTH_LONG).show();
            return false;
        }
        boolean has_num = false;
        boolean has_char = false;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isAlphabetic(password.charAt(i))) {
                has_char = true;
            }
            if (Character.isDigit(password.charAt(i))) {
                has_num = true;
            }
        }

        if (!has_char) {
            Toast.makeText(context, "Your password must include a letter.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!has_num) {
            Toast.makeText(context, "Your password must include a number.", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }

}