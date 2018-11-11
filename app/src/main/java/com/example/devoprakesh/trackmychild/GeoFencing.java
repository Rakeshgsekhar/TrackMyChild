package com.example.devoprakesh.trackmychild;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;

public class GeoFencing extends AppCompatActivity {

    Switch enabler;
    boolean isEnabled;
    Button addnewbtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final int PLACE_PICKER_REQUEST = 1;
    List<Place> geofences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_fencing);

       geofences = new ArrayList<Place>();
        sharedPreferences = getSharedPreferences("UserLoginDetails",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        addnewbtn = findViewById(R.id.addgeofence);
        enabler = findViewById(R.id.fencingenabler);
        isEnabled = getPreferences(MODE_PRIVATE).getBoolean(getString(R.string.setting_enabled),false);
        enabler.setChecked(isEnabled);
        enabler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                editor.putBoolean(getString(R.string.setting_enabled),b);
                isEnabled = b;
                editor.commit();

            }
        });

        addnewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    Intent i = builder.build(GeoFencing.this);
                    startActivityForResult(i,PLACE_PICKER_REQUEST);
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
            Place place = PlacePicker.getPlace(this,data);

            if(place == null){
                Log.i("DATA","No Place Selected");
                return;
            }

            String placeId = place.getId();
            geofences.add(place);
        }
    }
}
