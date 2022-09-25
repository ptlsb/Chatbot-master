package com.example.random;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
public void Onclick(View view){
    EditText username = (EditText) findViewById(R.id.editTextTextPersonName);
    EditText password = (EditText) findViewById(R.id.passworf);
    EditText email = (EditText) findViewById(R.id.email);
    if(username.getText().toString().matches("") || password.getText().toString().matches("")){
        Toast.makeText(this , "Complete info please " , Toast.LENGTH_SHORT).show();
    }
    else{
        ParseUser user = new ParseUser();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setEmail(email.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                   Toast.makeText(MainActivity.this , "success!!" , Toast.LENGTH_LONG).show();
                   Intent intent = new Intent(getApplicationContext() , userlist.class);
                   startActivity(intent);
                   finish();
                }
                else {
                   Toast.makeText(MainActivity.this , e.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.login);
        textView.setOnClickListener(this::onClick);
        setTitle("TextMe");


    }

    @Override
    public void onClick(View view) {
    if(view.getId() == R.id.login){
        Intent intent = new Intent(getApplicationContext() , loginpage.class);
        startActivity(intent);
        finish();
    }

    }
}
