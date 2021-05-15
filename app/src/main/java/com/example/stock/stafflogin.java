package com.example.stock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class stafflogin extends AppCompatActivity {

    String entryid,name;
    TextView staffid;
    Button service,viewallservice,syserq,allsysreq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stafflogin);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor));
        }
        Bundle bundle = getIntent().getExtras();
        entryid = bundle.getString("entryid");
        name = bundle.getString("name");
        staffid=findViewById(R.id.staffid);
        staffid.setText(name.toUpperCase());
        service=findViewById(R.id.service);
        syserq=findViewById(R.id.sysreq);
        viewallservice=findViewById(R.id.allservice);
        allsysreq=findViewById(R.id.allsysreq);
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("entryid", entryid);
                bundle1.putString("name",name);
                Intent ii =new Intent(stafflogin.this,serviceinsert.class);
                ii.putExtras(bundle1);
                startActivity(ii);
            }
        });
        syserq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("entryid", entryid);
                bundle1.putString("name",name);
                Intent ii =new Intent(stafflogin.this,requestinsert.class);
                ii.putExtras(bundle1);
                startActivity(ii);
            }
        });
        viewallservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("entryid", entryid);
                bundle1.putString("name",name);
                Intent ii =new Intent(stafflogin.this,serviceview.class);
                ii.putExtras(bundle1);
                startActivity(ii);
            }
        });
        allsysreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("entryid", entryid);
                bundle1.putString("name",name);
                Intent ii =new Intent(stafflogin.this,viewrequest.class);
                ii.putExtras(bundle1);
                startActivity(ii);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent ii = new Intent(stafflogin.this,MainActivity.class);
        startActivity(ii);
    }
}