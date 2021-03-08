package com.demoapp.demo.Common.LoginSingUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.demoapp.demo.Common.MapViewer.MapViewr;
import com.demoapp.demo.Common.OTP.ForgetPassword;
import com.demoapp.demo.Common.OTP.MakeSelection;
import com.demoapp.demo.Common.OTP.VerifyOTP;
import com.demoapp.demo.Databases.ServicesHelperClass;
import com.demoapp.demo.R;
import com.demoapp.demo.User.ServiceDashboard;
import com.demoapp.demo.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class service_center_login extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    RelativeLayout progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_service_center_login);

        countryCodePicker = findViewById(R.id.service_center_login_country_code_picker);
        phoneNumber = findViewById(R.id.service_center_login_phone_number);
        password = findViewById(R.id.service_center_login_password);
        progressbar = findViewById(R.id.service_center_login_progress_bar);

    }

    public void callForgetPassword(View view){
        startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
        finish();
    }

    public void letTheUserLoggedIn(View view){
        if (!validateFields()) {
            return;
        }

        progressbar.setVisibility(View.VISIBLE);

        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }

        String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;

        Query checkUser = FirebaseDatabase.getInstance().getReference("service").orderByChild("phoneNo").equalTo(_completePhoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNumber.setError(null);
                    phoneNumber.setErrorEnabled(false);

                    String systemPassword = snapshot.child(_completePhoneNumber).child("password").getValue(String.class);
                    if (systemPassword.equals(_password)) {

                        password.setError(null);
                        password.setErrorEnabled(false);

                        String _fullName = snapshot.child(_completePhoneNumber).child("fullName").getValue(String.class);
                        String _email = snapshot.child(_completePhoneNumber).child("email").getValue(String.class);
                        String _phoneNo = snapshot.child(_completePhoneNumber).child("phoneNo").getValue(String.class);
                        String _dateOfBirth = snapshot.child(_completePhoneNumber).child("date").getValue(String.class);

                        Toast.makeText(service_center_login.this, _fullName+"\n"+_email+"\n"+_phoneNo+"\n"+_dateOfBirth, Toast.LENGTH_SHORT).show();

                        setNumberData(_phoneNo);

                        startActivity(new Intent(getApplicationContext(), ServiceDashboard.class));


                    } else {

                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(service_center_login.this, "password does not match!!", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(service_center_login.this, "user data does not exist!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(service_center_login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setNumberData(String user_phoneNo) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("servicesMarker").child("bookingreport");

        ServicesHelperClass addNewUser = new ServicesHelperClass(user_phoneNo);
        reference.child("markerSnippet").setValue(addNewUser);
    }

    public void callSignUpFromLogin(View view){
        startActivity(new Intent(getApplicationContext(), Service_center_signUp_page1.class));
        finish();
    }

    private boolean validateFields() {

        String _phoneNumber = phoneNumber.getEditText().getText().toString().trim();
        String _password = password.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";

        if (_phoneNumber.isEmpty() && _password.isEmpty()) {
            phoneNumber.setError("field can't be empty");
            phoneNumber.requestFocus();
            return false;
        } else if (!_phoneNumber.matches(checkSpaces)) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }

    }

}