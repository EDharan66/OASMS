package com.demoapp.demo.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demoapp.demo.Common.MapViewer.Booking_page_new;
import com.demoapp.demo.Common.MapViewer.TextMessage;
import com.demoapp.demo.Databases.ServicesHelperClass;
import com.demoapp.demo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BookingPage extends AppCompatActivity {

    TextView name_text;
    TextView email_text;
    TextView phone_text;
    Button booking_btn;
    ImageView call;
    ImageView message;
    ImageView mail;
    ImageView location;
    String number;
    String customerNumberInfo;
    String name;
    String mail_id;
    String markerName;
    String markerSnippet;
    String dataTime;
    String serviceCenterNumberInfo;

    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);

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
        databaseReference1 = firebaseDatabase.getReference();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE , dd-MMM-yyyy , hh:mm:ss a ");
        dataTime = simpleDateFormat.format(calendar.getTime());


        getdata();
        getFullData();

    }

    private void getFullData() {
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String fullName = snapshot.child("service").child(number.toString()).child("fullName").getValue(String.class);
                String mail = snapshot.child("service").child(number.toString()).child("email").getValue(String.class);
                name_text.setText(fullName.toString());
                email_text.setText(mail.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(BookingPage.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }


   private void getdata() {

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {

               serviceCenterNumberInfo = snapshot.child("bookingreport").child("markerSnippet").child("markerSnippet").getValue(String.class);
               String customernumberInfo = snapshot.child("bookingreport").child("markerSnippet").child("markerSnippet").getValue(String.class);
               number = serviceCenterNumberInfo;
               customerNumberInfo = customernumberInfo;
               phone_text.setText(number.toString());

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               // calling on cancelled method when we receive
               // any error or we are not able to get the data.
               Toast.makeText(BookingPage.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
           }
       });
   }

    public void callSetBooking(View view) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("report").child("userReportInfo");

        ServicesHelperClass addNewUser = new ServicesHelperClass(customerNumberInfo, serviceCenterNumberInfo);

        reference.child(dataTime.toString()).setValue(addNewUser);

        Toast.makeText(this, "Successfully report uploaded", Toast.LENGTH_SHORT).show();

    }

    public void callservicephone(View view) {
        String s = "tel:" + serviceCenterNumberInfo.toString();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(s));
        startActivity(intent);
    }

    public void callServiceMessageInfo(View view) {
        startActivity(new Intent(getApplicationContext(), TextMessage.class));
    }

    public void callServiceMailInfo(View view) {
    }

    public void CallServiceLocationInfo(View view) {
    }
}