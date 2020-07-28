package com.example.dell.firstcry.View.Admin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dell.firstcry.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminMapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener{
    GoogleMap mMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    SearchView searchView;
    LatLng mlatLng;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maps);
        searchView = findViewById(R.id.searchView);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String qLoc = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (qLoc != null && !qLoc.equals("")){
                    Geocoder qGeocoder = new Geocoder(AdminMapsActivity.this);
                    try{
                        addressList = qGeocoder.getFromLocationName(qLoc,1);
                    }catch (Exception e){e.printStackTrace();}
                    Address address = addressList.get(0);
                    LatLng qlatLng = new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.clear();
                    MarkerOptions qmarkerOptions = new MarkerOptions().position(qlatLng);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(qlatLng));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(qlatLng, 12));
                    mMap.addMarker(qmarkerOptions);
                    mlatLng = qlatLng;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        Button bt_done = findViewById(R.id.bt_admin_maps);
        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder rgeocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
                String geoLocation = "Not Found";
                try {
                    List<Address> listAddress = rgeocoder.getFromLocation(mlatLng.latitude,mlatLng.longitude,1);
                    if(listAddress != null && listAddress.size()>0){
                        if(listAddress.get(0).getThoroughfare()!= null){
                            geoLocation = "";
                            if (listAddress.get(0).getSubThoroughfare()!=null){
                                geoLocation += listAddress.get(0).getSubThoroughfare();
                            }
                            geoLocation += listAddress.get(0).getThoroughfare();
                            if (listAddress.get(0).getLocality() != null){
                                geoLocation += " "+listAddress.get(0).getLocality();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(),CreateVacDriveActivity.class);
                intent.putExtra("result",geoLocation);
                intent.putExtra("lat",mlatLng.latitude);
                intent.putExtra("long",mlatLng.longitude);
                Log.i("AdminMap",geoLocation);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mlatLng = latLng;
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
        );
        mlatLng = latLng;
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    mlatLng = new LatLng(location.getLatitude(),location.getLongitude());
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(AdminMapsActivity.this);
                }
            }
        });
    }
}
