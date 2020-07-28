package com.example.dell.firstcry.Adapter;


import android.content.Context;
import android.content.Intent;
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
import com.example.dell.firstcry.View.Admin.CreateFollowUpActivity;
import com.example.dell.firstcry.View.Admin.ui.home.AdminHomeView;

import java.util.ArrayList;

import static com.example.dell.firstcry.Statics.FIREBASE_USER;
import static com.example.dell.firstcry.Statics.mREF_USER;
import static com.example.dell.firstcry.Statics.mREF_VACC;

public class AdapterFeedAdmin extends RecyclerView.Adapter<AdapterFeedAdmin.MyViewHolder> {

    ArrayList<VacDriveObject> vacDriveObjectArrayList;
    ArrayList<String> enroll_count;
    ArrayList<String> keys ;
    AdminHomeView view;
    Context context;
    public AdapterFeedAdmin(AdminHomeView view,ArrayList<VacDriveObject> vacDriveObjectArrayList, ArrayList<String> keys
            ,ArrayList<String> enroll_count,Context context) {
        this.vacDriveObjectArrayList = vacDriveObjectArrayList;
        this.keys = keys;
        this.view = view;
        this.enroll_count = enroll_count;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.admin_feed_row,viewGroup,false);
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
            myViewHolder.bt_verify.setVisibility(View.GONE);
        }
        myViewHolder.bt_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.openQR_Scanner(vacDriveObjectArrayList.get(i),keys.get(i));
            }
        });
        myViewHolder.bt_end_drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mREF_USER.child(FIREBASE_USER.getUid()).child("vacc_index").child(keys.get(i)).setValue("false");
                mREF_VACC.child(keys.get(i)).child("expired").setValue("true");
            }
        });
        myViewHolder.tv_ecount.setText(enroll_count.get(i));
        myViewHolder.bt_followup_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context.getApplicationContext(), CreateFollowUpActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return vacDriveObjectArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title,tv_desc,tv_vacname,tv_loc,tv_paid,tv_timedate,tv_ecount;
        Button bt_verify,bt_end_drive,bt_followup_info;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.ll_user_vac_row);
            tv_title = itemView.findViewById(R.id.tv_title_user_vac_row);
            tv_desc = itemView.findViewById(R.id.tv_desc_user_vac_row);
            tv_loc = itemView.findViewById(R.id.tv_location_user_vac_row);
            tv_paid = itemView.findViewById(R.id.tv_paid_user_vac_row);
            tv_vacname = itemView.findViewById(R.id.tv_vacname_user_vac_row);
            tv_timedate = itemView.findViewById(R.id.tv_time_date_uservacrow);
            bt_verify = itemView.findViewById(R.id.bt_verifyQR_admin);
            bt_end_drive = itemView.findViewById(R.id.bt_end_drive_admin);
            tv_ecount = itemView.findViewById(R.id.tv_adminfeedrow_enrolled_count);
            bt_followup_info = itemView.findViewById(R.id.bt_addfollow_up_info_admin);
        }
    }

}
