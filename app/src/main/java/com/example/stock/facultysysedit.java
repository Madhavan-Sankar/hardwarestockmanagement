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

public class facultysysedit extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String id,name,monitor,keyboard,mouse,cpu;//predefined and set
    TextView id1,monitor1,keyboard1,mouse1,cpu1;
    EditText name1;
    MyAdapter myAdapter;
    ArrayList<product> products = new ArrayList<>();
    ArrayList<String> mon = new ArrayList<>();//monitor
    ArrayList<String> key = new ArrayList<>();//keyboard
    ArrayList<String> mo = new ArrayList<>();//mouse
    ArrayList<String> cp = new ArrayList<>();//cpu
    ListView mListViewmonitor,mListViewkeyboard,mListViewmouse,mListViewcpu;
    Button submit;
    ProgressBar progressBar;
    Spinner replacement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultysysedit);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor));
        }
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        progressBar=findViewById(R.id.progress);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        name = bundle.getString("name");
        monitor = bundle.getString("monitor");
        keyboard = bundle.getString("keyboard");
        mouse = bundle.getString("mouse");
        cpu = bundle.getString("cpu");
        id1=findViewById(R.id.id);
        name1=findViewById(R.id.name);
        monitor1=findViewById(R.id.monitor);
        keyboard1=findViewById(R.id.keyboard);
        mouse1=findViewById(R.id.mouse);
        cpu1=findViewById(R.id.cpu);
        replacement=findViewById(R.id.replacement);
        id1.setText(id);
        name1.setText(name);
        monitor1.setText(monitor);
        keyboard1.setText(keyboard);
        mouse1.setText(mouse);
        cpu1.setText(cpu);
        submit=findViewById(R.id.submit);
        mListViewmonitor=findViewById(R.id.listviewmonitor);
        mListViewkeyboard=findViewById(R.id.listviewkeyboard);
        mListViewmouse=findViewById(R.id.listviewmouse);
        mListViewcpu=findViewById(R.id.listviewcpu);
        monitor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListViewmonitor.setVisibility(View.VISIBLE);
                mListViewkeyboard.setVisibility(View.GONE);
                mListViewmouse.setVisibility(View.GONE);
                mListViewcpu.setVisibility(View.GONE);
            }
        });
        keyboard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListViewmonitor.setVisibility(View.GONE);
                mListViewkeyboard.setVisibility(View.VISIBLE);
                mListViewmouse.setVisibility(View.GONE);
                mListViewcpu.setVisibility(View.GONE);
            }
        });
        mouse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListViewmonitor.setVisibility(View.GONE);
                mListViewkeyboard.setVisibility(View.GONE);
                mListViewmouse.setVisibility(View.VISIBLE);
                mListViewcpu.setVisibility(View.GONE);
            }
        });
        cpu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListViewmonitor.setVisibility(View.GONE);
                mListViewkeyboard.setVisibility(View.GONE);
                mListViewmouse.setVisibility(View.GONE);
                mListViewcpu.setVisibility(View.VISIBLE);
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
        final List<String> replacementtype = Arrays.asList("Worn out","Replace");
        ArrayAdapter adapter = new ArrayAdapter(facultysysedit.this, R.layout.my_selected_item, replacementtype);
        adapter.setDropDownViewResource(R.layout.my_dropdown_item);
        replacement.setAdapter(adapter);
        replacement.setOnItemSelectedListener(facultysysedit.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replace = replacement.getSelectedItem().toString();
                final String id2,name2,monitor2,keyboard2,mouse2,cpu2;
                id2=id1.getText().toString();
                name2=name1.getText().toString();
                monitor2=monitor1.getText().toString();
                keyboard2=keyboard1.getText().toString();
                mouse2=mouse1.getText().toString();
                cpu2=cpu1.getText().toString();
                if(id2.equals(id) && name2.equals(name) && monitor2.equals(monitor) && keyboard2.equals(keyboard) && mouse2.equals(mouse) && cpu2.equals(cpu))
                {
                    Toast.makeText(facultysysedit.this, "No change in data!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(id2.equals("") || name2.equals("") || monitor2.equals("") || keyboard2.equals("") || mouse2.equals("") || cpu2.equals(""))
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
                                    String[] field = new String[10];
                                    field[0] = "faculty_id";
                                    field[1] = "faculty_name";
                                    field[2] = "monitor";
                                    field[3] = "keyboard";
                                    field[4] = "mouse";
                                    field[5] = "cpu";
                                    field[6] = "oldmonitor";
                                    field[7] = "oldkeyboard";
                                    field[8] = "oldmouse";
                                    field[9] = "oldcpu";
                                    //Creating array for data
                                    String[] data = new String[10];
                                    data[0] = id2;
                                    data[1] = name2;
                                    data[2] = monitor2;
                                    data[3] = keyboard2;
                                    data[4] = mouse2;
                                    data[5] = cpu2;
                                    data[6] = monitor;
                                    data[7] = keyboard;
                                    data[8] = mouse;
                                    data[9] = cpu;
                                    PutData putData = new PutData(ipaddress + "/facultysysedit.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            progressBar.setVisibility(View.GONE);
                                            String result = putData.getResult();
                                            if (result.equals("Faculty Details Update Success")) {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("entryid", "admin");
                                                bundle.putString("name", "admin");//admin name
                                                Intent ii = new Intent(facultysysedit.this, admin.class);
                                                ii.putExtras(bundle);
                                                startActivity(ii);
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
                                    String[] field = new String[10];
                                    field[0] = "faculty_id";
                                    field[1] = "faculty_name";
                                    field[2] = "monitor";
                                    field[3] = "keyboard";
                                    field[4] = "mouse";
                                    field[5] = "cpu";
                                    field[6] = "curmonitor";
                                    field[7] = "curkeyboard";
                                    field[8] = "curmouse";
                                    field[9] = "curcpu";
                                    //Creating array for data
                                    String[] data = new String[10];
                                    data[0] = id2;
                                    data[1] = name2;
                                    if(monitor2.equals(monitor)) {
                                        data[2] = " ";
                                        data[6] = monitor;
                                    }
                                    else {
                                        data[2] = monitor2;
                                        data[6] = monitor2;
                                    }
                                    if(keyboard2.equals(keyboard)) {
                                        data[3] = " ";
                                        data[7] = keyboard;
                                    }
                                    else {
                                        data[3] = keyboard2;
                                        data[7] = keyboard2;
                                    }
                                    if(mouse2.equals(mouse)) {
                                        data[4] = " ";
                                        data[8] = mouse;
                                    }
                                    else {
                                        data[4] = mouse2;
                                        data[8] = mouse2;
                                    }
                                    if(cpu2.equals(cpu)) {
                                        data[5] = " ";
                                        data[9] = cpu;
                                    }
                                    else {
                                        data[5] = cpu2;
                                        data[9] = cpu2;
                                    }
                                    PutData putData = new PutData(ipaddress + "/facultysyseditwo.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            progressBar.setVisibility(View.GONE);
                                            String result = putData.getResult();
                                            if (result.equals("Faculty Details Update Success")) {
                                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("entryid", "admin");
                                                bundle.putString("name", "admin");//admin name
                                                Intent ii = new Intent(facultysysedit.this, admin.class);
                                                ii.putExtras(bundle);
                                                startActivity(ii);
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
                Toast.makeText(facultysysedit.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });
        Volley.newRequestQueue(facultysysedit.this).add(stringRequest);

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

    public void onBackPressed() {
        Intent i = new Intent(facultysysedit.this,facultyview.class);
        startActivity(i);
    }
}