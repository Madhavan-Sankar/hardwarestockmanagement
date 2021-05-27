package com.example.stock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class service extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ProgressBar progressBar;
    ListView mListView;
    ArrayList<serviceclass> products = new ArrayList<>();
    ArrayList<serviceclass> filterbutton = new ArrayList<>();
    EditText search;
    MyAdapter myAdapter;
    ImageView filter;
    LinearLayout filter1;
    Button apply;
    Spinner dpspinner;
    int is_clicked=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor));
        }
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        progressBar=findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        mListView=findViewById(R.id.listview);
        search=findViewById(R.id.search);
        apply=findViewById(R.id.apply);
        filter=findViewById(R.id.filter);
        filter1=findViewById(R.id.filter1);
        dpspinner=findViewById(R.id.dpspinner);
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
                ArrayList<serviceclass> filteredname = new ArrayList<>();
                if(is_clicked==0) {
                    for (serviceclass i : products) {
                        if (i.getId().toLowerCase().contains(searchArray.toLowerCase()) || i.getName().toLowerCase().contains(searchArray.toLowerCase())) {
                            if (!filteredname.contains(i)) {
                                filteredname.add(i);
                            }
                        }
                    }
                }
                else
                {
                    for (serviceclass i : filterbutton) {
                        if (i.getId().toLowerCase().contains(searchArray.toLowerCase()) || i.getName().toLowerCase().contains(searchArray.toLowerCase())) {
                            if (!filteredname.contains(i)) {
                                filteredname.add(i);
                            }
                        }
                    }
                }
                myAdapter = new MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,filteredname,ipaddress){
                };
                mListView.setAdapter(myAdapter);
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filter1.getVisibility()==View.GONE)
                {
                    filter1.setVisibility(View.VISIBLE);
                    apply.setVisibility(View.VISIBLE);
                }
                else
                {
                    filter1.setVisibility(View.GONE);
                    apply.setVisibility(View.GONE);
                }
            }
        });
        List<String> status = Arrays.asList("ALL","Done","Pending","Rejected");
        @SuppressLint("ResourceType")
        ArrayAdapter adapter = new ArrayAdapter(service.this, R.layout.my_selected_item, status);
        adapter.setDropDownViewResource(R.layout.my_dropdown_item);
        dpspinner.setAdapter(adapter);
        dpspinner.setOnItemSelectedListener(service.this);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = dpspinner.getSelectedItem().toString();
                search.setText("");
                filterbutton.clear();
                is_clicked=1;
                if(!s.equalsIgnoreCase("ALL")) {
                    for (serviceclass i : products) {
                        if(i.getStatus().equalsIgnoreCase(s))
                        {
                            filterbutton.add(i);
                        }
                    }
                    myAdapter = new MyAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, filterbutton,ipaddress) {
                    };
                    mListView.setAdapter(myAdapter);
                }
                else
                {
                    is_clicked=0;
                    myAdapter = new MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,products,ipaddress);
                    mListView.setAdapter(myAdapter);
                }
                filter1.setVisibility(View.GONE);
                apply.setVisibility(View.GONE);
                Toast.makeText(service.this, "Filter Applied Successfully!!", Toast.LENGTH_SHORT).show();
            }
        });
        populateListView();
    }

    private void populateListView (){
        progressBar.setVisibility(View.VISIBLE);
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ipaddress+"/serviceread.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                String id = object.getString("id");
                                String type = object.getString("type");
                                String name = object.getString("name");
                                String remark=object.getString("remark");
                                String reason=object.getString("reason");
                                String status=object.getString("status");
                                String dateandtime = object.getString("dateandtime");
                                serviceclass product = new serviceclass(id,type,name,remark,reason,status,dateandtime);
                                products.add(product);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        myAdapter = new MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,products,ipaddress);
                        mListView.setAdapter(myAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(service.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(service.this).add(stringRequest);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    class MyAdapter extends ArrayAdapter<serviceclass> {

        Context context;
        ArrayList<serviceclass> rlast;
        String ipaddress;
        public MyAdapter(Context c, int resource,ArrayList<serviceclass> last,String ipaddress) {
            super(c,resource,last);
            this.context = c;
            rlast=last;
            this.ipaddress=ipaddress;
        }
        @SuppressLint("WrongViewCast")
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.cardview2, parent, false);
            TextView id = row.findViewById(R.id.id);
            TextView type = row.findViewById(R.id.type);
            TextView name = row.findViewById(R.id.name);
            TextView remarks = row.findViewById(R.id.remarks);
            TextView reason = row.findViewById(R.id.reason);
            TextView status = row.findViewById(R.id.status);
            TextView dateandtime = row.findViewById(R.id.dateandtime);
            Button done = row.findViewById(R.id.done);
            Button cancel = row.findViewById(R.id.cancel);
            final serviceclass product1= rlast.get(position);
            // now set our resources on views
            id.setText(product1.getId());
            type.setText(product1.getType());
            name.setText(product1.getName());
            remarks.setText(product1.getRemarks());
            reason.setText(product1.getReason());
            status.setText(product1.getStatus());
            dateandtime.setText(product1.getDateandtime());
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "id";
                            field[1] = "dateandtime";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = product1.getId();
                            data[1] = product1.getDateandtime();
                            PutData putData = new PutData(ipaddress+"/serviceupdate.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Service Update Success")) {
                                        Toast.makeText(context, "" + result, LENGTH_SHORT).show();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("entryid", "admin");
                                        bundle.putString("name", "admin");//admin name
                                        Intent ii = new Intent(service.this, admin.class);
                                        ii.putExtras(bundle);
                                        startActivity(ii);
                                    }
                                    else
                                        Toast.makeText(context, "" + result, LENGTH_SHORT).show();
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(service.this);
                    alertDialog2.setTitle("Enter reason...");
                    final EditText reas = new EditText(service.this);
                    reas.setInputType(InputType.TYPE_CLASS_TEXT);
                    alertDialog2.setView(reas);
                    alertDialog2.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    Handler handler = new Handler(Looper.getMainLooper());
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //Starting Write and Read data with URL
                                            //Creating array for parameters
                                            String[] field = new String[3];
                                            field[0] = "id";
                                            field[1] = "reason";
                                            field[2] = "dateandtime";
                                            //Creating array for data
                                            String[] data = new String[3];
                                            data[0] = product1.getId();
                                            data[1] = reas.getText().toString();
                                            data[2] = product1.getDateandtime();
                                            PutData putData = new PutData(ipaddress+"/servicecancel.php", "POST", field, data);
                                            if (putData.startPut()) {
                                                if (putData.onComplete()) {
                                                    progressBar.setVisibility(View.GONE);
                                                    String result = putData.getResult();
                                                    if(result.equals("Service Update Success")) {
                                                        Toast.makeText(context, "" + result, LENGTH_SHORT).show();
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("entryid", "admin");
                                                        bundle.putString("name", "admin");//admin name
                                                        Intent ii = new Intent(service.this, admin.class);
                                                        ii.putExtras(bundle);
                                                        startActivity(ii);
                                                    }
                                                    else
                                                        Toast.makeText(context, "" + result, LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });
                                }
                            });
                    alertDialog2.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }});
                    alertDialog2.show();
                }
            });
            return row;
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString("entryid", "admin");
        bundle.putString("name", "admin");//admin name
        Intent ii = new Intent(service.this, admin.class);
        ii.putExtras(bundle);
        startActivity(ii);
    }
}