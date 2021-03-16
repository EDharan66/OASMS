package com.demoapp.demo.Common.LoginSingUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.demoapp.demo.Common.MapViewer.NearByPlace.MapsActivity;
import com.demoapp.demo.Common.OTP.ForgetPassword;
import com.demoapp.demo.Databases.ServicesHelperClass;
import com.demoapp.demo.Databases.SessionManager;
import com.demoapp.demo.HelperClass.CheckInterNet;
import com.demoapp.demo.R;
import com.demoapp.demo.User.UserDashboard;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class customer_login extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber, password;
    TextInputEditText RememberMePhoneNumber, RememberMePassword;
    RelativeLayout progressbar;
    CheckBox rememberMe;

    String loginType = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_login);

        countryCodePicker = findViewById(R.id.customer_login_country_code_picker);
        phoneNumber = findViewById(R.id.customer_login_phone_number);
        password = findViewById(R.id.customer_login_password);
        progressbar = findViewById(R.id.customer_login_progress_bar);
        rememberMe = findViewById(R.id.customer_login_remember_me);
        RememberMePhoneNumber= findViewById(R.id.login_phone_number_editText);
        RememberMePassword = findViewById(R.id.customer_login_password_editText);


        SessionManager sessionManager = new SessionManager(customer_login.this, SessionManager.SESSION_REMEMBERME);
        if(sessionManager.checkRememberMe()){
            HashMap<String ,String > rememberMeDetails = sessionManager.getRememberMeDetailFromSession();
            RememberMePhoneNumber.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPHONENO));
            RememberMePassword.setText(rememberMeDetails.get(SessionManager.KEY_SESSIONPASSWORD));
        }

    }


    public void callForgetPassword(View view) {

        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);

        intent.putExtra("loginType", loginType);

        startActivity(intent);
        finish();
    }

    public void letTheUserLoggedIn(View view) {

        CheckInterNet checkInterNet = new CheckInterNet();
        if (!checkInterNet.isConnected(this)) {
            showCustomDialog();
        }

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

        if(rememberMe.isChecked()){
            SessionManager sessionManager = new SessionManager(customer_login.this, SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(_phoneNumber,_password);
        }

        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);

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

                        SessionManager sessionManager = new SessionManager(customer_login.this,SessionManager.SESSION_USERSESSION);
                        sessionManager.createLoginSession(_fullName, _phoneNo, _email, _password, _dateOfBirth);

                        Toast.makeText(customer_login.this, _fullName + "\n" + _email + "\n" + _phoneNo + "\n" + _dateOfBirth, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

                        setNumberData(_phoneNo);

                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                        finish();


                    } else {

                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(customer_login.this, "password does not match!!", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(customer_login.this, "user data does not exist!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(customer_login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showCustomDialog() {

    }

    private void setNumberData(String user_phoneNo) {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("User").child("bookingreport");

        ServicesHelperClass addNewUser = new ServicesHelperClass(user_phoneNo);
        reference.child("userphoneinfo").setValue(addNewUser);
    }

    public void callSignUpFromLogin(View view) {
        startActivity(new Intent(getApplicationContext(), customer_signUp_page1.class));
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