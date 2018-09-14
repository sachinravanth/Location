package com.example.sachin.location;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    Button track, search, test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        final String phoneNumber = intent.getStringExtra("phone");
        final String uid = intent.getStringExtra("uid");
        Log.d("paapu","Phone Number:"+phoneNumber+", UID:"+uid);
        track = (Button)findViewById(R.id.track);
        search = (Button)findViewById(R.id.search);
        test = (Button)findViewById(R.id.test);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,TrackerActivity.class);
                intent.putExtra("phone",phoneNumber);
                intent.putExtra("uid",uid);
                startActivity(intent);
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,MapsActivity.class);
                intent.putExtra("phone",phoneNumber);
                intent.putExtra("uid",uid);
                startActivity(intent);
                finish();
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                intent.putExtra("phone",phoneNumber);
                intent.putExtra("uid",uid);
                startActivity(intent);
                finish();
            }
        });
    }
}
