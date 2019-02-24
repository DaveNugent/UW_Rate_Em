package com.example.uw_rate_em;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private RecyclerView CourseListText;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUsersRef = mRootRef.child("Users");
    String[] userCourseList = new String[20];
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("My Profile");

        firebaseAuth = FirebaseAuth.getInstance();
        CourseListText = (RecyclerView) findViewById(R.id.courseRecyclerText);


        logout = (Button) findViewById(R.id.logoutBtn);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // logout if the user clicks logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity((new Intent(ProfileActivity.this, MainActivity.class)));
            }
        });

        // populate string array with all courses associated with the users uis
        mUsersRef.child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                Log.d("in data change", "here");
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    userCourseList[i] = data.getKey().toUpperCase();
                    i++;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        CourseListText.setHasFixedSize(true);
         // using a linear layout manager
        layoutManager = new LinearLayoutManager(this);

        CourseListText.setLayoutManager(layoutManager);

        // specify the  adapter
        mAdapter = new MyAdapter(userCourseList);
        CourseListText.setAdapter(this.mAdapter);

    }
}
