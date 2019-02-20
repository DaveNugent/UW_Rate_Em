package com.example.uw_rate_em;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText passwordText, usernameText;
    private Button loginBtn, createAccBtn, guestBtn;
    boolean guest;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordText = (EditText) findViewById(R.id.passwordText);
        usernameText = (EditText) findViewById(R.id.usernameText);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        createAccBtn = (Button)findViewById(R.id.createAccBtn);
        guestBtn = (Button)findViewById(R.id.guestBtn);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){
            finish();
            Intent intent = new Intent(MainActivity.this, Home_Screen.class);
            startActivity(intent);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO check if username already used
                // TODO if not add to database
                guest =  false;
                // go to next activity
                String username = usernameText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                if (!validateInput()){
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Home_Screen.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                // else tell user username already taken
            }
        });

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO check if username already used
                // TODO if not add to database
                guest =  false;
                // go to next activity
                String username = usernameText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                if (!validateInput()){
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Home_Screen.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
