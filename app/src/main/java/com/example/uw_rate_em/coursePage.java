package com.example.uw_rate_em;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private Button addCourseSchedule, addRatingBtn;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mCourseRef = mRootRef.child("Course");


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);

        gradeText = (TextView) findViewById(R.id.gradeText);
        courseNameText = (TextView) findViewById(R.id.courseNameText);
        addCourseSchedule = (Button)findViewById(R.id.addRatingBtn);
        addRatingBtn = (Button) findViewById(R.id.addRatingBtn);

        Intent intent = getIntent();
        final Course course = (Course) intent.getSerializableExtra("course");

        gradeText.setText(valueOf(course.getGpa()));
        courseNameText.setText(course.getName());

        addRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();

        mCourseRef.child("gpa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.getValue(String.class);
                gradeText.setText(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        addCourseSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCourseRef.child("ratings").setValue("1");
                //addCourse(course);
            }
        });

    }
    private void addCourse(Course course){
        //Course course1 = new Course("testCourse");
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
//        myRef.setValue(course);

    }
}
