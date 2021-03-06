package com.example.dell.firstcry.Adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.firstcry.Model.VacDriveObject;
import com.example.dell.firstcry.R;

import java.util.ArrayList;

import static com.example.dell.firstcry.Statics.VAC_TAKEN_BOOL_LIST;

public class AdapterVacHistory extends RecyclerView.Adapter<AdapterVacHistory.MyViewHolder> {

    ArrayList<VacDriveObject> vacDriveObjectArrayList;
    public AdapterVacHistory(ArrayList<VacDriveObject> vacDriveObjects) {
        vacDriveObjectArrayList = new ArrayList<>();
        VacDriveObject vacDriveObject = new VacDriveObject();
        vacDriveObject.TIME = "H:04 - M:30";
        vacDriveObject.DATE = "12/01/2020";
        vacDriveObject.ENROLL = "Enrollment Needed";
        vacDriveObject.PAID = "Free Of Cost";
        vacDriveObject.VACC_NAME = "Diptheria";
        vacDriveObject.LOCATION = "Makum Road, Tinisukia";
        vacDriveObject.DESC = "Registration Prior is needed";
        vacDriveObjectArrayList.add(vacDriveObject);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.user_vac_row,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            if (VAC_TAKEN_BOOL_LIST.get(i).equals("true")) {
                myViewHolder.tv_title.setText(vacDriveObjectArrayList.get(i).TITLE);
                myViewHolder.tv_vacname.setText("VACCINE NAME:"+vacDriveObjectArrayList.get(i).VACC_NAME);
                myViewHolder.tv_loc.setText("LOCATION:"+vacDriveObjectArrayList.get(i).LOCATION);
                myViewHolder.tv_desc.setText("DESC"+vacDriveObjectArrayList.get(i).DESC);
                myViewHolder.tv_paid.setText("PRICE:"+vacDriveObjectArrayList.get(i).PAID);
                myViewHolder.bt_enroll.setVisibility(View.GONE);
            }
            else{myViewHolder.linearLayout.setVisibility(View.GONE);}
    }

    @Override
    public int getItemCount() {
        return vacDriveObjectArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title,tv_desc,tv_vacname,tv_loc,tv_paid;
        LinearLayout linearLayout;
        Button bt_enroll;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title_user_vac_row);
            tv_desc = itemView.findViewById(R.id.tv_desc_user_vac_row);
            tv_loc = itemView.findViewById(R.id.tv_location_user_vac_row);
            tv_paid = itemView.findViewById(R.id.tv_paid_user_vac_row);
            tv_vacname = itemView.findViewById(R.id.tv_vacname_user_vac_row);
            bt_enroll = itemView.findViewById(R.id.bt_enroll_user_vac_row);
            linearLayout = itemView.findViewById(R.id.ll_user_vac_row);
        }
    }

}
