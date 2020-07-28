package com.example.dell.firstcry.View;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dell.firstcry.R;
import com.example.dell.firstcry.View.User.Splash02;

public class Splash01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash01);
        CountDownTimer countDownTimer = new CountDownTimer(2000,100){
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), Splash02.class));
                finish();
            }
        };countDownTimer.start();

    }
}
