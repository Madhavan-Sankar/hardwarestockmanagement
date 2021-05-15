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

public class stockview extends AppCompatActivity {

    ProgressBar progressBar;
    ListView mListView;
    ArrayList<product> products = new ArrayList<>();
    EditText search;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockview);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor));
        }
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
                ArrayList<product> filteredname = new ArrayList<>();
                for(product i : products){
                    if (i.getName().toLowerCase().contains(searchArray.toLowerCase()) || i.getType().toLowerCase().contains((searchArray.toLowerCase()))) {
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
                Toast.makeText(stockview.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(stockview.this).add(stringRequest);

    }




    class MyAdapter extends ArrayAdapter<product> {

        Context context;
        ArrayList<product> rlast;
        public MyAdapter(Context c, int resource,ArrayList<product> last) {
            super(c, resource,last);
            this.context = c;
            rlast=last;
        }
        @SuppressLint("WrongViewCast")
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.cardview, parent, false);
            TextView type = row.findViewById(R.id.type);
            TextView name = row.findViewById(R.id.name);
            TextView quantity = row.findViewById(R.id.quantity);
            final product product1 = rlast.get(position);
            // now set our resources on views
            type.setText(product1.getType());
            name.setText(product1.getName());
            quantity.setText(String.valueOf(product1.getQuantity()));
            return row;
        }
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString("entryid", "admin");
        bundle.putString("name", "admin");//admin name
        Intent ii = new Intent(stockview.this, admin.class);
        ii.putExtras(bundle);
        startActivity(ii);
    }
}