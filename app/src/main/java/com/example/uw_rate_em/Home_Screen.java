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

import java.util.*;



public class Home_Screen extends AppCompatActivity {

//    static ArrayList<Course> courseList = new ArrayList<Course>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText courseSearchText;
        Button searchCourse, addCourse;


        courseSearchText = (EditText) findViewById(R.id.courseSearchText);
        searchCourse = (Button)findViewById(R.id.searchCourse);
        addCourse = (Button)findViewById(R.id.addCourse);



        searchCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course course = Course.searchCourse(courseSearchText.getText().toString().trim());
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
                Course course = new Course(courseSearchText.getText().toString().trim());
                if (Course.addCourse(course)){
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
    }

}
