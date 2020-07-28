package com.example.dell.firstcry.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.firstcry.Adapter.AdapterVacHistory;
import com.example.dell.firstcry.Model.VacDriveObject;
import com.example.dell.firstcry.R;

import java.util.ArrayList;

import static com.example.dell.firstcry.Statics.VAC_HISTORY_LIST;
import static com.example.dell.firstcry.Statics.VAC_TAKEN_BOOL_LIST;

public class VacHistoryUserFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterVacHistory adapterVacHistory;
    LinearLayoutManager llm;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_hello_list,container,false);
        ArrayList<VacDriveObject> vacDriveObjects = new ArrayList<>();
        for (int i = 0; i<VAC_TAKEN_BOOL_LIST.size();i++){
            if (VAC_TAKEN_BOOL_LIST.get(i).equals("true")) {
                vacDriveObjects.add(VAC_HISTORY_LIST.get(i));
            }
        }
        adapterVacHistory = new AdapterVacHistory(vacDriveObjects);
        llm = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.recyclerView_helloList);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapterVacHistory);
        adapterVacHistory.notifyDataSetChanged();
        return view;
    }

}
