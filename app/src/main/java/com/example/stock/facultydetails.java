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

public class facultydetails extends AppCompatActivity {


    ProgressBar progressBar;
    ListView mListView;
    ArrayList<facultydetailsclass> products = new ArrayList<>();
    EditText search;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultydetails);
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
                ArrayList<facultydetailsclass> filteredname = new ArrayList<>();
                for(facultydetailsclass i : products){
                    if (i.getFaculty_id().toLowerCase().contains(searchArray.toLowerCase()) || i.getFaculty_name().toLowerCase().contains(searchArray.toLowerCase())) {
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
                                facultydetailsclass product = new facultydetailsclass(id,name,email,password,dept);
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
                Toast.makeText(facultydetails.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });
        Volley.newRequestQueue(facultydetails.this).add(stringRequest);
    }
    class MyAdapter extends ArrayAdapter<facultydetailsclass> {

        Context context;
        ArrayList<facultydetailsclass> rlast;
        String ipaddress;
        public MyAdapter(Context c, int resource,ArrayList<facultydetailsclass> last, String ipaddress) {
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
            View row = layoutInflater.inflate(R.layout.cardview5, parent, false);
            TextView id = row.findViewById(R.id.id);
            TextView name = row.findViewById(R.id.name);
            TextView email = row.findViewById(R.id.email);
            TextView password = row.findViewById(R.id.password);
            TextView dept = row.findViewById(R.id.dept);
            Button edit = row.findViewById(R.id.edit);
            Button delete = row.findViewById(R.id.delete);
            final facultydetailsclass product1= rlast.get(position);
            // now set our resources on views
            id.setText(product1.getFaculty_id());
            name.setText(product1.getFaculty_name());
            email.setText(product1.getEmail());
            password.setText(product1.getPassword());
            dept.setText(product1.getDept());
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", product1.getFaculty_id());
                    bundle.putString("name", product1.getFaculty_name());
                    bundle.putString("email", product1.getEmail());
                    bundle.putString("password", product1.getPassword());
                    bundle.putString("dept", product1.getDept());
                    Intent ii = new Intent(facultydetails.this, facultyedit.class);
                    ii.putExtras(bundle);
                    startActivity(ii);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[1];
                            field[0] = "faculty_id";
                            //Creating array for data
                            String[] data = new String[1];
                            data[0] = product1.getFaculty_id();
                            PutData putData = new PutData(ipaddress+"/facultydelete.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if(result.equals("Faculty Deleted Successfully!!")) {
                                        Toast.makeText(context, "" + result, LENGTH_SHORT).show();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("entryid", "admin");
                                        bundle.putString("name", "admin");//admin name
                                        Intent ii = new Intent(facultydetails.this, admin.class);
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
        bundle.putString("entryid", "admin");
        bundle.putString("name", "admin");//admin name
        Intent ii = new Intent(facultydetails.this, admin.class);
        ii.putExtras(bundle);
        startActivity(ii);
    }
}