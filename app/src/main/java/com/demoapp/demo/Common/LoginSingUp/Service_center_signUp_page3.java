package com.demoapp.demo.Common.LoginSingUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.demoapp.demo.Common.MapViewer.MapCurrentLocation;
import com.demoapp.demo.Common.OTP.VerifyOTP;
import com.demoapp.demo.Common.SendNotificationPack.Token;
import com.demoapp.demo.Databases.ServicesHelperClass;
import com.demoapp.demo.Databases.UserHelperClass;
import com.demoapp.demo.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;

public class Service_center_signUp_page3 extends AppCompatActivity {

    TextInputLayout phoneNumber;
    ScrollView scrollView;
    CountryCodePicker countryCodePicker;

    String fullName, phoneNo, email, userName, password, date, gender, latitude, longitude;

    //Initialize variable;
    Button btlocation;
    TextView tvlatitude, tvlongitude;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_service_center_sign_up_page3);

        scrollView = findViewById(R.id.Service_center_signUp_page3_scroll_view);
        countryCodePicker = findViewById(R.id.Service_center_signUp_page3_country_code_picker);
        phoneNumber = findViewById(R.id.Service_center_signUp_page3_phone_number);


        //Assign variable
        btlocation = findViewById(R.id.service_center_signup_location_button);
        tvlongitude = findViewById(R.id.longitude_right);
        tvlatitude = findViewById(R.id.latitude_right);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                Service_center_signUp_page3.this
        );

    }


    public void callVerifyOTPScreen(View view) {

        if (!validatePhoneNumber() | !validateSetLocation()) {
            return;
        }


        fullName = getIntent().getStringExtra("fullName");
        email = getIntent().getStringExtra("email");
        userName = getIntent().getStringExtra("userName");
        password = getIntent().getStringExtra("password");
        date = getIntent().getStringExtra("date");
        gender = getIntent().getStringExtra("gender");


        //Get complete phone number
        String _getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
        phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

        Intent intent = new Intent(getApplicationContext(), StartUpScreen.class);


        storeNewUsersData();


        //Add Transition
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.service_center_signup_page3_next_button), "transition_OTP_screen");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Service_center_signUp_page3.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }

    private void storeNewUsersData() {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("service");
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken= FirebaseInstanceId.getInstance().getToken();
        Token token= new Token(refreshToken);

        ServicesHelperClass addNewUser = new ServicesHelperClass(fullName, phoneNo, email, userName, password, date, gender, latitude, longitude, token);

        reference.child(phoneNo).setValue(addNewUser);


    }

    //map_code//
    public void CallSetLocation(View view) {

        if (ActivityCompat.checkSelfPermission(Service_center_signUp_page3.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Service_center_signUp_page3.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();

        } else {

            ActivityCompat.requestPermissions(Service_center_signUp_page3.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}
                    , 100);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1])
                == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();
        } else {

            Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(

                Context.LOCATION_SERVICE
        );

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();

                    if (location != null) {

                        tvlatitude.setText(String.valueOf(location.getLatitude()));
                        tvlongitude.setText(String.valueOf(location.getLongitude()));

                        latitude = String.valueOf(location.getLatitude());
                        longitude = String.valueOf(location.getLongitude());


                    } else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                tvlatitude.setText(String.valueOf(location1.getLatitude()));
                                tvlongitude.setText(String.valueOf(location1.getLongitude()));

                                latitude = String.valueOf(location1.getLatitude());
                                longitude = String.valueOf(location1.getLongitude());

                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });
        } else {

            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }

    //validate//

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (!val.matches(checkspaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateSetLocation() {
        String val1 = latitude;
        String val2 = longitude;

        if (val1.isEmpty() && val2.isEmpty()) {
            phoneNumber.setError("Enter set Location properly");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }

}