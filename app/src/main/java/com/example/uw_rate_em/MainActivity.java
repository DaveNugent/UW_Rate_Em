package com.example.uw_rate_em;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText passwordText, usernameText;
    private Button loginBtn, createAccBtn, guestBtn;
    boolean guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordText = (EditText) findViewById(R.id.passwordText);
        usernameText = (EditText) findViewById(R.id.usernameText);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        createAccBtn = (Button)findViewById(R.id.createAccBtn);
        guestBtn = (Button)findViewById(R.id.guestBtn);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO check if username and password correct
                // TODO if yes
                guest =  false;
                // go to next activity
                Intent intent = new Intent(MainActivity.this, Home_Screen.class);
                startActivity(intent);

                // TODO else tell user wrong credentials

            }
        });

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO check if username already used
                // TODO if not add to database
                guest =  false;
                // go to next activity
                Intent intent = new Intent(MainActivity.this, Home_Screen.class);
                startActivity(intent);

                // else tell user username already taken
            }
        });

        guestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guest = true;
                // go to next activity
                Intent intent = new Intent(MainActivity.this, Home_Screen.class);
                startActivity(intent);
            }
        });

    }


}
