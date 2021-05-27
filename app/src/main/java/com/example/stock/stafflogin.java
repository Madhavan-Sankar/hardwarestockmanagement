package com.example.stock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


public class stafflogin extends AppCompatActivity {

    String entryid,name;
    TextView staffid;
    LinearLayout expandableview1,expandableview2,expandableview3;
    TextView gservice,gsystem,geditacc;
    CardView cv00,cv01,cv20,alphaservicerequest,alphasystemrequest,alphaeditacc;
    Button service,viewallservice,syserq,allsysreq,editacc;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stafflogin);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor1));
        }
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        Bundle bundle = getIntent().getExtras();
        entryid = bundle.getString("entryid");
        name = bundle.getString("name");
        staffid=findViewById(R.id.staffid);
        staffid.setText(name.toUpperCase());
        service=findViewById(R.id.service);
        syserq=findViewById(R.id.sysreq);
        viewallservice=findViewById(R.id.allservice);
        allsysreq=findViewById(R.id.allsysreq);
        editacc=findViewById(R.id.editacc);
        gservice=findViewById(R.id.gservice);
        gsystem=findViewById(R.id.gsystem);
        geditacc=findViewById(R.id.geditacc);
        cv00=findViewById(R.id.cv00);
        cv01=findViewById(R.id.cv01);
        cv20=findViewById(R.id.cv20);
        alphaservicerequest=findViewById(R.id.alphaservicerequest);
        alphasystemrequest=findViewById(R.id.alphasystemrequest);
        alphaeditacc=findViewById(R.id.alphaeditacc);
        expandableview1=findViewById(R.id.expandableview1);
        expandableview2=findViewById(R.id.expandableview2);
        expandableview3=findViewById(R.id.expandableview3);
        progressBar=findViewById(R.id.progress);
        gservice.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(expandableview1.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(cv00, new AutoTransition());
                    expandableview1.setVisibility(View.VISIBLE);
                    alphaservicerequest.setAlpha(1);
                }
                else
                {
                    TransitionManager.beginDelayedTransition(cv00, new AutoTransition());
                    expandableview1.setVisibility(View.GONE);
                    alphaservicerequest.setAlpha(0);
                }
            }
        });
        gsystem.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(expandableview2.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(cv01, new AutoTransition());
                    expandableview2.setVisibility(View.VISIBLE);
                    alphasystemrequest.setAlpha(1);
                }
                else
                {
                    TransitionManager.beginDelayedTransition(cv01, new AutoTransition());
                    expandableview2.setVisibility(View.GONE);
                    alphasystemrequest.setAlpha(0);
                }
            }
        });
        geditacc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if(expandableview3.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(cv20, new AutoTransition());
                    expandableview3.setVisibility(View.VISIBLE);
                    alphaeditacc.setAlpha(1);
                }
                else
                {
                    TransitionManager.beginDelayedTransition(cv20, new AutoTransition());
                    expandableview3.setVisibility(View.GONE);
                    alphaeditacc.setAlpha(0);
                }
            }
        });
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
        editacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                StringRequest stringRequest = new StringRequest(Request.Method.GET,  ipaddress +"/facultydetailsread.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                progressBar.setVisibility(View.GONE);

                                try {

                                    JSONArray array = new JSONArray(response);
                                    for (int i = 0; i<array.length(); i++){

                                        JSONObject object = array.getJSONObject(i);
                                        String id = object.getString("faculty_id");
                                        String name = object.getString("faculty_name");
                                        String email = object.getString("email");
                                        String password = object.getString("password");
                                        String dept = object.getString("dept");
                                        if(id.equalsIgnoreCase(entryid))
                                        {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("id", entryid);
                                            bundle.putString("name", name);
                                            bundle.putString("email", email);
                                            bundle.putString("password", password);
                                            bundle.putString("dept", dept);
                                            bundle.putString("entryid", entryid);
                                            bundle.putString("name", name);
                                            Intent ii =new Intent(stafflogin.this,facultyedit.class);
                                            ii.putExtras(bundle);
                                            startActivity(ii);
                                        }
                                    }

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(stafflogin.this, error.toString(),Toast.LENGTH_LONG).show();

                    }
                });
                Volley.newRequestQueue(stafflogin.this).add(stringRequest);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent ii = new Intent(stafflogin.this,MainActivity.class);
        startActivity(ii);
    }
}