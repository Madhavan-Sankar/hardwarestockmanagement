package com.example.stock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class labview extends AppCompatActivity {

    ProgressBar progressBar;
    ListView mListView;
    ArrayList<labclass> products = new ArrayList<>();
    EditText search;
    String entryid,name;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labview);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor));
        }
        Bundle bundle = getIntent().getExtras();
        entryid = bundle.getString("entryid");
        name = bundle.getString("name");
        progressBar=findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        mListView=findViewById(R.id.listview);
        search=findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchArray = s.toString();
                ArrayList<labclass> filteredname = new ArrayList<>();
                for(labclass i : products){
                    if (i.getId().toLowerCase().contains(searchArray.toLowerCase()) || i.getLab().toLowerCase().contains(searchArray.toLowerCase())) {
                        if (!filteredname.contains(i)) {
                            filteredname.add(i);
                        }
                    }
                }
                myAdapter = new MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,filteredname){
                };
                mListView.setAdapter(myAdapter);
            }
        });
        populateListView();
    }

    private void populateListView (){
        progressBar.setVisibility(View.VISIBLE);
        String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ipaddress+"/labread.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                String id = object.getString("system_id");
                                String lab = object.getString("lab");
                                String monitor = object.getString("monitor");
                                String keyboard = object.getString("keyboard");
                                String mouse = object.getString("mouse");
                                String cpu = object.getString("cpu");
                                labclass product = new labclass(id,lab,monitor,keyboard,mouse,cpu);
                                products.add(product);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        myAdapter = new MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,products);
                        mListView.setAdapter(myAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(labview.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(labview.this).add(stringRequest);

    }

    class MyAdapter extends ArrayAdapter<labclass> {

        Context context;
        ArrayList<labclass> rlast;
        public MyAdapter(Context c, int resource,ArrayList<labclass> last) {
            super(c,resource,last);
            this.context = c;
            rlast=last;
        }
        @SuppressLint("WrongViewCast")
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.cardview3, parent, false);
            TextView laborname=row.findViewById(R.id.laborname);
            TextView id = row.findViewById(R.id.id);
            TextView name1 = row.findViewById(R.id.name);
            TextView monitor = row.findViewById(R.id.monitor);
            TextView keyboard = row.findViewById(R.id.keyboard);
            TextView mouse = row.findViewById(R.id.mouse);
            TextView cpu = row.findViewById(R.id.cpu);
            Button edit = row.findViewById(R.id.edit);
            final labclass product1= rlast.get(position);
            // now set our resources on views
            laborname.setText("LAB NAME");
            id.setText(product1.getId());
            name1.setText(product1.getLab());
            monitor.setText(product1.getMonitor());
            keyboard.setText(product1.getKeyboard());
            mouse.setText(product1.getMouse());
            cpu.setText(product1.getCpu());
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", product1.getId());
                    bundle.putString("lab", product1.getLab());
                    bundle.putString("monitor", product1.getMonitor());
                    bundle.putString("keyboard", product1.getKeyboard());
                    bundle.putString("mouse", product1.getMouse());
                    bundle.putString("cpu", product1.getCpu());
                    bundle.putString("entryid",entryid);
                    bundle.putString("name",name);
                    Intent ii = new Intent(labview.this, labsysedit.class);
                    ii.putExtras(bundle);
                    startActivity(ii);
                }
            });
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
            Intent ii = new Intent(labview.this, admin.class);
            ii.putExtras(bundle);
            startActivity(ii);
        }
        else
        {
            Bundle bundle = new Bundle();
            bundle.putString("entryid", "lab");
            bundle.putString("name", "lab");//lab name
            Intent ii = new Intent(labview.this, lab.class);
            ii.putExtras(bundle);
            startActivity(ii);
        }
    }
}