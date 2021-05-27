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
import android.widget.Spinner;
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
import java.util.Arrays;
import java.util.List;

public class labsysedit extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String id,lab,dept,monitor,keyboard,mouse,cpu;
    TextView id1,dept1,monitor1,keyboard1,mouse1,cpu1;
    EditText lab1;
    MyAdapter myAdapter;
    MyAdapter1 myAdapter1;
    ArrayList<product> products = new ArrayList<>();
    List<String> lablist;
    ArrayList<String> mon = new ArrayList<>();//monitor
    ArrayList<String> key = new ArrayList<>();//keyboard
    ArrayList<String> mo = new ArrayList<>();//mouse
    ArrayList<String> cp = new ArrayList<>();//cpu
    ListView mListViewdept,mListViewmonitor,mListViewkeyboard,mListViewmouse,mListViewcpu;
    Button submit;
    String entryid,name;
    ProgressBar progressBar;
    Spinner replacement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labsysedit);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor));
        }
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        lablist = ((Ipaddress) this.getApplication()).getDepartment();
        progressBar=findViewById(R.id.progress);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        lab = bundle.getString("lab");
        dept = bundle.getString("dept");
        monitor = bundle.getString("monitor");
        keyboard = bundle.getString("keyboard");
        mouse = bundle.getString("mouse");
        cpu = bundle.getString("cpu");
        entryid = bundle.getString("entryid");
        name = bundle.getString("name");
        id1=findViewById(R.id.id);
        lab1=findViewById(R.id.lab);
        dept1=findViewById(R.id.dept);
        monitor1=findViewById(R.id.monitor);
        keyboard1=findViewById(R.id.keyboard);
        mouse1=findViewById(R.id.mouse);
        cpu1=findViewById(R.id.cpu);
        replacement=findViewById(R.id.replacement);
        id1.setText(id);
        lab1.setText(lab);
        dept1.setText(dept);
        monitor1.setText(monitor);
        keyboard1.setText(keyboard);
        mouse1.setText(mouse);
        cpu1.setText(cpu);
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
        final List<String> replacementtype = Arrays.asList("Worn out","Replace");
        ArrayAdapter adapter = new ArrayAdapter(labsysedit.this, R.layout.my_selected_item, replacementtype);
        adapter.setDropDownViewResource(R.layout.my_dropdown_item);
        replacement.setAdapter(adapter);
        replacement.setOnItemSelectedListener(labsysedit.this);
        populateListView();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replace = replacement.getSelectedItem().toString();
                final String id2,lab2,dept2,monitor2,keyboard2,mouse2,cpu2;
                id2=id1.getText().toString();
                lab2=lab1.getText().toString();
                dept2=dept1.getText().toString();
                monitor2=monitor1.getText().toString();
                keyboard2=keyboard1.getText().toString();
                mouse2=mouse1.getText().toString();
                cpu2=cpu1.getText().toString();
                if(id2.equals(id) && lab2.equals(lab) && dept2.equals(dept) && monitor2.equals(monitor) && keyboard2.equals(keyboard) && mouse2.equals(mouse) && cpu2.equals(cpu))
                {
                    Toast.makeText(labsysedit.this, "No change in data!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(id2.equals("") || lab2.equals("") || dept2.equals("") || monitor2.equals("") || keyboard2.equals("") || mouse2.equals("") || cpu2.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "All fields are Required", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(replace.equalsIgnoreCase("replace"))
                        {
                            progressBar.setVisibility(View.VISIBLE);
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Starting Write and Read data with URL
                                    //Creating array for parameters
                                    String[] field = new String[11];
                                    field[0] = "system_id";
                                    field[1] = "lab";
                                    field[2] = "dept";
                                    field[3] = "monitor";
                                    field[4] = "keyboard";
                                    field[5] = "mouse";
                                    field[6] = "cpu";
                                    field[7] = "oldmonitor";
                                    field[8] = "oldkeyboard";
                                    field[9] = "oldmouse";
                                    field[10] = "oldcpu";
                                    //Creating array for data
                                    String[] data = new String[11];
                                    data[0] = id2;
                                    data[1] = lab2;
                                    data[2] = dept2;
                                    data[3] = monitor2;
                                    data[4] = keyboard2;
                                    data[5] = mouse2;
                                    data[6] = cpu2;
                                    data[7] = monitor;
                                    data[8] = keyboard;
                                    data[9] = mouse;
                                    data[10] = cpu;
                                    PutData putData = new PutData(ipaddress + "/labsysedit.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            progressBar.setVisibility(View.GONE);
                                            String result = putData.getResult();
                                            if (result.equals("Lab Details Update Success")) {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                if (entryid.equalsIgnoreCase("admin")) {
                                                    Bundle bundle1 = new Bundle();
                                                    bundle1.putString("entryid", entryid);
                                                    bundle1.putString("name", name);//admin name
                                                    Intent iii = new Intent(labsysedit.this, admin.class);
                                                    iii.putExtras(bundle1);
                                                    startActivity(iii);
                                                } else {
                                                    Bundle bundle2 = new Bundle();
                                                    bundle2.putString("entryid", entryid);
                                                    bundle2.putString("name", name);//admin name
                                                    Intent intent = new Intent(labsysedit.this, lab.class);
                                                    intent.putExtras(bundle2);
                                                    startActivity(intent);
                                                }
                                                //finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    //End Write and Read data with URL
                                }
                            });
                        }
                        else if(replace.equalsIgnoreCase("worn out"))
                        {
                            progressBar.setVisibility(View.VISIBLE);
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Starting Write and Read data with URL
                                    //Creating array for parameters
                                    String[] field = new String[11];
                                    field[0] = "system_id";
                                    field[1] = "lab";
                                    field[2] = "dept";
                                    field[3] = "monitor";
                                    field[4] = "keyboard";
                                    field[5] = "mouse";
                                    field[6] = "cpu";
                                    field[7] = "curmonitor";
                                    field[8] = "curkeyboard";
                                    field[9] = "curmouse";
                                    field[10] = "curcpu";
                                    //Creating array for data
                                    String[] data = new String[11];
                                    data[0] = id2;
                                    data[1] = lab2;
                                    data[2] = dept2;
                                    if(monitor2.equals(monitor)) {
                                        data[3] = " ";
                                        data[7] = monitor;
                                    }
                                    else {
                                        data[3] = monitor2;
                                        data[7] = monitor2;
                                    }
                                    if(keyboard2.equals(keyboard)) {
                                        data[4] = " ";
                                        data[8] = keyboard;
                                    }
                                    else {
                                        data[4] = keyboard2;
                                        data[8] = keyboard2;
                                    }
                                    if(mouse2.equals(mouse)) {
                                        data[5] = " ";
                                        data[9] = mouse;
                                    }
                                    else {
                                        data[5] = mouse2;
                                        data[9] = mouse2;
                                    }
                                    if(cpu2.equals(cpu)) {
                                        data[6] = " ";
                                        data[10] = cpu;
                                    }
                                    else {
                                        data[6] = cpu2;
                                        data[10] = cpu2;
                                    }
                                    PutData putData = new PutData(ipaddress + "/labsyseditwo.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            progressBar.setVisibility(View.GONE);
                                            String result = putData.getResult();
                                            if (result.equals("Lab Details Update Success")) {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                if (entryid.equalsIgnoreCase("admin")) {
                                                    Bundle bundle1 = new Bundle();
                                                    bundle1.putString("entryid", entryid);
                                                    bundle1.putString("name", name);//admin name
                                                    Intent iii = new Intent(labsysedit.this, admin.class);
                                                    iii.putExtras(bundle1);
                                                    startActivity(iii);
                                                } else {
                                                    Bundle bundle2 = new Bundle();
                                                    bundle2.putString("entryid", entryid);
                                                    bundle2.putString("name", name);//admin name
                                                    Intent intent = new Intent(labsysedit.this, lab.class);
                                                    intent.putExtras(bundle2);
                                                    startActivity(intent);
                                                }
                                                //finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    //End Write and Read data with URL
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    private void populateListView (){
        myAdapter1 = new MyAdapter1(getApplicationContext(),android.R.layout.simple_list_item_1, lablist);//for initialising departments
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
                                products.add(product);
                                if(product.getType().equalsIgnoreCase("monitor"))
                                    mon.add(name);
                                else if(product.getType().equalsIgnoreCase("keyboard"))
                                    key.add(name);
                                else if(product.getType().equalsIgnoreCase("mouse"))
                                    mo.add(name);
                                else if(product.getType().equalsIgnoreCase("cpu"))
                                    cp.add(name);
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
                Toast.makeText(labsysedit.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(labsysedit.this).add(stringRequest);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
        Bundle bundle = new Bundle();
        bundle.putString("entryid", entryid);
        bundle.putString("name", name);//admin name
        Intent ii = new Intent(labsysedit.this, labview.class);
        ii.putExtras(bundle);
        startActivity(ii);
    }
}