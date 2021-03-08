package com.demoapp.demo.Common.OTP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.demoapp.demo.R;
import com.demoapp.demo.User.UserDashboard;

public class MakeSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_make_selection);
    }

    public void callBackScreenFromMakeSelection(View view) {
        startActivity(new Intent(getApplicationContext(), ForgetPassword.class));
        finish();
    }

    public void callOTPScreenFromMakeSelection(View view) {

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);


        //Add Shared Animation
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(findViewById(R.id.call_otp_screen), "transition_OTP_screen");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MakeSelection.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }


}