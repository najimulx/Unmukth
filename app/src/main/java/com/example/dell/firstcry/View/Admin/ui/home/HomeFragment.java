package com.example.dell.firstcry.View.Admin.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.example.dell.firstcry.Adapter.AdapterFeedAdmin;
import com.example.dell.firstcry.Backend.StaticDownloaderModel;
import com.example.dell.firstcry.Model.VacDriveObject;
import com.example.dell.firstcry.R;
import com.example.dell.firstcry.View.Admin.CreateVacDriveActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;

import static com.example.dell.firstcry.Statics.ADMIN_HOME_VIEW_X;
import static com.example.dell.firstcry.Statics.VAC_DRIVE_OBJ_X;
import static com.example.dell.firstcry.Statics.VAC_KEY_X;

public class HomeFragment extends Fragment implements AdminHomeView {

    private HomeViewModel homeViewModel;
    StaticDownloaderModel staticDownloaderModel;
    AdapterFeedAdmin adapterFeedAdmin;
    RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        FloatingActionButton fab = root.findViewById(R.id.fab_admin);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CreateVacDriveActivity.class));
            }
        });
        recyclerView = root.findViewById(R.id.rv_feed_admin);
        staticDownloaderModel = new StaticDownloaderModel(this);
        staticDownloaderModel.performAdminDataDownload();
        return root;
    }

    @Override
    public void feedSuccess(ArrayList<VacDriveObject> vacDriveObjects, ArrayList<String> vacDriveKey,ArrayList<String> enroll_count) {
        adapterFeedAdmin = new AdapterFeedAdmin(this,vacDriveObjects,vacDriveKey,enroll_count, getContext());
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapterFeedAdmin);
    }

    @Override
    public void feedfail() {
        Toast.makeText(getContext(),"Failed to load data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openQR_Scanner(VacDriveObject vacDriveObject, String key) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setPrompt("Scanning");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
                ADMIN_HOME_VIEW_X = this;
                VAC_DRIVE_OBJ_X = vacDriveObject;
                VAC_KEY_X = key;
    }

    @Override
    public void qrVerifySuccess() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("VERIFICATION SUCCESS");
        alertDialog.setMessage("Thank you for using our service.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void qrVerifyFailed() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("FAILED");
        alertDialog.setMessage("Your token could not be verified.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


}