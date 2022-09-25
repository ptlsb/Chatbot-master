package com.example.random;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class userlist extends AppCompatActivity {
    ListView listView ;
    ArrayList<String> userslist ;
    ArrayAdapter<String> ad ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        setTitle("Active users");
        listView = findViewById(R.id.userlist);
        userslist = new ArrayList<>();
        ad = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, userslist);
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                      Intent intent = new Intent(getApplicationContext() , chatpage.class);
                      intent.putExtra("user" , userslist.get(i));
                      startActivity(intent);
             }
         });
        ParseQuery<ParseUser> query = ParseUser.getQuery() ;
        query.whereNotEqualTo("username" , ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                if(e==null) {
                    if (list.size() == 0) {
                        Toast.makeText(userlist.this , "No entry found" , Toast.LENGTH_LONG).show();

                    }
                    else{
                        for (ParseUser user : list) {
                            userslist.add(user.getUsername());
                            Log.i("user" , user.getUsername());
                        }
                        listView.setAdapter(ad);
                    }
                }
                else{
                    e.printStackTrace();
                }
            }
        });



    }
}