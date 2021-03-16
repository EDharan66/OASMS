package com.demoapp.demo.Common.MapViewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demoapp.demo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TextMessage extends AppCompatActivity {

    EditText etMessage;
    TextView etPhone;
    Button btSent;
    String number;
    String serviceCenterNumberInfo;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_message);


        etPhone = findViewById(R.id.et_phone);
        etMessage = findViewById(R.id.et_message);
        btSent = findViewById(R.id.bt_send);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        getdata();

        btSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(TextMessage.this, Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED){

                    sendMessage();
                }
                else {

                    ActivityCompat.requestPermissions(TextMessage.this, new String[]{Manifest.permission.SEND_SMS}

                    ,100);
                }
            }
        });

    }

    private void getdata() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                serviceCenterNumberInfo = snapshot.child("bookingreport").child("markerSnippet").child("markerSnippet").getValue(String.class);
                etPhone.setText(serviceCenterNumberInfo.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(TextMessage.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {

        String sPhone = serviceCenterNumberInfo;
        String sMessage = etMessage.getText().toString().trim();

        if(!sPhone.equals("") && !sMessage.equals("")){

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(sPhone,null, sMessage, null,null);

            Toast.makeText(getApplicationContext(), "SMS send Successfully!", Toast.LENGTH_LONG).show();

        }
        else {

            Toast.makeText(getApplicationContext(), "Enter the value first!", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults.length > 0 && grantResults[0]
        == PackageManager.PERMISSION_GRANTED){

            sendMessage();
        }else {

            Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }

    }
}