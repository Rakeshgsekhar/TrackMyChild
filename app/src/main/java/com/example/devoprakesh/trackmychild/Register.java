package com.example.devoprakesh.trackmychild;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText regemail,regpass,regconpass;
    private String email,pass,conpass;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button submit;
        mAuth = FirebaseAuth.getInstance();

        //progressBar = findViewById(R.id.progressBar2);
        regemail = findViewById(R.id.regemail);
        regpass = findViewById(R.id.regpass);
        regconpass = findViewById(R.id.regconpass);
        submit = findViewById(R.id.submitbtn);

        sharedPreferences = getSharedPreferences("UserLoginDetails",MODE_PRIVATE);

        editor = sharedPreferences.edit();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = regemail.getText().toString();
                pass = regpass.getText().toString();
                conpass = regpass.getText().toString();

                MakeVisible();

                if(TextUtils.isEmpty(email)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(conpass)){

                    Toast.makeText(Register.this," Input fields cannot be empty",Toast.LENGTH_LONG).show();

                    MakeInVisible();
                }else{

                    if(pass.equals(conpass)){


                        mAuth.createUserWithEmailAndPassword(email,pass)
                                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if(task.isSuccessful()){
                                            String uid;
                                            Toast.makeText(Register.this," Registration Successful!!!",Toast.LENGTH_LONG).show();
                                            Log.i("Output Create User :->","Success");
                                            uid = mAuth.getUid();
                                            editor.putString("UID",uid);
                                            editor.putString("EmailID",email);
                                            editor.apply();
                                            Intent intent = new Intent(Register.this,Home.class);
                                            startActivity(intent);
                                            finish();


                                        }else{
                                            Toast.makeText(Register.this," Something went wrong , Try again Later",Toast.LENGTH_LONG).show();

                                            MakeVisible();
                                            Log.i("Output Create User :->","Error 400 : "+task.getResult()+"\n : "+task.getException());

                                        }
                                    }
                                });
                    }else{

                        Toast.makeText(Register.this," Password field does'nt match !!",Toast.LENGTH_LONG).show();
                        Log.i("OutputCredentialsUser:","Password mismatch");

                        MakeInVisible();
                    }
                }


            }
        });



    }

    private void MakeVisible(){

        regemail.setVisibility(View.INVISIBLE);
        regpass.setVisibility(View.INVISIBLE);
        regconpass.setVisibility(View.INVISIBLE);

       // progressBar.setVisibility(View.VISIBLE);

    }

    private void MakeInVisible(){
        regemail.setVisibility(View.VISIBLE);
        regpass.setVisibility(View.VISIBLE);
        regconpass.setVisibility(View.VISIBLE);

        //progressBar.setVisibility(View.INVISIBLE);
    }

}
