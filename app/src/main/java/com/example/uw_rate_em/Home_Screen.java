package com.example.uw_rate_em;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;



public class Home_Screen extends AppCompatActivity {

    EditText courseSearchText;
    Button searchCourse, addCourse, myProfileBtn;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mCourseRef = mRootRef.child("Course");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        courseSearchText = (EditText) findViewById(R.id.courseSearchText);
        searchCourse = (Button)findViewById(R.id.searchCourse);
        addCourse = (Button)findViewById(R.id.addCourse);
        myProfileBtn = (Button)findViewById(R.id.myProfileBtn);



        searchCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (!validateInput()){
                return;
            }
            ;
            //TODO search database for course
                //Course course = Course.searchCourse(courseSearchText.getText().toString().trim());
                Course course = new Course(courseSearchText.getText().toString().trim());
                course.setGpa(4.0);
                mCourseRef.child("Name").setValue(course.getName());
                mCourseRef.child("gpa").setValue(Double.toString(course.getGpa()));
                if ( course != null){
                    Intent intent = new Intent(Home_Screen.this, coursePage.class);
                    intent.putExtra("course", course);
                    startActivity(intent);
                }
                else {
                    Snackbar.make(view, "Could not find course", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateInput()){
                    return;
                }
                //TODO search database for course
//                Course course = new Course(courseSearchText.getText().toString().trim());
                Course course = new Course(courseSearchText.getText().toString().trim());
                if (true){ //FIXME search database add database
                    // course added
                    Intent intent = new Intent(Home_Screen.this, coursePage.class);
                    intent.putExtra("course", course);
                    startActivity(intent);
                }
                else{
                    Snackbar.make(view, "Course already exists", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        myProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home_Screen.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInput() {
        String courseText = courseSearchText.getText().toString().trim();
        if ((courseText.isEmpty())) {
            Toast.makeText(Home_Screen.this, "Please fill course field", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
