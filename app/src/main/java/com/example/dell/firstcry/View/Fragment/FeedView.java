package com.example.dell.firstcry.View.Fragment;

import com.example.dell.firstcry.Model.VacDriveObject;

public interface FeedView {
    void feedSuccess();
    void feedfail();
    void enrollSuccess(String string);
    void enrollFail();

    void showPaymentGateway(String key,VacDriveObject vacDriveObject);
}
