package com.demoapp.demo.Common.OTP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.MalformedJsonException;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.demoapp.demo.Common.LoginSingUp.StartUpScreen;
import com.demoapp.demo.Common.LoginSingUp.customer_login;
import com.demoapp.demo.Common.LoginSingUp.customer_signUp_page3;
import com.demoapp.demo.Common.MapViewer.NearByPlace.MapsActivity;
import com.demoapp.demo.Common.MapViewer.TextMessage;
import com.demoapp.demo.HelperClass.CheckInterNet;
import com.demoapp.demo.R;
import com.demoapp.demo.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {


    ImageView forgotBkBtn;
    Button forgotNxtBtn;
    ImageView passwordIcon;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNo;
    TextView title, desc;
    RelativeLayout progressbar;

    String _loginType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);




        forgotBkBtn = findViewById(R.id.forget_password_back_btn);
        passwordIcon = findViewById(R.id.forget_password_icon);
        title = findViewById(R.id.forget_password_title);
        desc = findViewById(R.id.forget_password_description);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNo = findViewById(R.id.forget_password_phone_number);
        forgotNxtBtn = findViewById(R.id.forget_password_next_btn);
        progressbar = findViewById(R.id.FP_progress_bar);

        _loginType = getIntent().getStringExtra("loginType");



        Animation animation = AnimationUtils.loadAnimation(this, R.anim.side_anim);

//Set animation to all the elements
        passwordIcon.setAnimation(animation);
        title.setAnimation(animation);
        desc.setAnimation(animation);
        phoneNo.setAnimation(animation);
        countryCodePicker.setAnimation(animation);
        forgotNxtBtn.setAnimation(animation);
    }

    public void verifyPhoneNumber(View view) {

        CheckInterNet checkInterNet = new CheckInterNet();
        if(!checkInterNet.isConnected(this)){
            showCustomDialog();
        }

        if (!validateFields()){
            return;
        }

        progressbar.setVisibility(View.VISIBLE);

        String _phoneNumber = phoneNo.getEditText().getText().toString().trim();

        if (_phoneNumber.charAt(0) == '0') {
            _phoneNumber = _phoneNumber.substring(1);
        }



        String _completePhoneNumber = "+" + countryCodePicker.getFullNumber() + _phoneNumber;

        Query checkUser = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("phoneNo").equalTo(_completePhoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    phoneNo.setError(null);
                    phoneNo.setErrorEnabled(false);

                    Toast.makeText(getApplicationContext(), "the login Type " +_loginType , Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                    intent.putExtra("phoneNo", _completePhoneNumber);
                    intent.putExtra("whatToDo", "updateData");
                    intent.putExtra("loginType", _loginType);
                    startActivity(intent);
                    finish();
                    progressbar.setVisibility(View.GONE);


                    } else {

                        progressbar.setVisibility(View.GONE);
                        phoneNo.setError("No such data exist!");
                        phoneNo.requestFocus();
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(ForgetPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showCustomDialog() {

    }

    public void callBackScreenFromForgetPassword(View view) {
        Intent intent = new Intent(getApplicationContext(), StartUpScreen.class);


        //Add Shared Animation
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.forget_password_back_btn), "transition_StartUp_Screen");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ForgetPassword.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private boolean validateFields() {

        String _phoneNumber = phoneNo.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";

        if (_phoneNumber.isEmpty()) {
            phoneNo.setError("field can't be empty");
            phoneNo.requestFocus();
            return false;
        } else if (!_phoneNumber.matches(checkSpaces)) {
            phoneNo.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNo.setError(null);
            return true;
        }

    }
}