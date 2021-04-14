package com.example.dherya_final_assignment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseClass databaseClass;
    EditText username,password,cPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseClass = new DatabaseClass(this);
        username = findViewById(R.id.rusername);
        password = findViewById(R.id.rpassword);
        cPassword = findViewById(R.id.cpassword);
    }
    public void register_click(android.view.View view){

        if (username.getText().toString().equals("")||cPassword.getText().toString().equals("")||password.getText().toString().equals(""))
        {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
        else {
            if (databaseClass.CheckUsername(username.getText().toString())) {
                if (password.getText().toString().equals(cPassword.getText().toString())) {
                    databaseClass.Insert(username.getText().toString(),password.getText().toString());
                    Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Username already in use.. Use Another.!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}