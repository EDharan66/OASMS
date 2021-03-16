package com.demoapp.demo.Common.Booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demoapp.demo.Common.MapViewer.TextMessage;
import com.demoapp.demo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookingNotificationService extends AppCompatActivity {


    ImageView closeBtn, call, imageView ,message;
    Button acceptbtn, declinebtn;
    TextView titleText, name, number, info, not_found_id;
    ConstraintLayout constraintLayout;

    String cust_name, cust_number, cust_message, request, imgUrl;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    List<Object> image = new ArrayList<>();

    private static String serviceNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_notification_service);


        closeBtn = findViewById(R.id.close_btn_notification);
        call = findViewById(R.id.notification_call);
        message = findViewById(R.id.notification_message);
        imageView = findViewById(R.id.notification_imageView);
        acceptbtn = findViewById(R.id.accept_button);
        declinebtn = findViewById(R.id.decline_button);
        titleText = findViewById(R.id.notification_Title_View);
        name = findViewById(R.id.notification_name_text_field);
        number = findViewById(R.id.number_text_field);
        info = findViewById(R.id.message_text_field);
        constraintLayout = findViewById(R.id.constraintLayout);
        not_found_id = findViewById(R.id.not_found_id);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        callNotification();


        acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase rootNode;
                DatabaseReference reference;

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("report").child("requestPermission");
                reference.child(serviceNumber).child("request").setValue("accept");

                acceptbtn.setText("accepted");
                declinebtn.setVisibility(View.GONE);
                acceptbtn.setVisibility(View.VISIBLE);
            }
        });

        declinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase rootNode;
                DatabaseReference reference;

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("report").child("requestPermission");
                reference.child(serviceNumber).child("request").setValue("decline");

                declinebtn.setText("declined");
                acceptbtn.setVisibility(View.GONE);
                declinebtn.setVisibility(View.VISIBLE);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(BookingNotificationService.this, Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED){

                    callServicePhone();
                }
                else {

                    ActivityCompat.requestPermissions(BookingNotificationService.this, new String[]{Manifest.permission.CALL_PHONE}
                            ,100);
                }
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TextMessage.class));
            }
        });


    }

    private void callNotification() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {
                    serviceNumber = snapshot.child("servicesMarker").child("login").child("phoneNo").getValue(String.class);
                    cust_name = snapshot.child("report").child("requestPermission").child(serviceNumber).child("fullName_info").getValue(String.class);
                    cust_number = snapshot.child("report").child("requestPermission").child(serviceNumber).child("customerNumberInfo").getValue(String.class);
                    cust_message = snapshot.child("report").child("requestPermission").child(serviceNumber).child("message").getValue(String.class);
                    request = snapshot.child("report").child("requestPermission").child(serviceNumber).child("request").getValue(String.class);
                    imgUrl = snapshot.child("report").child("requestPermission").child(serviceNumber).child("upload img").child("mImageUri").getValue(String.class);

                }
                catch (Exception e){
                    Toast.makeText(BookingNotificationService.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


                if (cust_number != null){
                    not_found_id.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    Picasso.get().load(imgUrl).into(imageView);

                    name.setText(cust_name.toString());
                    number.setText(cust_number.toString());
                    info.setText(cust_message.toString());
                }
                else{
                    not_found_id.setVisibility(View.VISIBLE);
                    constraintLayout.setVisibility(View.GONE);
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(BookingNotificationService.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void callServicePhone() {
        String s = "tel:" + cust_number;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(s));
        startActivity(intent);
    }
}