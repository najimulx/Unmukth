package com.example.dell.firstcry.View.Fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dell.firstcry.Adapter.AdapterVacEnrolled;
import com.example.dell.firstcry.Model.VacDriveObject;
import com.example.dell.firstcry.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

import static com.example.dell.firstcry.Statics.FIREBASE_USER;
import static com.example.dell.firstcry.Statics.VAC_HISTORY_LIST;
import static com.example.dell.firstcry.Statics.VAC_TAKEN_BOOL_LIST;

public class VacEnrolledUserFragment extends Fragment implements VacEnrolledView{
    RecyclerView recyclerView;
    AdapterVacEnrolled adapterVacHistory;
    LinearLayoutManager llm;
    View view;
    Dialog dialog_QR;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_hello_list,container,false);
        ArrayList<VacDriveObject> vacDriveObjects = new ArrayList<>();
        for (int i = 0; i<VAC_TAKEN_BOOL_LIST.size();i++){
            if (VAC_TAKEN_BOOL_LIST.get(i).equals("false")) {
                vacDriveObjects.add(VAC_HISTORY_LIST.get(i));
            }
        }
        adapterVacHistory = new AdapterVacEnrolled(this,vacDriveObjects);
        llm = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recyclerView_helloList);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapterVacHistory);
        adapterVacHistory.notifyDataSetChanged();

        return view;
    }
    public void enrollSuccess(String string) {
        dialog_QR = new Dialog(getContext());
        dialog_QR.setContentView(R.layout.dialog_qr_);
        dialog_QR.show();
        Button close = dialog_QR.findViewById(R.id.bt_qr_dialog);
        ImageView imageView = dialog_QR.findViewById(R.id.iv_qr_dialog);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_QR.cancel();
            }
        });
        if (string != null && !string.isEmpty()){
            try{
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                BitMatrix bitMatrix = multiFormatWriter
                        .encode(string, BarcodeFormat.QR_CODE,500,500);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getContext(),"QR code generation failed",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showQR() {
        enrollSuccess(FIREBASE_USER.getUid());
    }
}
