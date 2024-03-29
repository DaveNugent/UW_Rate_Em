package com.example.uw_rate_em;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.String.valueOf;

public class coursePage extends AppCompatActivity {

    private TextView gradeText, courseNameText;
    private  EditText ratingText;
    private Button addCourseSchedule, addRatingBtn;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUsersRef = mRootRef.child("Users");
    DatabaseReference mCourseRef = mRootRef.child("Courses");
    DecimalFormat df = new DecimalFormat("#.##");
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser() ;

    Course course;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);

        gradeText = (TextView) findViewById(R.id.gradeText);
        courseNameText = (TextView) findViewById(R.id.courseNameText);
        addCourseSchedule = (Button)findViewById(R.id.addCourseSchedule);
        addRatingBtn = (Button) findViewById(R.id.addRatingBtn);
        ratingText = (EditText) findViewById(R.id.ratingText);

        Intent intent = getIntent();
        course = (Course) intent.getSerializableExtra("course");
        // setting title to courses name
        setTitle(course.getName());

        gradeText.setText(valueOf(course.getGpa()));
        courseNameText.setText(course.getName());

    }
    @Override
    public void onStart() {
        super.onStart();


        mCourseRef.child(course.getName()).child("Gpa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class);
                // setting course object gpa to databases course gpa
                course.setGpa(Double.valueOf(temp));
                // rounding to second decimal point
                temp = (String)df.format(course.getGpa());
                // setting gpa to temp
                gradeText.setText(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mCourseRef.child(course.getName()).child("Ratings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // setting course objects ratings to databases ratings
                String temp = dataSnapshot.getValue(String.class);
                if (temp != null){
                    course.setRatings(Integer.parseInt(temp));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addCourseSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add course to database under Root/Courses/current user uid
                mUsersRef.child(currentUser.getUid()).child(course.getName()).setValue(course.getName());
                Toast.makeText(coursePage.this, "Course added", Toast.LENGTH_SHORT).show();
            }
        });

        // updating course object and firebase course overall rating
        addRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // checking input is not null
                if (validateInput()) {
                    double userRating = Double.valueOf(ratingText.getText().toString());
                    // checking input is within range
                    if ((userRating > 4.0) || (userRating < 0.0)) {
                        Toast.makeText(coursePage.this, "Please enter a value from 0.0 - 4.0", Toast.LENGTH_SHORT).show();
                    } else {
                        // multiplying the average by its weight (ratings)
                        double currTotal = course.getGpa() * course.getRatings();
                        // incrementing the course objects rating
                        course.setRatings((course.getRatings() + 1));
                        // finding the new average with the added value
                        course.setGpa((currTotal + userRating) / course.getRatings());
                        // setting databases ratings and gpa feilds
                        mCourseRef.child(course.getName()).child("Gpa").setValue(String.valueOf(course.getGpa()));
                        mCourseRef.child(course.getName()).child("Ratings").setValue(String.valueOf(course.getRatings()));
                        // giving user feedback
                        Toast.makeText(coursePage.this, "Rating added", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    // check to make sure user input is not null
    private boolean validateInput() {
        String courseText = ratingText.getText().toString().trim();
        if ((courseText.isEmpty())) {
            Toast.makeText(coursePage.this, "Please fill ratings field", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
