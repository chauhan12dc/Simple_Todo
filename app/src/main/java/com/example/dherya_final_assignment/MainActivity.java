package com.example.dherya_final_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    public static TextView textview ;
    DatabaseClass databaseClass;
    EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseClass = new DatabaseClass(this);
        username = findViewById(R.id.username_txt);
        password = findViewById(R.id.password_txt);
    }

    public void signIn_click(android.view.View view) {
        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(this, "All Fields are required!", Toast.LENGTH_SHORT).show();
        } else {
            if (databaseClass.CheckLogin(username.getText().toString(), password.getText().toString())) {
                Intent i = new Intent(this, HomeActivity.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(this, "Invalid Username or Password.. Try Again", Toast.LENGTH_SHORT).show();
                username.setText("");
                password.setText("");
                username.requestFocus();
            }
        }
    }
    public void signUp_click(android.view.View view){
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }
}