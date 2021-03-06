package com.example.stock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class signup extends AppCompatActivity {

    Button submit;
    EditText username1,name1,password1,department1,id1;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor));
        }
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        id1=findViewById(R.id.id);
        name1=findViewById(R.id.name);
        username1=findViewById(R.id.username);
        password1=findViewById(R.id.password);
        department1=findViewById(R.id.department);
        submit=findViewById(R.id.submit);

        progressBar = findViewById(R.id.progress);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name,password,email,department,id;
                id=id1.getText().toString();
                name=name1.getText().toString();
                email=username1.getText().toString();
                password=password1.getText().toString();
                department=department1.getText().toString();


                if(!name.equals("") && !password.equals("") && !email.equals("") && !department.equals("") && !id.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "id";
                            field[1] = "name";
                            field[2] = "password";
                            field[3] = "email";
                            field[4] = "department";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = id;
                            data[1] = name;
                            data[2] = password;
                            data[3] = email;
                            data[4] = department;
                            PutData putData = new PutData(ipaddress+"/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(signup.this,MainActivity.class);
        startActivity(i);
    }
}