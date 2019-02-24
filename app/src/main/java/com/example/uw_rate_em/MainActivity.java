package com.example.uw_rate_em;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText passwordText, usernameText;
    private Button loginBtn, createAccBtn, guestBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        passwordText = (EditText) findViewById(R.id.passwordText);
        usernameText = (EditText) findViewById(R.id.usernameText);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        createAccBtn = (Button)findViewById(R.id.createAccBtn);
        guestBtn = (Button)findViewById(R.id.guestBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        // setting red title to be string "sign in"
        setTitle("Sign In");

        // getting the current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // if the current user isn't null redirect them to their home screen
        if (user != null){
            finish();
            Intent intent = new Intent(MainActivity.this, Home_Screen.class);
            startActivity(intent);
        }

        // try to login with info from usernameText and passwordText
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set username and password to the editText values
                String username = usernameText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                // make sure all relevant fields are populated
                if (!validateInput()){
                    return;
                }
                // try to sign in with credentials
                firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Home_Screen.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set username and password to the editText values
                String username = usernameText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                // make sure all relevant fields are populated
                if (!validateInput()){
                    return;
                }
                // try to create a new user with the given credentials
                firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, Home_Screen.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        guestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to next activity as guest
                firebaseAuth.signInAnonymously();
                Intent intent = new Intent(MainActivity.this, Home_Screen.class);
                startActivity(intent);
            }
        });

    }

    // check if username and password fields are not empty
    private boolean validateInput() {
        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        if ((username.isEmpty()) || (password.isEmpty())) {
            Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
