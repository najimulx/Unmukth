package com.example.dell.firstcry.Adapter;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dell.firstcry.View.Fragment.VacEnrolledUserFragment;
import com.example.dell.firstcry.View.Fragment.VacHistoryUserFragment;

public class AdapterPagerVacHistory extends FragmentPagerAdapter {

    public AdapterPagerVacHistory(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new VacEnrolledUserFragment();
                break;
            case 1:
                fragment = new VacHistoryUserFragment();
                break;
            default:
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Enrolled";
            case 1:
                return "History";
            default:
                return "Enrolled";
        }
    }
}
