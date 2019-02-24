package com.example.uw_rate_em;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

import java.util.*;



public class Home_Screen extends AppCompatActivity {

    EditText courseSearchText;
    Button searchCourse, addCourse, myProfileBtn;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mCourseRef = mRootRef.child("Courses");

    ArrayList<String> courseNameArrayList = new ArrayList<>();

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

                if ( searchCourse()){
                    Course course = new Course(courseSearchText.getText().toString().trim());
                    Intent intent = new Intent(Home_Screen.this, coursePage.class);
                    intent.putExtra("course", course);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Home_Screen.this, "Could not find course", Toast.LENGTH_SHORT).show();
                }


            }
        });

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateInput()) {
                    return;
                }


                if (!searchCourse()){ //FIXME search database add database
                    // creating new course object
                    Course course = new Course(courseSearchText.getText().toString().trim());
                    // creating reference to new course title in firebase.
                    DatabaseReference pushedCourseRef = mCourseRef.child(course.getName()).child("Name");
                    // Setting value of new course to the Course Name
                    pushedCourseRef.setValue(course.getName());

                    // creating reference to new gpa in firebase
                    DatabaseReference pushedGpaRef = mCourseRef.child(course.getName()).child("Gpa");
                    // Setting value of new course to the Course Name
                    pushedGpaRef.setValue(Double.toString(course.getGpa()));

                    // creating reference to new ratings in firebase
                    DatabaseReference pushedRatingsRef = mCourseRef.child(course.getName()).child("Ratings");
                    // Setting value of new course to the Course Name
                    pushedRatingsRef.setValue(Integer.toString(course.getRatings()));

                    // passing that course to the course page
                    Intent intent = new Intent(Home_Screen.this, coursePage.class);
                    // going to course page
                    intent.putExtra("course", course);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Home_Screen.this, "Course already exists", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart() {
        super.onStart();

        mCourseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    // adding course to local array list if it is not already there
                    if (!(data.getValue() == null) && !courseNameArrayList.contains(data.getKey())) {
                        courseNameArrayList.add(data.getKey().toUpperCase());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    private Boolean searchCourse(){
        if (courseNameArrayList.contains(courseSearchText.getText().toString().trim().toUpperCase())) {
            return true;
        }
        return false;
    }

}
