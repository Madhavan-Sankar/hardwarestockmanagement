package com.example.stock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class admin extends AppCompatActivity {
    String entryid,name;
    Button stock,view,add,service,systemrequest,facultysystem,facultyadd,facultydetails,labsystem,labadd;
    TextView staffid;
    LinearLayout expandableview1,expandableview2,expandableview3,expandableview4;
    TextView gstock,gfaculty,grequest,glab;
    CardView cv00,cv01,cv20,cv21,alphastock,alpharequest,alphafaculty,alphalab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor1));
        }
        Bundle bundle = getIntent().getExtras();
        entryid = bundle.getString("entryid");
        name = bundle.getString("name");

        expandableview1=findViewById(R.id.expandableview1);
        expandableview2=findViewById(R.id.expandableview2);
        expandableview3=findViewById(R.id.expandableview3);
        expandableview4=findViewById(R.id.expandableview4);
        gstock=findViewById(R.id.gstock);
        gfaculty=findViewById(R.id.gfaculty);
        grequest=findViewById(R.id.grequest);
        glab=findViewById(R.id.glab);
        cv00=findViewById(R.id.cv00);
        cv01=findViewById(R.id.cv01);
        cv20=findViewById(R.id.cv20);
        cv21=findViewById(R.id.cv21);
        alphastock=findViewById(R.id.alphastock);
        alpharequest=findViewById(R.id.alpharequest);
        alphafaculty=findViewById(R.id.alphafaculty);
        alphalab=findViewById(R.id.alphalab);
        gstock.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(expandableview1.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(cv00, new AutoTransition());
                    expandableview1.setVisibility(View.VISIBLE);
                    //alpharequest.setAlpha(0);
                    alphastock.setAlpha(1);
                }
                else
                {
                    TransitionManager.beginDelayedTransition(cv00, new AutoTransition());
                    expandableview1.setVisibility(View.GONE);
                    //expandableview2.setVisibility(View.GONE);
                    //alpharequest.setAlpha(0);
                    alphastock.setAlpha(0);
                }
            }
        });
        grequest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(expandableview2.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(cv01, new AutoTransition());
                    expandableview2.setVisibility(View.VISIBLE);
                    //alphastock.setAlpha(0);
                    alpharequest.setAlpha(1);
                }
                else
                {
                    TransitionManager.beginDelayedTransition(cv01, new AutoTransition());
                    expandableview2.setVisibility(View.GONE);
                    //expandableview1.setVisibility(View.GONE);
                    //alphastock.setAlpha(0);
                    alpharequest.setAlpha(0);
                }
            }
        });
        gfaculty.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(expandableview3.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(cv20, new AutoTransition());
                    expandableview3.setVisibility(View.VISIBLE);
                    //alphalab.setAlpha(0);
                    alphafaculty.setAlpha(1);
                }
                else
                {
                    TransitionManager.beginDelayedTransition(cv20, new AutoTransition());
                    expandableview3.setVisibility(View.GONE);
                    //expandableview4.setVisibility(View.GONE);
                    //alphalab.setAlpha(0);
                    alphafaculty.setAlpha(0);
                }
            }
        });
        glab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(expandableview4.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(cv21, new AutoTransition());
                    expandableview4.setVisibility(View.VISIBLE);
                    //alphafaculty.setAlpha(0);
                    alphalab.setAlpha(1);
                }
                else
                {
                    TransitionManager.beginDelayedTransition(cv21, new AutoTransition());
                    expandableview4.setVisibility(View.GONE);
                    //expandableview3.setVisibility(View.GONE);
                    //alphafaculty.setAlpha(0);
                    alphalab.setAlpha(0);
                }
            }
        });

        staffid=findViewById(R.id.staffid);
        stock=findViewById(R.id.stock);
        view=findViewById(R.id.view);
        add=findViewById(R.id.add);
        service=findViewById(R.id.service);
        systemrequest=findViewById(R.id.sysreq);
        facultysystem=findViewById(R.id.facultysystem);
        facultyadd = findViewById(R.id.facultyadd);
        facultydetails=findViewById(R.id.facultydetails);
        labadd = findViewById(R.id.labadd);
        labsystem=findViewById(R.id.labsystem);
        staffid.setText(name);
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(admin.this,stockentry.class);
                startActivity(i);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(admin.this,stockview.class);
                startActivity(ii);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iii = new Intent(admin.this,stockadd.class);
                startActivity(iii);
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(admin.this, service.class);
                startActivity(ii);
            }
        });
        systemrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(admin.this,sysreqview.class);
                startActivity(intent2);
            }
        });
        facultyadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(admin.this,facultysysadd.class);
                startActivity(intent1);
            }
        });
        facultysystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin.this,facultyview.class);
                startActivity(intent);
            }
        });
        facultydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(admin.this,facultydetails.class);
                startActivity(intent1);
            }
        });
        labadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("entryid", entryid);
                bundle.putString("name", name);//admin name
                Intent ii = new Intent(admin.this, labsysadd.class);
                ii.putExtras(bundle);
                startActivity(ii);
            }
        });
        labsystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("entryid", entryid);
                bundle.putString("name", name);//admin name
                Intent iii = new Intent(admin.this, labview.class);
                iii.putExtras(bundle);
                startActivity(iii);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(admin.this,MainActivity.class);
        startActivity(i);
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> rlast;
        public MyAdapter(Context c, int resource,ArrayList<String> last) {
            super(c, resource,last);
            this.context = c;
            rlast=last;
        }
        @SuppressLint("WrongViewCast")
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.admincardview, parent, false);
            TextView name = row.findViewById(R.id.name);
            name.setText(rlast.get(position));
            return row;
        }
    }

}