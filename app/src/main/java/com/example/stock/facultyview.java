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
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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

public class facultyview extends AppCompatActivity {

    ProgressBar progressBar;
    ListView mListView;
    ArrayList<facultyclass> products = new ArrayList<>();
    EditText search;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultyview);
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
                ArrayList<facultyclass> filteredname = new ArrayList<>();
                for(facultyclass i : products){
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET,  ipaddress +"/facultyread.php",
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
                                String monitor = object.getString("monitor");
                                String keyboard = object.getString("keyboard");
                                String mouse = object.getString("mouse");
                                String cpu = object.getString("cpu");
                                facultyclass product = new facultyclass(id,name,monitor,keyboard,mouse,cpu);
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
                Toast.makeText(facultyview.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(facultyview.this).add(stringRequest);

    }

    class MyAdapter extends ArrayAdapter<facultyclass> {

        Context context;
        ArrayList<facultyclass> rlast;
        String ipaddress;
        public MyAdapter(Context c, int resource,ArrayList<facultyclass> last, String ipaddress) {
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
            View row = layoutInflater.inflate(R.layout.cardview3, parent, false);
            TextView id = row.findViewById(R.id.id);
            TextView name = row.findViewById(R.id.name);
            TextView monitor = row.findViewById(R.id.monitor);
            TextView keyboard = row.findViewById(R.id.keyboard);
            TextView mouse = row.findViewById(R.id.mouse);
            TextView cpu = row.findViewById(R.id.cpu);
            Button edit = row.findViewById(R.id.edit);
            Button delete = row.findViewById(R.id.delete);
            final facultyclass product1= rlast.get(position);
            // now set our resources on views
            id.setText(product1.getId());
            name.setText(product1.getName());
            monitor.setText(product1.getMonitor());
            keyboard.setText(product1.getKeyboard());
            mouse.setText(product1.getMouse());
            cpu.setText(product1.getCpu());
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", product1.getId());
                    bundle.putString("name", product1.getName());
                    bundle.putString("monitor", product1.getMonitor());
                    bundle.putString("keyboard", product1.getKeyboard());
                    bundle.putString("mouse", product1.getMouse());
                    bundle.putString("cpu", product1.getCpu());
                    Intent ii = new Intent(facultyview.this, facultysysedit.class);
                    ii.putExtras(bundle);
                    startActivity(ii);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    androidx.appcompat.app.AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(facultyview.this);
                    alertDialog2.setTitle("Confirm Delete...");
                    alertDialog2.setMessage("Sure to delete "+ product1.getId() +" ?..");
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
                                            String[] field = new String[6];
                                            field[0] = "faculty_id";
                                            field[1] = "faculty_name";
                                            field[2] = "monitor";
                                            field[3] = "keyboard";
                                            field[4] = "mouse";
                                            field[5] = "cpu";
                                            //Creating array for data
                                            String[] data = new String[6];
                                            data[0] = product1.getId();
                                            data[1] = product1.getName();
                                            data[2] = product1.getMonitor();
                                            data[3] = product1.getKeyboard();
                                            data[4] = product1.getMouse();
                                            data[5] = product1.getCpu();
                                            PutData putData = new PutData(ipaddress+"/facultysysdelete.php", "POST", field, data);
                                            if (putData.startPut()) {
                                                if (putData.onComplete()) {
                                                    progressBar.setVisibility(View.GONE);
                                                    String result = putData.getResult();
                                                    if (result.equals("Faculty System Deleted Successfully!")){
                                                        Toast.makeText(getApplicationContext(),"Deleted "+product1.getId()+" Successfully!!",Toast.LENGTH_SHORT).show();
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("entryid", "admin");
                                                        bundle.putString("name", "admin");//admin name
                                                        Intent ii = new Intent(facultyview.this, admin.class);
                                                        ii.putExtras(bundle);
                                                        startActivity(ii);
                                                    }
                                                    else{
                                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                                                    }
                                                //finish();
                                                }
                                            }
                                            //End Write and Read data with URL
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
        Intent ii = new Intent(facultyview.this, admin.class);
        ii.putExtras(bundle);
        startActivity(ii);
    }
}