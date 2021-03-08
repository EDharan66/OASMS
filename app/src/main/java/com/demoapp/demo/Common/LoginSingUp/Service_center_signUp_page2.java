package com.demoapp.demo.Common.LoginSingUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.demoapp.demo.R;

import java.util.Calendar;

public class Service_center_signUp_page2 extends AppCompatActivity {

    ImageView backBtn;
    Button next, login;
    TextView titleText, slideText;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_service_center_sign_up_page2);

        //Hooks
        backBtn = findViewById(R.id.Service_center_signUp_page2_back_button);
        next = findViewById(R.id.Service_center_signUp_page2_next_button);
        login = findViewById(R.id.Service_center_signUp_page2_login_button);
        titleText = findViewById(R.id.Service_center_signUp_page2_title_text);
        slideText = findViewById(R.id.Service_center_signUp_page2_slide_text);
        radioGroup = findViewById(R.id.Service_center_signUp_page2_radio_group);
        datePicker = findViewById(R.id.Service_center_signUp_page2_age_picker);
    }

    public void call3rdSigupScreen(View view) {



        if(!validateAge() | !validateGender()){
            return;
        }

        String _fullName = getIntent().getStringExtra("fullName");
        String _email = getIntent().getStringExtra("email");
        String _userName = getIntent().getStringExtra("userName");
        String _password = getIntent().getStringExtra("password");

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String _gender = selectedGender.getText().toString();

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        String _date = day+"/"+month+"/"+year;

        Intent intent = new Intent(getApplicationContext(), Service_center_signUp_page3.class);


        intent.putExtra("fullName", _fullName);
        intent.putExtra("email", _email);
        intent.putExtra("userName", _userName);
        intent.putExtra("password", _password);
        intent.putExtra("date", _date);
        intent.putExtra("gender", _gender);

        //Add Transition and call next activity
        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair(next, "transition_next_btn");
        pairs[2] = new Pair(login, "transition_login_btn");
        pairs[3] = new Pair(titleText, "transition_title_text");
        pairs[4] = new Pair(slideText, "transition_slide_text");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Service_center_signUp_page2.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }


    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 14) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    public void callLoginFromSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), customer_login.class));
        finish();
    }
}