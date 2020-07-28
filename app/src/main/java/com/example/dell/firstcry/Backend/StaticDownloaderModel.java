package com.example.dell.firstcry.Backend;

import android.support.annotation.NonNull;

import com.example.dell.firstcry.Model.VacDriveObject;
import com.example.dell.firstcry.View.Admin.ui.home.AdminHomeView;
import com.example.dell.firstcry.View.Fragment.FeedView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.dell.firstcry.Statics.FIREBASE_USER;
import static com.example.dell.firstcry.Statics.VAC_DRIVE_LIST_ALL;
import static com.example.dell.firstcry.Statics.VAC_DRIVE_LIST_KEY_ALL;
import static com.example.dell.firstcry.Statics.VAC_HISTORY_LIST;
import static com.example.dell.firstcry.Statics.VAC_HISTORY_LIST_KEY;
import static com.example.dell.firstcry.Statics.VAC_TAKEN_BOOL_LIST;
import static com.example.dell.firstcry.Statics.mREF_USER;
import static com.example.dell.firstcry.Statics.mREF_VACC;

public class StaticDownloaderModel {

    FeedView feedView;
    AdminHomeView adminHomeView;

    public StaticDownloaderModel(AdminHomeView adminHomeView) {
        this.adminHomeView = adminHomeView;
    }

    public StaticDownloaderModel(FeedView feedView) {
        this.feedView = feedView;
    }

    public StaticDownloaderModel() {
    }

    public void performUserFeedDownload(){
        VAC_DRIVE_LIST_ALL = new ArrayList<>();
        VAC_DRIVE_LIST_KEY_ALL = new ArrayList<>();
        mREF_VACC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getChildrenCount()>0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.child("expired").getValue() != null) {
                            continue;
                        }
                        VAC_DRIVE_LIST_ALL.add(snapshot.getValue(VacDriveObject.class));
                        VAC_DRIVE_LIST_KEY_ALL.add(snapshot.getKey());

                    }
                    feedView.feedSuccess();
                }
                else{feedView.feedfail();}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                feedView.feedfail();
            }
        });

    }
    public void performUserVacHistoryDownload(){
        VAC_HISTORY_LIST_KEY = new ArrayList<>();
        mREF_USER.child(FIREBASE_USER.getUid()).child("enrolled_vac")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null && dataSnapshot.getChildrenCount()>0){
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                VAC_HISTORY_LIST_KEY.add(snapshot.getKey());
                            }
                        }
                        VAC_HISTORY_LIST = new ArrayList<>();
                        VAC_TAKEN_BOOL_LIST = new ArrayList<>();
                        mREF_VACC.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null && dataSnapshot.getChildrenCount()>0){
                                    for (String s:VAC_HISTORY_LIST_KEY){
                                        VAC_HISTORY_LIST.add(dataSnapshot.child(s).getValue(VacDriveObject.class));
                                        VAC_TAKEN_BOOL_LIST
                                                .add(dataSnapshot.child(s).child("enroll_list")
                                                        .child(FIREBASE_USER.getUid()).child("done").getValue(String.class));
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void performAdminDataDownload(){
        ArrayList<VacDriveObject> vacDriveObjects = new ArrayList<>();
        ArrayList<String> vacDriveKey = new ArrayList<>();
        ArrayList<String> enroll_count = new ArrayList<>();
        mREF_USER.child(FIREBASE_USER.getUid()).child("vacc_index").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if (snapshot.getValue(String.class).equals("true")) {
                        vacDriveKey.add(snapshot.getKey());
                    }
                }
                mREF_VACC.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (String s:vacDriveKey){
                            vacDriveObjects.add(dataSnapshot.child(s).getValue(VacDriveObject.class));
                            if (dataSnapshot.child(s).child("enroll_list").getChildrenCount()>0){
                                enroll_count.add(Long.toString(dataSnapshot.child(s).child("enroll_list").getChildrenCount()));
                            }else{enroll_count.add("0");}
                        }
                        adminHomeView.feedSuccess(vacDriveObjects,vacDriveKey,enroll_count);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                        adminHomeView.feedfail();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                adminHomeView.feedfail();
            }
        });

    }

}
