package com.example.dell.firstcry.View;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dell.firstcry.R;
import com.example.dell.firstcry.View.Admin.AdminMainActivity;
import com.example.dell.firstcry.View.User.UserMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.dell.firstcry.Statics.DOB;
import static com.example.dell.firstcry.Statics.FIREBASE_DATABASE;
import static com.example.dell.firstcry.Statics.FIREBASE_USER;
import static com.example.dell.firstcry.Statics.VAC_TYPE;
import static com.example.dell.firstcry.Statics.VAC_WEEK_E;
import static com.example.dell.firstcry.Statics.VAC_WEEK_S;
import static com.example.dell.firstcry.Statics.WEEKS;
import static com.example.dell.firstcry.Statics.mAUTH;
import static com.example.dell.firstcry.Statics.mREF_MAP_VAC;
import static com.example.dell.firstcry.Statics.mREF_USER;
import static com.example.dell.firstcry.Statics.mREF_VACC;

public class SplashActivity extends AppCompatActivity {

    boolean connect = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_splash);



        ImageView imageView = findViewById(R.id.imageView4);
        Animation animation = new AlphaAnimation(1.0f, 0.1f);
        animation.setDuration(900);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        imageView.startAnimation(animation);
        initialize();
    }
    void initialize(){
        mAUTH = FirebaseAuth.getInstance();
        FIREBASE_USER = mAUTH.getCurrentUser();

        FIREBASE_DATABASE = FirebaseDatabase.getInstance("https://hacks3.firebaseio.com/");
        mREF_USER = FIREBASE_DATABASE.getReference("user");
        mREF_VACC = FIREBASE_DATABASE.getReference("vacc");
        mREF_MAP_VAC = FIREBASE_DATABASE.getReference("map_coord");

        CountDownTimer countDownTimer = new CountDownTimer(10000,100){
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                if (connect) {
                    Toast.makeText(SplashActivity.this, "Unable to Connect", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        };countDownTimer.start();

        if (FIREBASE_USER != null){
            mREF_USER.child(FIREBASE_USER.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String admin = dataSnapshot.child("ADMIN").getValue(String.class);
                    DOB = dataSnapshot.child("DOB").getValue(String.class);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = null;
                    try {
                        date1 = simpleDateFormat.parse(DOB);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date date2 = Calendar.getInstance().getTime();
                    long different = date2.getTime() - date1.getTime();
                    long secondsInMilli = 1000;
                    long minutesInMilli = secondsInMilli * 60;
                    long hoursInMilli = minutesInMilli * 60;
                    long daysInMilli = hoursInMilli * 24;
                    long elapsedDays = different / daysInMilli;
                    WEEKS = (int) (elapsedDays/7);
                    initVAC_TYPE();
                    if (admin.equals("true")){
                        startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                        countDownTimer.cancel();
                        connect = false;
                        finish();
                    }else{
                        startActivity(new Intent(getApplicationContext(), UserMainActivity.class));
                        countDownTimer.cancel();
                        connect = false;
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));finish();

        }
    }

    private void initVAC_TYPE(){
        List<String> namesList = Arrays.asList( "BCG","OPV","Hep-B","DTP","IPV","Hep-B"
        ,"Hib","Rotavirus","PCV","DTP","IPV","Hib","Rotavirus","PCV","DTP","DTP","IPV",
                "Hib","Rotavirus","PCV","OPV","Hep-B","OPV","MMR","Typhoid Conjugate Vaccine","Hep-A",
                "MMR","Varicella","PCV","DTP","IPV","Hib","Hep-A","Typhoid Conjugate Vaccine",
                "DTP","OPV","Varicella","MMR","Tdap");
        VAC_TYPE = new ArrayList<>();
        VAC_TYPE.addAll(namesList);
        ////////////////////

        ///////////////////
        List<Integer> startWeek = Arrays
                .asList(0,0,0,6,6,6,6,6,6,10,10,10,10,10,14,14,14,14,14,25,25,38,38,38,
                        52,64,64,64,68,68,68,77,104,208,208,208,208,520,520);
        List<Integer> endWeek = Arrays
                .asList(0,0,0,6,6,6,6,6,6,10,10,10,10,10,14,14,14,14,14,25,25,38,38,52,
                        52,64,64,64,77,77,77,77,104,312,312,312,312,624,624);
        VAC_WEEK_S = new ArrayList<>();
        VAC_WEEK_E = new ArrayList<>();
        VAC_WEEK_S.addAll(startWeek);
        VAC_WEEK_E.addAll(endWeek);
        for (int i = 0; i<VAC_WEEK_S.size();){
            if (VAC_WEEK_S.get(i)>WEEKS){
                break;
            }
            VAC_WEEK_S.remove(i);
            VAC_WEEK_E.remove(i);
            VAC_TYPE.remove(i);
        }
        for (int i = 0; i<VAC_WEEK_S.size();i++){
            VAC_WEEK_S.set(i,VAC_WEEK_S.get(i)-WEEKS);
            VAC_WEEK_E.set(i,VAC_WEEK_E.get(i)-WEEKS);
        }
    }


}
