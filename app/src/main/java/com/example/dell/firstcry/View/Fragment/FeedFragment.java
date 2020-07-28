package com.example.dell.firstcry.View.Fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dell.firstcry.Adapter.AdapterFeedUser;
import com.example.dell.firstcry.Backend.StaticDownloaderModel;
import com.example.dell.firstcry.Backend.UserModel;
import com.example.dell.firstcry.Model.VacDriveObject;
import com.example.dell.firstcry.Model.VacType;
import com.example.dell.firstcry.R;
import com.example.dell.firstcry.View.User.UserMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.dell.firstcry.Statics.mREF_VACC;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment implements FeedView{
    RecyclerView recyclerView;
    AdapterFeedUser adapterFeedUser;
    View v;
    Boolean flag = false;
    FeedView view;
    StaticDownloaderModel staticDownloaderModel;
    Dialog dialog_QR,dialog_gateway,dialog_tick;
    public FeedFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_feed, container, false);
        recyclerView = v.findViewById(R.id.rv_feed_user);
        staticDownloaderModel = new StaticDownloaderModel(this);
        staticDownloaderModel.performUserFeedDownload();
        view = this;

        return v;
    }

    @Override
    public void feedSuccess() {
        adapterFeedUser = new AdapterFeedUser(this);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapterFeedUser);
        CountDownTimer countDownTimer = new CountDownTimer(5000,100){
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                flag = true;
            }
        };countDownTimer.start();

        mREF_VACC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (flag){
                    notification(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void feedfail() {
       // Toast.makeText(getContext(),"Failed To Download Feed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void enrollSuccess(String string) {
        dialog_tick = new Dialog(getContext());
        dialog_tick.setContentView(R.layout.dialog_qr);
        dialog_tick.show();
        ImageView mImgCheck = (ImageView) dialog_tick.findViewById(R.id.iv_qr_dialog_tick);
        ((Animatable) mImgCheck.getDrawable()).start();
        ImageView iv_tick = dialog_tick.findViewById(R.id.iv_qr_dialog_tick);
        iv_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_tick.dismiss();

            }
        });



    }

    @Override
    public void enrollFail() {
        Toast.makeText(getContext(),"Failed to enroll",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPaymentGateway(String key, VacDriveObject vacDriveObject) {
        dialog_gateway = new Dialog(getContext());
        dialog_gateway.setContentView(R.layout.dialog_payment_gateway);
        Button bt = dialog_gateway.findViewById(R.id.bt_dialog_enroll);
        dialog_gateway.show();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserModel(view).performQRGeneration(vacDriveObject,key);
                dialog_gateway.cancel();
            }
        });
    }


    void notification(VacType vacType){
        createNotificationChannel();

        String title = "Vaccination Drive Available";
        String body = "Press to get more information";

        PendingIntent contentIntent =
                PendingIntent.getActivity(getContext(), 0, new Intent(getContext(), UserMainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "FCM_CHANNEL")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(999, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "FCM_Channel_name";
            String description = "For FCM Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("FCM_CHANNEL", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void notified(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        mBuilder.setSmallIcon(R.drawable.bell);
        mBuilder.setContentTitle("New Vaccination Drive");
        mBuilder.setContentText("Hi, Enroll into the latest vaccine drive!");
        NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        mNotificationManager.notify(111, mBuilder.build());

    }
}
