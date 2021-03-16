package com.demoapp.demo.Common.OTP;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demoapp.demo.Common.LoginSingUp.StartUpScreen;
import com.demoapp.demo.HelperClass.CheckInterNet;
import com.demoapp.demo.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetNewPassword extends AppCompatActivity {

    TextInputLayout newPassword, confrimPassword;
    Button updateBtn;
    RelativeLayout progressBar;
    ImageView setNewPasswordIcon;
    TextView setNewPasswordTitle, setNewPasswordDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);


        progressBar = findViewById(R.id.progress_bar);
        newPassword = findViewById(R.id.new_password);
        confrimPassword = findViewById(R.id.confirm_password);
        updateBtn = findViewById(R.id.set_new_password_btn);
        setNewPasswordIcon = findViewById(R.id.set_new_password_icon);
        setNewPasswordTitle = findViewById(R.id.set_new_password_title);
        setNewPasswordDesc = findViewById(R.id.set_new_password_description);


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.side_anim);

        //Set animation to all the elements
        setNewPasswordIcon.setAnimation(animation);
        setNewPasswordTitle.setAnimation(animation);
        setNewPasswordDesc.setAnimation(animation);
        newPassword.setAnimation(animation);
        confrimPassword.setAnimation(animation);
        updateBtn.setAnimation(animation);


    }

    public void goToHomeFromSetNewPassword(View view) {
        startActivity(new Intent(getApplicationContext(), StartUpScreen.class));
        finish();
    }

    public void setNewPasswordBtn(View view) {

        CheckInterNet checkInterNet = new CheckInterNet();
        if(!checkInterNet.isConnected(this)){
            showCustomDialog();
        }

        if (!validatePassword() | !validateConfrimPassword()){

            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        String _newPassword = newPassword.getEditText().getText().toString().trim();
        String _phoneNumber = getIntent().getStringExtra("phoneNo");
        String _loginType = getIntent().getStringExtra("loginType");


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(_loginType).child(_phoneNumber).child("password").setValue(_newPassword);

        startActivity(new Intent(getApplicationContext(),SuccessMessage.class));
        finish();





    }

    private boolean validateConfrimPassword() {
        String val = confrimPassword.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            confrimPassword.setError("field can't be empty");
            return false;
        }
        else if(!val.matches(checkPassword)){
            confrimPassword.setError("Password should contain any special characters [eg.. @,$,&..]!!");
            return false;
        }
        else {
            confrimPassword.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = newPassword.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            newPassword.setError("field can't be empty");
            return false;
        }
        else if(!val.matches(checkPassword)){
            newPassword.setError("Password should contain any special characters [eg.. @,$,&..]!!");
            return false;
        }
        else {
            newPassword.setError(null);
            return true;
        }
    }

    private void showCustomDialog() {

    }
}