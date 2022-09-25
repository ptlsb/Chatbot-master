package com.example.random;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class chatpage extends AppCompatActivity {
    String username ;
    ArrayList<String> message = new ArrayList<>();
    ArrayAdapter<String> ad ;
    ListView sendmessage ;
    String msgsend = "";
    Encrypt encrypt;
    public void sent(View view){
        EditText ms = findViewById(R.id.test);
        try {
            encrypt.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            msgsend = encrypt.encrypt(ms.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ParseObject Message = new ParseObject("Message");
        Message.put("sender" , ParseUser.getCurrentUser().getUsername());
        Message.put("receiver" , username);
        Message.put("message" , msgsend);
        ms.setText("");
        Message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    message.add(msgsend);
                    ad.notifyDataSetChanged();

                }
                else{
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatpage);
          encrypt = new Encrypt();
          Intent intent = getIntent();
          username = intent.getStringExtra("user");
          setTitle("chat to " + username);
          sendmessage =findViewById(R.id.chatlist);
          ad = new ArrayAdapter<String>(this , R.layout.support_simple_spinner_dropdown_item , message);
          sendmessage.setAdapter(ad);
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Message");
        query1.whereEqualTo("sender" , ParseUser.getCurrentUser().getUsername());
        query1.whereEqualTo("receiver" , username);
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Message");
        query2.whereEqualTo("receiver" ,ParseUser.getCurrentUser().getUsername());
        query2.whereEqualTo("sender",username);

        List<ParseQuery<ParseObject>> queries = new ArrayList<>();
        queries.add(query1);
        queries.add(query2);
        ParseQuery<ParseObject> query = ParseQuery.or(queries);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    if(list.size() > 0){
                        for(ParseObject msg : list){
                            try {
                                encrypt.init();
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            String msgcontent = null;
                            try {
                                msgcontent = encrypt.decrypt(msg.getString("message"));
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            if(msg.getString("sender").equals(username)){
                                msgcontent = "<" + msgcontent ;
                            }
                            message.add(msgcontent);
                        }
                    }
                }
            }
        });

    }
}