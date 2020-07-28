package com.example.dell.firstcry.View.Admin.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.dell.firstcry.Adapter.AdapterCheckBoxes;
import com.example.dell.firstcry.Backend.AdminModel;
import com.example.dell.firstcry.R;
import com.example.dell.firstcry.View.Admin.AdminMainActivity;
import com.example.dell.firstcry.View.Admin.CheckBoxesView;

public class CheckBoxesVaccineFragment extends Fragment implements CheckBoxesView {

    private GalleryViewModel galleryViewModel;
    RecyclerView recyclerView;
    Button bt_save;
    AdapterCheckBoxes adapterCheckBoxes;
    AdminModel adminModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_vaccine_serve_list, container, false);

        recyclerView = root.findViewById(R.id.rv_checkboxes_vac);
        bt_save = root.findViewById(R.id.bt_save_Checkboxes_vac);
        adapterCheckBoxes = new AdapterCheckBoxes();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapterCheckBoxes);
        adminModel = new AdminModel(this);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminModel.uploadCheckBoxes(getContext());
            }
        });
        return root;
    }

    @Override
    public void checkboxUploadSuccess() {
        Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
        getActivity().startActivity(new Intent(getContext().getApplicationContext(), AdminMainActivity.class));
        getActivity().finish();
    }
}