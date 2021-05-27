package com.example.stock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class labsysadd extends AppCompatActivity {

    TextView id1,dept1,monitor1,keyboard1,mouse1,cpu1;
    EditText lab1;
    MyAdapter myAdapter;
    MyAdapter1 myAdapter1;
    ArrayList<product> products = new ArrayList<>();
    ArrayList<String> mon = new ArrayList<>();//monitor
    ArrayList<String> key = new ArrayList<>();//keyboard
    ArrayList<String> mo = new ArrayList<>();//mouse
    ArrayList<String> cp = new ArrayList<>();//cpu
    List<String> lab;
    ListView mListViewmonitor,mListViewkeyboard,mListViewmouse,mListViewcpu,mListViewdept;
    Button submit;
    String entryid,name;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labsysad);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor));
        }
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        lab = ((Ipaddress) this.getApplication()).getDepartment();
        Bundle bundle = getIntent().getExtras();
        entryid = bundle.getString("entryid");
        name = bundle.getString("name");
        progressBar=findViewById(R.id.progress);
        id1=findViewById(R.id.id);
        lab1=findViewById(R.id.lab);
        dept1=findViewById(R.id.dept);
        monitor1=findViewById(R.id.monitor);
        keyboard1=findViewById(R.id.keyboard);
        mouse1=findViewById(R.id.mouse);
        cpu1=findViewById(R.id.cpu);
        submit=findViewById(R.id.submit);
        mListViewdept=findViewById(R.id.listviewdept);
        mListViewmonitor=findViewById(R.id.listviewmonitor);
        mListViewkeyboard=findViewById(R.id.listviewkeyboard);
        mListViewmouse=findViewById(R.id.listviewmouse);
        mListViewcpu=findViewById(R.id.listviewcpu);
        dept1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListViewdept.setVisibility(View.VISIBLE);
                mListViewmonitor.setVisibility(View.GONE);
                mListViewkeyboard.setVisibility(View.GONE);
                mListViewmouse.setVisibility(View.GONE);
                mListViewcpu.setVisibility(View.GONE);
            }
        });
        monitor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListViewdept.setVisibility(View.GONE);
                mListViewmonitor.setVisibility(View.VISIBLE);
                mListViewkeyboard.setVisibility(View.GONE);
                mListViewmouse.setVisibility(View.GONE);
                mListViewcpu.setVisibility(View.GONE);
            }
        });
        keyboard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListViewdept.setVisibility(View.GONE);
                mListViewmonitor.setVisibility(View.GONE);
                mListViewkeyboard.setVisibility(View.VISIBLE);
                mListViewmouse.setVisibility(View.GONE);
                mListViewcpu.setVisibility(View.GONE);
            }
        });
        mouse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListViewdept.setVisibility(View.GONE);
                mListViewmonitor.setVisibility(View.GONE);
                mListViewkeyboard.setVisibility(View.GONE);
                mListViewmouse.setVisibility(View.VISIBLE);
                mListViewcpu.setVisibility(View.GONE);
            }
        });
        cpu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListViewdept.setVisibility(View.GONE);
                mListViewmonitor.setVisibility(View.GONE);
                mListViewkeyboard.setVisibility(View.GONE);
                mListViewmouse.setVisibility(View.GONE);
                mListViewcpu.setVisibility(View.VISIBLE);
            }
        });
        mListViewdept.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dept1.setText(adapterView.getItemAtPosition(i).toString());
                mListViewdept.setVisibility(View.GONE);
            }
        });
        mListViewmonitor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                monitor1.setText(adapterView.getItemAtPosition(i).toString());
                mListViewmonitor.setVisibility(View.GONE);
            }
        });
        mListViewkeyboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                keyboard1.setText(adapterView.getItemAtPosition(i).toString());
                mListViewkeyboard.setVisibility(View.GONE);
            }
        });
        mListViewmouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mouse1.setText(adapterView.getItemAtPosition(i).toString());
                mListViewmouse.setVisibility(View.GONE);
            }
        });
        mListViewcpu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cpu1.setText(adapterView.getItemAtPosition(i).toString());
                mListViewcpu.setVisibility(View.GONE);
            }
        });
        populateListView();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id,lab,dept,monitor,keyboard,mouse,cpu;
                id=id1.getText().toString();
                lab=lab1.getText().toString();
                dept=dept1.getText().toString();
                monitor=monitor1.getText().toString();
                keyboard=keyboard1.getText().toString();
                mouse=mouse1.getText().toString();
                cpu=cpu1.getText().toString();
                if(id.equals("") || lab.equals("") || dept.equals("") || monitor.equals("") || keyboard.equals("") || mouse.equals("") || cpu.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "All fields are Required", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[7];
                            field[0] = "system_id";
                            field[1] = "lab";
                            field[2] = "dept";
                            field[3] = "monitor";
                            field[4] = "keyboard";
                            field[5] = "mouse";
                            field[6] = "cpu";
                            //Creating array for data
                            String[] data = new String[7];
                            data[0] = id;
                            data[1] = lab;
                            data[2] = dept;
                            data[3] = monitor;
                            data[4] = keyboard;
                            data[5] = mouse;
                            data[6] = cpu;
                            PutData putData = new PutData(ipaddress+"/labsysadd.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Lab Details Update Success")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        if(entryid.equalsIgnoreCase("admin"))
                                        {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("entryid", entryid);
                                            bundle.putString("name", name);//admin name
                                            Intent ii = new Intent(labsysadd.this, admin.class);
                                            ii.putExtras(bundle);
                                            startActivity(ii);
                                        }
                                        else
                                        {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("entryid", entryid);
                                            bundle.putString("name", name);//admin name
                                            Intent ii = new Intent(labsysadd.this, lab.class);
                                            ii.putExtras(bundle);
                                            startActivity(ii);
                                        }
                                        //finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),result+"\nSystem ID already assigned",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
            }
        });
    }

    private void populateListView (){
        myAdapter1 = new MyAdapter1(getApplicationContext(),android.R.layout.simple_list_item_1, lab);//for initialising departments
        mListViewdept.setAdapter(myAdapter1);
        progressBar.setVisibility(View.VISIBLE);
        String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ipaddress+"/stockread.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);
                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String type = object.getString("type");
                                String name = object.getString("name");
                                int quantity = object.getInt("quantity");
                                product product = new product(type,name,quantity);
                                if(quantity!=0) {
                                    products.add(product);
                                    if (product.getType().equalsIgnoreCase("monitor"))
                                        mon.add(name);
                                    else if (product.getType().equalsIgnoreCase("keyboard"))
                                        key.add(name);
                                    else if (product.getType().equalsIgnoreCase("mouse"))
                                        mo.add(name);
                                    else if (product.getType().equalsIgnoreCase("cpu"))
                                        cp.add(name);
                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        /*ListAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, initial);
                        mListView.setAdapter(adapter);*/
                        myAdapter = new MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,mon);
                        mListViewmonitor.setAdapter(myAdapter);
                        myAdapter = new MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,key);
                        mListViewkeyboard.setAdapter(myAdapter);
                        myAdapter = new MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,mo);
                        mListViewmouse.setAdapter(myAdapter);
                        myAdapter = new MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,cp);
                        mListViewcpu.setAdapter(myAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(labsysadd.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(labsysadd.this).add(stringRequest);

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
            View row = layoutInflater.inflate(R.layout.cardview1, parent, false);
            TextView type = row.findViewById(R.id.type);
            // now set our resources on views
            final String insert = rlast.get(position);
            type.setText(insert);
            return row;
        }
    }

    class MyAdapter1 extends ArrayAdapter<String> {   //only for lab data insertion

        Context context;
        List<String> rlast;
        public MyAdapter1(Context c, int resource,List<String> last) {
            super(c, resource,last);
            this.context = c;
            rlast=last;
        }
        @SuppressLint("WrongViewCast")
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.cardview1, parent, false);
            TextView type = row.findViewById(R.id.type);
            // now set our resources on views
            final String insert = rlast.get(position);
            type.setText(insert);
            return row;
        }
    }

    @Override
    public void onBackPressed() {
        if(entryid.equalsIgnoreCase("admin"))
        {
            Bundle bundle = new Bundle();
            bundle.putString("entryid", "admin");
            bundle.putString("name", "admin");//admin name
            Intent ii = new Intent(labsysadd.this, admin.class);
            ii.putExtras(bundle);
            startActivity(ii);
        }
        else
        {
            Bundle bundle = new Bundle();
            bundle.putString("entryid", "lab");
            bundle.putString("name", "lab");//lab name
            Intent ii = new Intent(labsysadd.this, lab.class);
            ii.putExtras(bundle);
            startActivity(ii);
        }
    }
}