package com.example.dell.firstcry.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import com.example.dell.firstcry.Model.VacType;
import com.example.dell.firstcry.R;
import java.util.ArrayList;

import static com.example.dell.firstcry.Statics.VAC_TYPE;
import static com.example.dell.firstcry.Statics.VAC_TYPE_OBJECT;
import static com.example.dell.firstcry.Statics.VAC_WEEK_E;
import static com.example.dell.firstcry.Statics.VAC_WEEK_S;


public class AdapterCheckBoxes extends RecyclerView.Adapter<AdapterCheckBoxes.MyViewHolder> {

    public AdapterCheckBoxes() {
        VAC_TYPE_OBJECT = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.check_boxes_row,viewGroup,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.cb_boxes.setText(VAC_TYPE.get(i) + " Week: "+VAC_WEEK_S.get(i)+" - "+VAC_WEEK_E.get(i));
        myViewHolder.cb_boxes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    VacType vacType = new VacType(VAC_TYPE.get(i),VAC_WEEK_S.get(i),VAC_WEEK_E.get(i));
                    VAC_TYPE_OBJECT.add(vacType);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return VAC_TYPE.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox cb_boxes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_boxes = itemView.findViewById(R.id.cb_checkboxes);
        }
    }

}
