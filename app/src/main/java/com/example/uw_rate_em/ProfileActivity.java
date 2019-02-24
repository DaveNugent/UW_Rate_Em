package com.example.uw_rate_em;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mCourseRef = mRootRef.child("Courses");
    ArrayList<String> userCoursesArrayList = new ArrayList<>();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        logout = (Button) findViewById(R.id.logoutBtn);


    }

    @Override
    protected void onStart() {
        super.onStart();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity((new Intent(ProfileActivity.this, MainActivity.class)));
            }
        });
        //TODO add course display from userCourseArrayList (I didn't run and test this code yet)

        mCourseRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    // adding course to local array list if it is not already there
                    if (!(data.getValue() == null) && !userCoursesArrayList.contains(data.getKey())) {
                        userCoursesArrayList.add(data.getKey().toUpperCase());
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
