package com.example.dell.firstcry.View.User;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dell.firstcry.R;
import com.example.dell.firstcry.View.SplashActivity;

public class Splash02 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash02);
        CountDownTimer countDownTimer = new CountDownTimer(5000,100){
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
               startActivity(new Intent(getApplicationContext(),SplashActivity.class));
                finish();
            }
        };countDownTimer.start();

    }
}
