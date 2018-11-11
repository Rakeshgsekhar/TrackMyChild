package com.example.devoprakesh.trackmychild;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GeoFencing extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    Switch enabler;
    boolean isEnabled;
    Button addnewbtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    GoogleApiClient client;
    private static final int PLACE_PICKER_REQUEST = 1;
   public static List<Place> geofences;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_fencing);

        gson = new Gson();
        geofences = new ArrayList<Place>();
        sharedPreferences = getSharedPreferences("UserLoginDetails",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        addnewbtn = findViewById(R.id.addgeofence);
        enabler = findViewById(R.id.fencingenabler);
        isEnabled = sharedPreferences.getBoolean("Enabler",false);
        enabler.setChecked(isEnabled);
        enabler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                editor.putBoolean("Enabler",b);
                isEnabled = b;
                editor.apply();
                editor.commit();

            }
        });

        /*client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this,this)
                .build();*/

        addnewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    Intent intent = builder.build(GeoFencing.this);
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PLACE_PICKER_REQUEST && resultCode==RESULT_OK){
            Place place = PlacePicker.getPlace(GeoFencing.this,data);

            if(place == null){
                Log.i("DATA","No Place Selected");
                return;
            }

            String placeId = place.getId();
            geofences.add(place);
            String json = gson.toJson(geofences);

            Toast.makeText(GeoFencing.this,""+json,Toast.LENGTH_LONG).show();
            editor.putString("fencelist",json);
            editor.apply();
            //editor.commit();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("API","Coonected");
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.i("API","Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("API","Failed");

    }
}
