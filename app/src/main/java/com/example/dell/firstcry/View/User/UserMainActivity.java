package com.example.dell.firstcry.View.User;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.example.dell.firstcry.Backend.StaticDownloaderModel;
import com.example.dell.firstcry.R;
import com.example.dell.firstcry.View.Fragment.CommerceFragment;
import com.example.dell.firstcry.View.Fragment.FeedFragment;
import com.example.dell.firstcry.View.Fragment.UpcomingFragment;
import com.example.dell.firstcry.View.LoginActivity;


import static com.example.dell.firstcry.Statics.mAUTH;

public class UserMainActivity extends AppCompatActivity
        implements OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Unmukt");


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        View view = bottomNavigationView.findViewById(R.id.bot_nav_article);
        view.performClick();
        getFragmentManager().beginTransaction().replace(R.id.frame_layout,new FeedFragment()).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mAUTH.signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.govt_scheme) {
            startActivity(new Intent(getApplicationContext(),VacHistoryActivity.class));
        } else if (id == R.id.share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBody = "Share This App Now";
            String shareSub = "Sub";
            intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            intent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(intent,"Share using"));

        } else if (id == R.id.nearby_hospitals) {
            startActivity(new Intent(getApplicationContext(),UserMapsActivity.class));
        } /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.bot_nav_article:
                            selectedFragment = new FeedFragment();
                            break;
                        case R.id.bot_nav_diet:
                            selectedFragment = new UpcomingFragment();
                            break;
                        case R.id.bot_nav_commerce:
                            selectedFragment = new CommerceFragment();
                            break;
                    }
                    getFragmentManager().beginTransaction().replace(R.id.frame_layout,selectedFragment).commit();
                    return true;
                }
            };

    @Override
    protected void onStart() {
        super.onStart();
        StaticDownloaderModel model = new StaticDownloaderModel();
        model.performUserVacHistoryDownload();
    }


}
