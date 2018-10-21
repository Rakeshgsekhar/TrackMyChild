package com.example.devoprakesh.trackmychild;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private RecyclerView recyclerView;
    private AlertDialog.Builder alertDialogBuilder;
    private String childphnumber,unicodepassstr;
    private EditText childnumber,unicode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Intent intent = new Intent(Home.this,Display.class);
        startActivity(intent);
        finish();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemenu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.addchlid:
                AddChild();
                break;
            case R.id.logout:
                break;
            case R.id.profileview:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    void AddChild(){


        LayoutInflater inflater = LayoutInflater.from(Home.this);
        View promtsnumber = inflater.inflate(R.layout.promtsview,
                null);
        alertDialogBuilder = new AlertDialog.Builder(Home.this);

        alertDialogBuilder.setView(promtsnumber);

         childnumber = promtsnumber
                .findViewById(R.id.promptchildnumber);
         unicode = promtsnumber.findViewById(R.id.unicodepass);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface
                            , int i) {

                        childphnumber = childnumber.getText().toString();
                        unicodepassstr = unicode.getText().toString();

                        DatabaseReference databaseReference = FirebaseDatabase
                                .getInstance().getReference("Users");

                        databaseReference.child("").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                    }
                })
                .setNegativeButton("Cancel", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface
                            , int i) {
                        dialogInterface.cancel();
                    }
                });


    }
}
