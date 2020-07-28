package com.example.dell.firstcry.View.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.firstcry.Adapter.AdapterFeedUser;
import com.example.dell.firstcry.Adapter.AdapterUserVaccine;
import com.example.dell.firstcry.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {

    AdapterUserVaccine adapterUserVaccine;
    RecyclerView recyclerView;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_upcoming, container, false);

        recyclerView = v.findViewById(R.id.rv_upcoming);
        adapterUserVaccine = new AdapterUserVaccine();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapterUserVaccine);

        return v;
    }

}
