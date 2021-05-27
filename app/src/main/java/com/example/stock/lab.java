package com.example.stock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class lab extends AppCompatActivity {

    String entryid,name;
    Button labsystem,labadd,labservice,labserviceview;
    LinearLayout expandableview1,expandableview2;
    TextView gservice,gsystem;
    CardView cv00,cv01,alphaservice,alphasystem;
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
        labservice=findViewById(R.id.labservice);
        labserviceview=findViewById(R.id.labserviceview);
        gservice=findViewById(R.id.gservice);
        gsystem=findViewById(R.id.gsystem);
        cv00=findViewById(R.id.cv00);
        cv01=findViewById(R.id.cv01);
        alphaservice=findViewById(R.id.alphaservice);
        alphasystem=findViewById(R.id.alphasystem);
        expandableview1=findViewById(R.id.expandableview1);
        expandableview2=findViewById(R.id.expandableview2);
        gsystem.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(expandableview1.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(cv00, new AutoTransition());
                    expandableview1.setVisibility(View.VISIBLE);
                    alphasystem.setAlpha(1);
                }
                else
                {
                    TransitionManager.beginDelayedTransition(cv00, new AutoTransition());
                    expandableview1.setVisibility(View.GONE);
                    alphasystem.setAlpha(0);
                }
            }
        });
        gservice.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(expandableview2.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(cv01, new AutoTransition());
                    expandableview2.setVisibility(View.VISIBLE);
                    alphaservice.setAlpha(1);
                }
                else
                {
                    TransitionManager.beginDelayedTransition(cv01, new AutoTransition());
                    expandableview2.setVisibility(View.GONE);
                    alphaservice.setAlpha(0);
                }
            }
        });
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
        labservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(lab.this, labservice.class);
                startActivity(intent);
            }
        });
        labserviceview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("entryid", entryid);
                bundle1.putString("name", name);//lab admin name
                Intent intent1 = new Intent(lab.this, serviceview.class);
                intent1.putExtras(bundle1);
                startActivity(intent1);
            }
        });
    }
    public void onBackPressed() {
        Intent i = new Intent(lab.this,MainActivity.class);
        startActivity(i);
    }
}