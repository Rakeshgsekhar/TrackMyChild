package com.example.devoprakesh.trackmychild;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.places.Place;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrackSer extends Service {


    List<UserData> childrens;
    Gson gson;
    List<Place>geofences;
    SharedPreferences sharedPreferences;
    String child,fence;

    public TrackSer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences("UserLoginDetails",MODE_PRIVATE);
        child = sharedPreferences.getString("childlist","null");
        fence = sharedPreferences.getString("fencelist","null");



        if(!child.equals("null") && !fence.equals("null")){

            Type type = new TypeToken<ArrayList<UserData>>() {}.getType();
            Type type1 = new TypeToken<ArrayList<Place>>(){}.getType();


            childrens = gson.fromJson(child,type);

            geofences = gson.fromJson(fence,type1);



            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Childrens");

            for(UserData user : childrens){
                ref.child(user.getPhonenumber()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        Checkfence(dataSnapshot);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                        Checkfence(dataSnapshot);
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        }else{

        }
    }

    void Checkfence(DataSnapshot dataSnapshot){

        for(Place p : geofences){

            String key = dataSnapshot.getKey();
            Log.i("data:",key);
            Log.i("data:",dataSnapshot.getValue().toString());
            if(key.equals("Current")) {

                HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                //value.put;
                //dataSnapshot.get
                double lat = Double.parseDouble(value.get("latitude").toString());
                double lng = Double.parseDouble(value.get("longitude").toString());

                double mrklat = p.getLatLng().latitude;
                double mrklong = p.getLatLng().longitude;


                float []results = new float[10];
                Location.distanceBetween(mrklat,mrklong,lat,lng,results);

                Log.i("Result Distance",""+results[0]);

                if(results[0]<50 && results[0]>(-50)){
                    Log.i("Fenced","In Fenced Location");
                }
            }

        }
    }
}
