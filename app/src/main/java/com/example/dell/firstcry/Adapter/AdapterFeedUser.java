package com.example.dell.firstcry.Adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.firstcry.Backend.UserModel;
import com.example.dell.firstcry.Model.VacDriveObject;
import com.example.dell.firstcry.R;
import com.example.dell.firstcry.View.Fragment.FeedView;

import java.util.ArrayList;

import static com.example.dell.firstcry.Statics.VAC_DRIVE_LIST_ALL;
import static com.example.dell.firstcry.Statics.VAC_DRIVE_LIST_KEY_ALL;

public class AdapterFeedUser extends RecyclerView.Adapter<AdapterFeedUser.MyViewHolder> {

    ArrayList<VacDriveObject> vacDriveObjectArrayList;
    ArrayList<String> keys ;
    FeedView view;
    UserModel userModel;
    public AdapterFeedUser(FeedView view) {
        this.vacDriveObjectArrayList = VAC_DRIVE_LIST_ALL;
        this.keys = VAC_DRIVE_LIST_KEY_ALL;
        this.view = view;
        userModel = new UserModel(view);
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
        myViewHolder.tv_title.setText(vacDriveObjectArrayList.get(i).TITLE.toUpperCase());
        myViewHolder.tv_vacname.setText("VACCINE NAME:"+vacDriveObjectArrayList.get(i).VACC_NAME);
        myViewHolder.tv_loc.setText("LOCATION:"+vacDriveObjectArrayList.get(i).LOCATION);
        myViewHolder.tv_desc.setText("Description:"+vacDriveObjectArrayList.get(i).DESC);
        myViewHolder.tv_paid.setText("PAYMENT:"+vacDriveObjectArrayList.get(i).PAID);
        myViewHolder.tv_timedate.setText("DATE:"+vacDriveObjectArrayList.get(i).DATE+" TIME:"+vacDriveObjectArrayList.get(i).TIME);
        if (vacDriveObjectArrayList.get(i).ENROLL.equals("Direct Visit")){
            myViewHolder.bt_enroll.setVisibility(View.GONE);
        }
        myViewHolder.bt_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userModel.performEnroll(vacDriveObjectArrayList.get(i), VAC_DRIVE_LIST_KEY_ALL.get(i));
            }
        });

    }

    @Override
    public int getItemCount() {
        return vacDriveObjectArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title,tv_desc,tv_vacname,tv_loc,tv_paid,tv_timedate;
        Button bt_enroll;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.ll_user_vac_row);
            tv_title = itemView.findViewById(R.id.tv_title_user_vac_row);
            tv_desc = itemView.findViewById(R.id.tv_desc_user_vac_row);
            tv_loc = itemView.findViewById(R.id.tv_location_user_vac_row);
            tv_paid = itemView.findViewById(R.id.tv_paid_user_vac_row);
            tv_vacname = itemView.findViewById(R.id.tv_vacname_user_vac_row);
            bt_enroll = itemView.findViewById(R.id.bt_enroll_user_vac_row);
            tv_timedate = itemView.findViewById(R.id.tv_time_date_uservacrow);
        }
    }

}
