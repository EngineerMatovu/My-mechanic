package com.example.android.io2014.fire;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.example.android.io2014.Config;
import com.example.android.io2014.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    FloatingActionButton floatingActionButton;
    private Marker garage;
    private Marker garage1;
    private GoogleMap mMap;
    String log, latitude;
    Double l, g;
    FloatingActionButton fabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fabs = (FloatingActionButton) findViewById(R.id.fab);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        log = getIntent().getStringExtra("log");
        latitude = getIntent().getStringExtra("lat");


        Firebase.setAndroidContext(this);
        fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase fire = new Firebase(Config.FIREBASE_PRODUCTS_URL);
                fire.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
//                            double location_left = dataSnapshot.child("latitude").getValue(Double.class);
//                            double location_right = dataSnapshot.child("logitude").getValue(Double.class);
//                            LatLng sydney = new LatLng(location_left, location_right);
//                            mMap.addMarker(new MarkerOptions().position(sydney).title(dataSnapshot.child("garagename").toString()));
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
                        } else {
                            Log.e("", "onDataChange: No data");
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        throw firebaseError.toException();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void marker(Double l, Double g) {
        LatLng f = new LatLng(l, g);
        garage1 = mMap.addMarker(new MarkerOptions()
                .position(f)
                .title("garage1"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(f));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(log));
        mMap.addMarker(new MarkerOptions().position(sydney).title(getIntent().getStringExtra("name")));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            LatLng f = new LatLng(0.335,32.57);
        garage1 = mMap.addMarker(new MarkerOptions()
                .position(f)
                .title("Your Current location"));

        mMap.setOnMarkerClickListener(this);
        mMap
                            .addPolyline((new PolylineOptions())
                                    .add(sydney, f).width(5).color(Color.BLUE)
                                    .geodesic(true));
        // move camera to zoom on map

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

    public double distanceFrom( double lat2, double lng2) {
        Double  l=0.335;
        Double lg=32.57;
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-l);
        double dLng = Math.toRadians(lng2-lg);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(l)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;
        int meterConversion = 1609;
        return new Double(dist * meterConversion).floatValue();    // this will return distance
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
