package com.example.uw_rate_em;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.String.valueOf;

public class coursePage extends AppCompatActivity {

    private TextView gradeText, courseNameText;
    private Button addRatingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);

        gradeText = (TextView) findViewById(R.id.gradeText);
        courseNameText = (TextView) findViewById(R.id.courseNameText);
        addRatingBtn = (Button)findViewById(R.id.addRatingBtn);

        Intent intent = getIntent();
        final Course course = (Course) intent.getSerializableExtra("course");

        gradeText.setText(valueOf(course.getGpa()));
        courseNameText.setText(course.getName());
        addRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                course.addGrade(4.0);
//                gradeText.setText(valueOf(course.getGpa()));
            }
        });

    }
}
