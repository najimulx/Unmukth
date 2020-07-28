package com.example.dell.firstcry;

import com.example.dell.firstcry.Model.VacDriveObject;
import com.example.dell.firstcry.Model.VacType;
import com.example.dell.firstcry.View.Admin.ui.home.AdminHomeView;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Statics {


    public static FirebaseAuth mAUTH;
    public static FirebaseUser FIREBASE_USER;
    public static FirebaseDatabase FIREBASE_DATABASE;
    public static DatabaseReference mREF_USER;
    public static DatabaseReference mREF_VACC;
    public static DatabaseReference mREF_MAP_VAC;

    public static String ADMIN;
    public static LatLng LATLNG_CURRENT;


    public static ArrayList<VacDriveObject> VAC_DRIVE_LIST_ALL;
    public static ArrayList<String> VAC_DRIVE_LIST_KEY_ALL;

    public static ArrayList<VacDriveObject> VAC_HISTORY_LIST;
    public static ArrayList<String> VAC_HISTORY_LIST_KEY;
    public static ArrayList<String> VAC_TAKEN_BOOL_LIST;



    //Only used to call verify QR code//////////////
    public static AdminHomeView ADMIN_HOME_VIEW_X;
    public static VacDriveObject VAC_DRIVE_OBJ_X;
    public static String VAC_KEY_X;
    ///////////////////////////////////////////////
    public static ArrayList<String> VAC_TYPE;
    public static ArrayList<Integer> VAC_WEEK_S;
    public static ArrayList<Integer> VAC_WEEK_E;
    //////////////////////////////////////////////
    public static ArrayList<VacType> VAC_TYPE_OBJECT;
    public static String DOB;
    public static int WEEKS;


}
