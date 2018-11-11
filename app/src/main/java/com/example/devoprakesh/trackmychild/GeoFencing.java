package com.example.devoprakesh.trackmychild;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class GeoFencing extends AppCompatActivity {

    Switch enabler;
    boolean isEnabled;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_fencing);


        enabler = findViewById(R.id.fencingenabler);
        isEnabled = getPreferences(MODE_PRIVATE).getBoolean(getString(R.string.setting_enabled),false);
        enabler.setChecked(isEnabled);
        enabler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });



    }
}
