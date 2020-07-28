package com.example.dell.firstcry.View.Admin.ui.home;

import com.example.dell.firstcry.Model.VacDriveObject;

import java.util.ArrayList;

public interface AdminHomeView {

    void feedSuccess(ArrayList<VacDriveObject> vacDriveObjects,ArrayList<String> vacDriveKey,ArrayList<String> arrayList);
    void feedfail();

    void openQR_Scanner(VacDriveObject vacDriveObject,String key);
    void qrVerifySuccess();
    void qrVerifyFailed();

}
