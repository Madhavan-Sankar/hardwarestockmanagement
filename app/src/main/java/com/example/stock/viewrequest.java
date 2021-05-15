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
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class viewrequest extends AppCompatActivity {
    String entryid,name;
    ProgressBar progressBar;
    ListView mListView;
    ArrayList<requestclass> products = new ArrayList<>();
    EditText search;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrequest);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor));
        }
        Bundle bundle = getIntent().getExtras();
        entryid = bundle.getString("entryid");
        name = bundle.getString("name");
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
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
                ArrayList<requestclass> filteredname = new ArrayList<>();
                for(requestclass i : products){
                    if (i.getId().toLowerCase().contains(searchArray.toLowerCase()) || i.getName().toLowerCase().contains(searchArray.toLowerCase())) {
                        if (!filteredname.contains(i)) {
                            filteredname.add(i);
                        }
                    }
                }
                myAdapter = new MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,filteredname,ipaddress){
                };
                mListView.setAdapter(myAdapter);
            }
        });
        populateListView();
    }

    private void populateListView (){
        progressBar.setVisibility(View.VISIBLE);
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ipaddress+"/requestread.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String remark=object.getString("remark");
                                String status=object.getString("status");
                                String dateandtime = object.getString("dateandtime");
                                requestclass product = new requestclass(id,name,remark,status,dateandtime);
                                if(id.equalsIgnoreCase(entryid))
                                {
                                    products.add(product);
                                }
                                else {

                                }
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
                Toast.makeText(viewrequest.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(viewrequest.this).add(stringRequest);

    }

    class MyAdapter extends ArrayAdapter<requestclass> {

        Context context;
        ArrayList<requestclass> rlast;
        String ipaddress;
        public MyAdapter(Context c, int resource,ArrayList<requestclass> last,String ipaddress) {
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
            View row = layoutInflater.inflate(R.layout.cardview4, parent, false);
            TextView id = row.findViewById(R.id.id);
            TextView name1 = row.findViewById(R.id.name);
            TextView remarks = row.findViewById(R.id.remarks);
            TextView status = row.findViewById(R.id.status);
            TextView dateandtime = row.findViewById(R.id.dateandtime);
            Button done = row.findViewById(R.id.done);
            Button cancel = row.findViewById(R.id.cancel);
            final requestclass product1= rlast.get(position);
            id.setText(product1.getId());
            name1.setText(product1.getName());
            remarks.setText(product1.getRemarks());
            status.setText(product1.getStatus());
            dateandtime.setText(product1.getDateandtime());
            done.setVisibility(View.INVISIBLE);
            cancel.setText("DELETE");
            cancel.setOnClickListener(new View.OnClickListener() {
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
                            PutData putData = new PutData(ipaddress+"/requestcancel.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Request Update Success")) {
                                        Toast.makeText(context, "" + result, LENGTH_SHORT).show();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("entryid", entryid);
                                        bundle.putString("name", name);
                                        Intent ii = new Intent(viewrequest.this, stafflogin.class);
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
            return row;
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString("entryid", entryid);
        bundle.putString("name", name);//admin name
        Intent ii = new Intent(viewrequest.this, stafflogin.class);
        ii.putExtras(bundle);
        startActivity(ii);
    }
}