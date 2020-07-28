package com.example.dell.firstcry.Backend;

import android.support.annotation.NonNull;

import com.example.dell.firstcry.Model.VacDriveObject;
import com.example.dell.firstcry.Model.VacType;
import com.example.dell.firstcry.View.Fragment.FeedView;
import com.example.dell.firstcry.View.User.UserMapView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.dell.firstcry.Statics.FIREBASE_USER;
import static com.example.dell.firstcry.Statics.mREF_MAP_VAC;
import static com.example.dell.firstcry.Statics.mREF_USER;
import static com.example.dell.firstcry.Statics.mREF_VACC;

public class UserModel {

    FeedView view;
    boolean flag = true;
    UserMapView userMapView;
    public UserModel(FeedView view) {
        this.view = view;
    }

    public UserModel(UserMapView userMapView) {
        this.userMapView = userMapView;
    }

    public void performEnroll(VacDriveObject vacDriveObject, String key){
        if (!vacDriveObject.PAID.equals("Free")){
            paymentGateway(key,vacDriveObject);
        }
        else{
            performQRGeneration(vacDriveObject,key);
        }
    }
    public void performQRGeneration(VacDriveObject vacDriveObject, String key){
        mREF_VACC.child(key).child("enroll_List").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.getKey().equals(FIREBASE_USER.getUid())) {
                                flag = false;
                            }
                        }
                    }
                    if (flag) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                        String currentDateandTime = sdf.format(new Date());
                        mREF_VACC.child(key).child("enroll_list").child(FIREBASE_USER.getUid()).child("time").setValue(currentDateandTime);
                        mREF_VACC.child(key).child("enroll_list").child(FIREBASE_USER.getUid()).child("done").setValue("false");
                        mREF_USER.child(FIREBASE_USER.getUid()).child("enrolled_vac").child(key).setValue("true");
                        StaticDownloaderModel model = new StaticDownloaderModel();
                        model.performUserVacHistoryDownload();
                        view.enrollSuccess(FIREBASE_USER.getUid());
                    }
                    if (!flag){
                        view.enrollFail();
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void paymentGateway(String vacID,VacDriveObject vac_DriveObj){
      view.showPaymentGateway(vacID, vac_DriveObj);
    }

    public void performUserMapDataLoad(){
        ArrayList<VacType> latLngArrayList = new ArrayList<>();
        mREF_MAP_VAC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if (snapshot!=null){
                        latLngArrayList.add(snapshot.getValue(VacType.class));
                    }
                }
                userMapView.mapDataSuccess(latLngArrayList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
