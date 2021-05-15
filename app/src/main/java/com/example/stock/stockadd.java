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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
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

public class stockadd extends AppCompatActivity {
    ProgressBar progressBar;
    ListView mListView;
    ArrayList<String> initial = new ArrayList<String>();
    ArrayList<product> products = new ArrayList<>();
    EditText search,quantity;
    TextView name;
    Button add;
    int count=0; //no of clicks inside the listviwe
    MyAdapter1 myAdapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockadd);
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
        quantity=findViewById(R.id.quantity);
        name=findViewById(R.id.name);
        add=findViewById(R.id.add);
        search=findViewById(R.id.search);
        /*search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchArray = s.toString();
                ArrayList<product> filteredname = new ArrayList<>();
                for(product i : products){
                    if (i.getName().toLowerCase().contains(searchArray.toLowerCase()) || i.getType().toLowerCase().contains((searchArray.toLowerCase()))) {
                        if (!filteredname.contains(i)) {
                            filteredname.add(i);
                        }
                    }
                }
                myAdapter = new stockview.MyAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,filteredname){
                };
                mListView.setAdapter(myAdapter);
            }
        });*/
        populateListView();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nm = adapterView.getItemAtPosition(i).toString();
                if(count==0) {
                    ArrayList<String> names = new ArrayList<String>();
                    for (product p : products) {
                        if ((p.getType().toLowerCase()).equalsIgnoreCase(nm.toLowerCase())) {
                            names.add(p.getName());
                        }
                    }
                    count=count+1;
                    myAdapter1 = new MyAdapter1(getApplicationContext(), android.R.layout.simple_list_item_1, names);
                    mListView.setAdapter(myAdapter1);
                }
                else
                {
                    name.setText(nm);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Integer qty;
                qty=Integer.parseInt(quantity.getText().toString());
                if(!name.getText().toString().equals("") && !quantity.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "name";
                            field[1] = "quantity";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = name.getText().toString();
                            data[1] = String.valueOf(qty);
                            PutData putData = new PutData(ipaddress+"/stockupdate.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Stock Update Success")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void populateListView (){
        count=0;
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
                                if(!initial.contains(type))
                                    initial.add(type);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        /*ListAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, initial);
                        mListView.setAdapter(adapter);*/
                        myAdapter1 = new MyAdapter1(getApplicationContext(),android.R.layout.simple_list_item_1,initial);
                        mListView.setAdapter(myAdapter1);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(stockadd.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(stockadd.this).add(stringRequest);

    }


    class MyAdapter1 extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> rlast;
        public MyAdapter1(Context c, int resource,ArrayList<String> last) {
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
        bundle.putString("entryid", "admin");
        bundle.putString("name", "admin");//admin name
        Intent ii = new Intent(stockadd.this, admin.class);
        ii.putExtras(bundle);
        startActivity(ii);
    }
}