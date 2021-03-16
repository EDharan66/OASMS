package com.demoapp.demo.Common.Booking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.Call;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demoapp.demo.Common.MapViewer.TextMessage;
import com.demoapp.demo.Common.SendNotificationPack.APIService;
import com.demoapp.demo.Common.SendNotificationPack.Client;
import com.demoapp.demo.Common.SendNotificationPack.Data;
import com.demoapp.demo.Common.SendNotificationPack.MyResponse;
import com.demoapp.demo.Common.SendNotificationPack.NotificationSender;
import com.demoapp.demo.Databases.ServicesHelperClass;
import com.demoapp.demo.HelperClass.UploadHelperClass;
import com.demoapp.demo.R;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Callback;

public class BookingPage extends AppCompatActivity {

    TextView name_text;
    TextView email_text;
    TextView phone_text;
    Button booking_btn;
    RatingBar bookingRatingBar;
    ImageView call;
    ImageView message;
    ImageView mail;
    ImageView location;
    EditText editText;
    ProgressBar progessUPimg;
    String mail_info, mail_customer_info;
    String fullName_info,   fullName_customer_info;;
    String rating;
    String number;
    static String infomessage ;
    String customerNumberInfo;
    String dataTime;
    String serviceCenterNumberInfo;
    static String request;
    Uri uri;

    UploadHelperClass upload;

