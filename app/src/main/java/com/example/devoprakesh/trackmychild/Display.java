package com.example.devoprakesh.trackmychild;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Display extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = Display.class.getSimpleName();
    private HashMap<String,Marker> markerHashMap = new HashMap<>();
    private GoogleMap mMap;
    private String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        num = intent.getStringExtra("Number");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMaxZoomPreference(16);
        MapUpdates();
        // Add a marker in Sydney and move the camera


    }

    private void MapUpdates(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Childrens");
        ref.child(num).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                setMarker(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                setMarker(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d("Error :","Failed to read value",databaseError.toException());
            }
        });

    }

    private void setMarker(DataSnapshot dataSnapshot){

        String key = dataSnapshot.getKey();
        Log.i("data:",key);
        Log.i("data:",dataSnapshot.getValue().toString());
        if(key.equals("Current")){

            HashMap<String, Object> value = (HashMap<String, Object>)dataSnapshot.getValue();
            //value.put;
            //dataSnapshot.get
            double lat = Double.parseDouble(value.get("latitude").toString());
            double lng = Double.parseDouble(value.get("longitude").toString());

            Log.i("locations : :",""+lat+"::"+lng);

            LatLng location = new LatLng(lat,lng);

            if(!markerHashMap.containsKey(key)){
                markerHashMap.put(key,mMap.addMarker(new MarkerOptions()
                        .title(key).position(location)));
            }else{

                markerHashMap.get(key).setPosition(location);
            }

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for(Marker marker: markerHashMap.values()){

                builder.include(marker.getPosition());

            }

            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),300));


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Display.this,Home.class);
        startActivity(intent);
        finish();
    }
}
