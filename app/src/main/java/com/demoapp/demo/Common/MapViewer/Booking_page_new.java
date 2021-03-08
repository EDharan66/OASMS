package com.demoapp.demo.Common.MapViewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demoapp.demo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Booking_page_new extends AppCompatActivity {

    TextView name_text;
    TextView email_text;
    TextView phone_text;
    Button booking_btn;
    ImageView call;
    ImageView message;
    ImageView mail;
    ImageView location;
    String name;
    String sn;

    // our Firebase Database. 
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page_new);



        name_text = findViewById(R.id.name_text);
        email_text = findViewById(R.id.email_text);
        phone_text = findViewById(R.id.phone_text);
        booking_btn = findViewById(R.id.booking_button);
        call = findViewById(R.id.call);
        message = findViewById(R.id.message);
        mail = findViewById(R.id.mail);
        location = findViewById(R.id.location);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        getdata();




    }

    private void getdata() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String value = snapshot.child("bookingreport").child("markerSnippet").child("markerName").getValue(String.class);
                name = value;
                name_text.setText(name.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(Booking_page_new.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}