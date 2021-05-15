package com.example.stock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class lab extends AppCompatActivity {

    String entryid,name;
    Button labsystem,labadd;
    TextView staffid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor1));
        }
        Bundle bundle = getIntent().getExtras();
        entryid = bundle.getString("entryid");//lab admin id
        name = bundle.getString("name");//lab admin name
        staffid=findViewById(R.id.staffid);
        labadd = findViewById(R.id.labadd);
        labsystem=findViewById(R.id.labsystem);
        staffid.setText(name);
        labadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("entryid", entryid);
                bundle.putString("name", name);//lab admin name
                Intent ii = new Intent(lab.this, labsysadd.class);
                ii.putExtras(bundle);
                startActivity(ii);
            }
        });
        labsystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("entryid", entryid);
                bundle.putString("name", name);//lab admin name
                Intent iii = new Intent(lab.this, labview.class);
                iii.putExtras(bundle);
                startActivity(iii);
            }
        });
    }
    public void onBackPressed() {
        Intent i = new Intent(lab.this,MainActivity.class);
        startActivity(i);
    }
}