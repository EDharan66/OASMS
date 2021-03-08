package com.demoapp.demo.Common.OTP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.demoapp.demo.Common.LoginSingUp.StartUpScreen;
import com.demoapp.demo.R;

public class SetNewPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);
    }

    public void goToHomeFromSetNewPassword(View view) {
        startActivity(new Intent(getApplicationContext(), StartUpScreen.class));
        finish();
    }

    public void setNewPasswordBtn(View view) {

        startActivity(new Intent(getApplicationContext(), SuccessMessage.class));
        finish();
    }
}