    ImageView selectedImage, cameraBtn;
    Button uploadBtn;
    private  int PICK_IMAGE_REQUEST = 1;
    private StorageTask mupload;
    private APIService apiService;


    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    FirebaseStorage firebaseStorage;
    StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);

        name_text = findViewById(R.id.name_text);
        email_text = findViewById(R.id.email_text);
        phone_text = findViewById(R.id.phone_text);
        booking_btn = findViewById(R.id.booking_btn);
        bookingRatingBar = findViewById(R.id.booking_rating_bar);
        call = findViewById(R.id.call);
        message = findViewById(R.id.message);
        mail = findViewById(R.id.mail);
        location = findViewById(R.id.location);
        editText = findViewById(R.id.booking_field_text_field);
        selectedImage = findViewById(R.id.car_upimg);
        cameraBtn = findViewById(R.id.camera_upolad_icon_bg);
        uploadBtn = findViewById(R.id.upload_img);
        progessUPimg = findViewById(R.id.progessbar_upload_img);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);






        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        databaseReference1 = firebaseDatabase.getReference();

        infomessage = editText.getText().toString();

        Toast.makeText(this, "edit text value "+ infomessage, Toast.LENGTH_SHORT).show();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE , dd-MMM-yyyy , hh:mm:ss a ");
        dataTime = simpleDateFormat.format(calendar.getTime());


        getdata();
        getFullServiceData();







        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mupload != null && mupload.isInProgress()){
                    Toast.makeText(BookingPage.this, "upload in progress", Toast.LENGTH_SHORT).show();
                }else{

                    openFileChooser();

                }
            }
        });


        bookingRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = String.valueOf(bookingRatingBar.getRating());
                Toast.makeText(BookingPage.this, "rating is" + rating.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(BookingPage.this, Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED){

                    callServicePhone();


                }
                else {

                    ActivityCompat.requestPermissions(BookingPage.this, new String[]{Manifest.permission.CALL_PHONE}
                            ,100);
                }
            }
        });

    }




    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        storageRef = firebaseStorage.getReference().child("userRepairPic").child(customerNumberInfo.toString());

        if (uri != null){
            StorageReference fileReference = storageRef.child(System.currentTimeMillis()
            + "." +getFileExtension(uri));

            mupload = fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progessUPimg.setProgress(0);
                                        }
                                    },50);

                                    infomessage = editText.getText().toString();

                                    upload = new UploadHelperClass(infomessage.toString(),uri.toString());
                                    UploadHelperClass urlupload = new UploadHelperClass(uri.toString());

                                    databaseReference1.child("report").child("userReportInfo").child(customerNumberInfo).child(dataTime).child("upload imgs").setValue(upload);
                                    databaseReference1.child("report").child("requestPermission").child(serviceCenterNumberInfo).child("upload img").setValue(urlupload);

                                    Toast.makeText(BookingPage.this, "uploaded successful", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(BookingPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progessUPimg.setProgress((int) progress);
                        }
                    });

        }
        else {

            Toast.makeText(this, "no file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

        selectedImage.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){

            uri = data.getData();

            Picasso.get().load(uri).into(selectedImage);
        }
    }

    private void getFullServiceData() {
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                fullName_customer_info = snapshot.child("Users").child(customerNumberInfo).child("fullName").getValue(String.class);
                mail_customer_info = snapshot.child("Users").child(customerNumberInfo).child("email").getValue(String.class);
                fullName_info = snapshot.child("service").child(serviceCenterNumberInfo).child("fullName").getValue(String.class);
                mail_info = snapshot.child("service").child(serviceCenterNumberInfo).child("email").getValue(String.class);
                request = snapshot.child("report").child("requestPermission").child(serviceCenterNumberInfo).child("request").getValue(String.class);
                name_text.setText(fullName_info.toString());
                email_text.setText(mail_info.toString());
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
              customerNumberInfo = snapshot.child("bookingreport").child("userphoneinfo").child("phoneNo").getValue(String.class);

              phone_text.setText(serviceCenterNumberInfo.toString());

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

        if(!validateFields()){
            return;
        }

        FirebaseDatabase rootNode;
        DatabaseReference reference;
        rootNode = FirebaseDatabase.getInstance();
        Timer timer = new Timer();

        try {
            infomessage = editText.getText().toString();
            Toast.makeText(this, "edit text value "+ infomessage, Toast.LENGTH_SHORT).show();

            reference = rootNode.getReference("report").child("requestPermission");
            ServicesHelperClass addNewUser2 = new ServicesHelperClass(customerNumberInfo, fullName_customer_info, infomessage,"on pending request");
            reference.child(serviceCenterNumberInfo).setValue(addNewUser2);
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        uploadFile();
        checkInitialPost();


    }

    private void checkInitialPost() {

        Timer timer = new Timer();
        if(request.equals("on pending request") || request != null){


            booking_btn.setText(" Requesting ... ");
            Toast.makeText(this, "wait for a moment ...", Toast.LENGTH_SHORT).show();


            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    checkRequest();

                }
            }, 30000);

        }
    }

    private void checkRequest() {

        FirebaseDatabase rootNode;
        DatabaseReference reference;
        rootNode = FirebaseDatabase.getInstance();
        Timer timer = new Timer();

        if(request.equals("accept")){

            reference = rootNode.getReference("report").child("userReportInfo");
            ServicesHelperClass addNewUser = new ServicesHelperClass(customerNumberInfo, serviceCenterNumberInfo, fullName_info, mail_info, infomessage, rating, dataTime.toString());
            reference.child(customerNumberInfo).child(dataTime.toString()).setValue(addNewUser);


            reference = rootNode.getReference("report").child("serviceReportInfo");
            ServicesHelperClass addNewUser1 = new ServicesHelperClass(customerNumberInfo, serviceCenterNumberInfo, fullName_customer_info, mail_customer_info, infomessage, rating, dataTime.toString());
            reference.child(serviceCenterNumberInfo).child(dataTime.toString()).setValue(addNewUser1);

            booking_btn.setText("booking request accept");

            FirebaseDatabase.getInstance().getReference().child("service").child(serviceCenterNumberInfo).child("token").child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String usertoken = dataSnapshot.getValue(String.class);
                    sendNotifications(usertoken, "Booking",infomessage);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

           // Toast.makeText(getApplicationContext(), "Successfully report uploaded", Toast.LENGTH_SHORT).show();

        }
        else if(request.equals("decline")){

            booking_btn.setVisibility(View.GONE);

            reference = rootNode.getReference("report").child("requestPermission");
            reference.child(serviceCenterNumberInfo).removeValue();

            Toast.makeText(this, "request decline", Toast.LENGTH_LONG).show();

        }else {

            booking_btn.setVisibility(View.GONE);

            Toast.makeText(this, "booking person does not reply with in 30 sec ,\n try call to the person", Toast.LENGTH_LONG).show();
        }
    }


    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(retrofit2.Call<MyResponse> call, retrofit2.Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(BookingPage.this, "Failed ", Toast.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<MyResponse> call, Throwable t) {

            }
        });
    }

    public void callServicePhone() {
        String s = "tel:" + serviceCenterNumberInfo;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults.length > 0 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED){

            callServicePhone();
        }else {

            Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }

    }



    private boolean validateFields() {

        String message = editText.getText().toString().trim();

        if (message.isEmpty() ) {
            editText.setError("field can't be empty");
            editText.requestFocus();
            return false;
        } else {
            editText.setError(null);
            return true;
        }

    }


}