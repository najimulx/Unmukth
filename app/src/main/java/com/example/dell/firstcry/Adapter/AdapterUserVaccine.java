package com.example.dell.firstcry.Adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.firstcry.R;

import static com.example.dell.firstcry.Statics.VAC_TYPE;
import static com.example.dell.firstcry.Statics.VAC_WEEK_E;
import static com.example.dell.firstcry.Statics.VAC_WEEK_S;

public class AdapterUserVaccine extends RecyclerView.Adapter<AdapterUserVaccine.MyViewHolder> {


    public AdapterUserVaccine() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.row_vaccine_list,viewGroup,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_vaccName.setText(VAC_TYPE.get(i));


        myViewHolder.tv_vacPeriod.setText(VAC_WEEK_S.get(i)+"w to "+VAC_WEEK_E.get(i)+"w");

    }
    @Override
    public int getItemCount() {
        return VAC_TYPE.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_vaccName,tv_vacPeriod;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_vaccName = itemView.findViewById(R.id.tv_vacName);
            tv_vacPeriod = itemView.findViewById(R.id.tv_vaccine_week);
        }
    }

}
