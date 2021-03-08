package com.demoapp.demo.Common.LoginSingUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.demoapp.demo.R;
import com.google.android.material.textfield.TextInputLayout;

public class customer_signUp_page1 extends AppCompatActivity {

    ImageView backBtn;
    Button next, login;
    TextView titleText, slideText;

    TextInputLayout fullName, userName, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customer_signup);

        backBtn = findViewById(R.id.customer_signup_page1_back_button);
        next = findViewById(R.id.customer_signup_page1_next_button);
        login = findViewById(R.id.customer_signup_page1_login_button);
        titleText = findViewById(R.id.customer_signup_page1_title_text);
        slideText = findViewById(R.id.customer_signup_page1_slide_text);


        fullName = findViewById(R.id.customer_signup_page1_fullname);
        userName = findViewById(R.id.customer_signup_page1_username);
        email = findViewById(R.id.customer_signup_page1_email);
        password = findViewById(R.id.customer_signup_page1_password);




    }

    public void callNextCustomerSigUpScreen1(View view) {



        if(!validateFullName() | !validateUserName() | !validateEmail() | !validatePassword()){
            return;
        }

        String _fullName = fullName.getEditText().getText().toString();
        String _email = email.getEditText().getText().toString();
        String _userName = userName.getEditText().getText().toString();
        String _password = password.getEditText().getText().toString();

        Intent intent = new Intent(getApplicationContext(), customer_signUp_page2.class);

        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("userName", _userName);
        intent.putExtra("password", _password);

        //Add Shared Animation
        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair(next, "transition_next_btn");
        pairs[2] = new Pair(login, "transition_login_btn");
        pairs[3] = new Pair(titleText, "transition_title_text");
        pairs[4] = new Pair(slideText, "transition_slide_text");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(customer_signUp_page1.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }


    private boolean validateFullName() {

        String val = fullName.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            fullName.setError("field can't be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName() {

        String val = userName.getEditText().getText().toString().trim();
        String checkSpaces = "\\A\\w{1,20}\\z";

        if (val.isEmpty()) {
            userName.setError("field can't be empty");
            return false;
        }
        else if(val.length()>20){
            userName.setError("User name is to long!!");
            return false;
        }
        else if(!val.matches(checkSpaces)){
            userName.setError("Field can not be empty");
            return false;
        }
        else {
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {

        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("field can't be empty");
            return false;
        }
        else if(!val.matches(checkEmail)){
            email.setError("Invalid Email!");
            return false;
        }
        else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {

        String val = password.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("field can't be empty");
            return false;
        }
        else if(!val.matches(checkPassword)){
            password.setError("Password should contain 4 characters!!");
            return false;
        }
        else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void callLoginFromSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), customer_login.class));
        finish();
    }
}