package com.example.dell.firstcry.View.User;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dell.firstcry.Adapter.AdapterPagerVacHistory;
import com.example.dell.firstcry.R;

public class VacHistoryActivity extends AppCompatActivity {


    ViewPager viewPager;
    AdapterPagerVacHistory adapterPagerVacHistory;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vac_history);

        final ActionBar abar = getSupportActionBar();
        if(abar!=null) {
            abar.setDisplayHomeAsUpEnabled(true);
            abar.setTitle("Connections");
        }
        viewPager = findViewById(R.id.fragment_container_hello);
        adapterPagerVacHistory = new AdapterPagerVacHistory(getSupportFragmentManager());
        tabLayout = findViewById(R.id.tab_bar_hello);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapterPagerVacHistory);
        viewPager.setCurrentItem(getIntent().getIntExtra("tab",0));

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return  false;
    }

}
