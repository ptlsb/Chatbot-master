package com.example.random;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class loginpage extends AppCompatActivity {
    public  void loginup(View view){
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.pass);
        if(username.getText().toString().matches("")|| password.getText().toString().matches("")){
            Toast.makeText(loginpage.this ,"Enter details!!" , Toast.LENGTH_LONG).show();
        }
        else{
            ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if(e==null){
                        Toast.makeText(loginpage.this,"Success!!" , Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext() , userlist.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(loginpage.this, e.getMessage() , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        setTitle("TextMe");
    }
}