package com.example.dell.firstcry.Backend;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.dell.firstcry.Model.LatLng;
import com.example.dell.firstcry.Model.VacType;
import com.example.dell.firstcry.View.Admin.CheckBoxesView;
import com.example.dell.firstcry.View.Admin.ui.home.AdminHomeView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.dell.firstcry.Statics.ADMIN_HOME_VIEW_X;
import static com.example.dell.firstcry.Statics.VAC_KEY_X;
import static com.example.dell.firstcry.Statics.VAC_TYPE_OBJECT;
import static com.example.dell.firstcry.Statics.mREF_MAP_VAC;
import static com.example.dell.firstcry.Statics.mREF_VACC;
import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class AdminModel {

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    AdminHomeView adminHomeView;
    boolean flag = false;
    CheckBoxesView checkBoxesView;
    LatLng mlatLng;
    public AdminModel(CheckBoxesView checkBoxesView) {
        this.checkBoxesView = checkBoxesView;
    }

    public AdminModel(AdminHomeView adminHomeView) {
        this.adminHomeView = adminHomeView;
    }

    public void performVerifyQR(String QR_DATA){
        mREF_VACC.child(VAC_KEY_X).child("enroll_list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if (QR_DATA.equals(snapshot.getKey())){
                        flag = true;
                    }
                }
                if (flag){
                    String done = dataSnapshot.child(QR_DATA).child("done").getValue(String.class);
                    if (!done.equals("true")){
                        ADMIN_HOME_VIEW_X.qrVerifySuccess();
                        updateQR_VERIFY(QR_DATA);
                    }
                    else{
                        ADMIN_HOME_VIEW_X.qrVerifyFailed();
                    }
                }
                else{
                    ADMIN_HOME_VIEW_X.qrVerifyFailed();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void updateQR_VERIFY(String UID){
        mREF_VACC.child(VAC_KEY_X).child("enroll_list").child(UID).child("done").setValue("true");
    }

    public void uploadCheckBoxes(Context context){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    mlatLng = new LatLng(location.getLatitude(),location.getLongitude());
                    for (VacType v: VAC_TYPE_OBJECT){
                        v.LATLNG = mlatLng;
                        String key = mREF_MAP_VAC.push().getKey();
                        mREF_MAP_VAC.child(key).setValue(v);
                        Log.i("TAG",v.LATLNG.toString());
                    }
                    checkBoxesView.checkboxUploadSuccess();
                }
            }
        });
    }
}
