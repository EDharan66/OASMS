package com.demoapp.demo.Common.OTP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.MalformedJsonException;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.demoapp.demo.Common.LoginSingUp.StartUpScreen;
import com.demoapp.demo.Common.LoginSingUp.customer_signUp_page3;
import com.demoapp.demo.R;
import com.demoapp.demo.User.UserDashboard;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);
    }

    public void verifyPhoneNumber(View view) {
        startActivity(new Intent(getApplicationContext(), MakeSelection.class));
        finish();
    }

    public void callBackScreenFromForgetPassword(View view) {
        Intent intent = new Intent(getApplicationContext(), StartUpScreen.class);


        //Add Shared Animation
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.forget_password_back_btn), "transition_StartUp_Screen");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ForgetPassword.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}