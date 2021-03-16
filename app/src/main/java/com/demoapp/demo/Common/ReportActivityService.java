package com.demoapp.demo.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.demoapp.demo.HelperClass.RvAdapter.ReportRvAdapter;
import com.demoapp.demo.HelperClass.RvAdapter.ReportRvDateInfo;
import com.demoapp.demo.HelperClass.RvAdapter.ReportRvFullNameInfo;
import com.demoapp.demo.HelperClass.RvAdapter.ReportRvMailInfo;
import com.demoapp.demo.HelperClass.RvAdapter.ReportRvMessageInfo;
import com.demoapp.demo.HelperClass.RvAdapter.ReportRvNumaberInfo;
import com.demoapp.demo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ReportActivityService extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReportRvAdapter reportRvAdapter;


    ArrayList<ReportRvFullNameInfo> fullName_info_list = new ArrayList<>();
    ArrayList<ReportRvMailInfo> mail_info_list = new ArrayList<>();
    ArrayList<ReportRvNumaberInfo> serviceCenterNumberInfo = new ArrayList<>();
    ArrayList<ReportRvMessageInfo> message_list = new ArrayList<>();
    ArrayList<ReportRvDateInfo> dateinfo = new ArrayList<>();


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    Map<String, Object> map;
    String numberInfo;
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_service);


        firebaseDatabase = FirebaseDatabase.getInstance();


        databaseReference2 = firebaseDatabase.getReference();

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String customerNumberInfo = snapshot.child("servicesMarker").child("login").child("phoneNo").getValue(String.class);
                numberInfo = customerNumberInfo.toString();
                Toast.makeText(ReportActivityService.this, numberInfo + " get data successfull", Toast.LENGTH_SHORT).show();

                databaseReference = firebaseDatabase.getReference().child("report").child("serviceReportInfo");
                databaseReference.child(numberInfo.toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        //items.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            map = (Map<String, Object>) ds.getValue();

                            Object fullName_info = map.get("fullName_info");
                            Object mail_info = map.get("mail_info");
                            Object number = map.get("customerNumberInfo");
                            Object message = map.get("message");
                            Object date = map.get("dateinfo");


                            // serviceInfo.add(String.valueOf(serviceNumber));
                            ReportRvFullNameInfo item1 = new ReportRvFullNameInfo(fullName_info.toString());
                            ReportRvMailInfo item2 = new ReportRvMailInfo(mail_info.toString());
                            ReportRvNumaberInfo item3 = new ReportRvNumaberInfo(number.toString());
                            ReportRvMessageInfo item4 = new ReportRvMessageInfo(message.toString());
                            ReportRvDateInfo item5 = new ReportRvDateInfo(date.toString());
                            fullName_info_list.add(item1);
                            mail_info_list.add(item2);
                            serviceCenterNumberInfo.add(item3);
                            message_list.add(item4);
                            dateinfo.add(item5);

                            Toast.makeText(ReportActivityService.this, " loaded", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(ReportActivityService.this, "DataBase error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(ReportActivityService.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });



        recyclerView = findViewById(R.id.service_report_rv);

        reportRvAdapter = new ReportRvAdapter((ArrayList<ReportRvFullNameInfo>) fullName_info_list,(ArrayList<ReportRvMailInfo>) mail_info_list,(ArrayList<ReportRvNumaberInfo>) serviceCenterNumberInfo,(ArrayList<ReportRvMessageInfo>) message_list,(ArrayList<ReportRvDateInfo>) dateinfo );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(reportRvAdapter);


    }







}