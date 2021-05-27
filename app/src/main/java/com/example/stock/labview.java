package com.example.stock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.view.WindowManager;
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

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class labview extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ProgressBar progressBar;
    ListView mListView;
    ArrayList<labclass> products = new ArrayList<>();
    ArrayList<labclass> filterbutton = new ArrayList<>();
    EditText search;
    String entryid,name;
    MyAdapter myAdapter;
    ImageView filter;
    LinearLayout filter1;
    Button apply;
    Spinner deptspinner;
    Spinner labspinner;
    int is_clicked=0;

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
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        Bundle bundle = getIntent().getExtras();
        entryid = bundle.getString("entryid");
        name = bundle.getString("name");
        progressBar=findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        mListView=findViewById(R.id.listview);
        search=findViewById(R.id.search);
        deptspinner = findViewById(R.id.deptspinner);
        labspinner = findViewById(R.id.labspinner);
        apply=findViewById(R.id.apply);
        filter=findViewById(R.id.filter);
        filter1=findViewById(R.id.filter1);
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
                if(is_clicked==0) {
                    for (labclass i : products) {
                        if (i.getId().toLowerCase().contains(searchArray.toLowerCase()) || i.getLab().toLowerCase().contains(searchArray.toLowerCase())) {
                            if (!filteredname.contains(i)) {
                                filteredname.add(i);
                            }
                        }
                    }
                }
                else
                {
                    for (labclass i : filterbutton) {
                        if (i.getId().toLowerCase().contains(searchArray.toLowerCase())) {
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
        final List<String> department = ((Ipaddress) this.getApplication()).getDepartment();
        @SuppressLint("ResourceType")
        ArrayAdapter adapter = new ArrayAdapter(labview.this, R.layout.my_selected_item, department);
        adapter.setDropDownViewResource(R.layout.my_dropdown_item);
        deptspinner.setAdapter(adapter);
        deptspinner.setOnItemSelectedListener(labview.this);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dept = deptspinner.getSelectedItem().toString();
                String lab = labspinner.getSelectedItem().toString();
                search.setText("");
                filterbutton.clear();
                is_clicked=1;
                if(!dept.equalsIgnoreCase("ALL")) {
                    for (labclass i : products) {
                        if(!lab.equalsIgnoreCase("ALL"))
                        {
                            if (i.getDept().equalsIgnoreCase(dept) && i.getLab().equalsIgnoreCase(lab))
                            {
                                if (!filterbutton.contains(i)) {
                                    filterbutton.add(i);
                                }
                            }
                        }
                        else {
                            if (i.getDept().equalsIgnoreCase(dept)) {
                                if (!filterbutton.contains(i)) {
                                    filterbutton.add(i);
                                }
                            }
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
                Toast.makeText(labview.this, "Filter Applied Successfully!!", Toast.LENGTH_SHORT).show();
            }
        });
        populateListView();
    }

    private void populateListView (){
        progressBar.setVisibility(View.VISIBLE);
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
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
                                String dept = object.getString("dept");
                                String monitor = object.getString("monitor");
                                String keyboard = object.getString("keyboard");
                                String mouse = object.getString("mouse");
                                String cpu = object.getString("cpu");
                                labclass product = new labclass(id,lab,dept,monitor,keyboard,mouse,cpu);
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
                Toast.makeText(labview.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(labview.this).add(stringRequest);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String dept = adapterView.getItemAtPosition(i).toString();
        switch(dept)
        {
            case "CSE":
                ArrayAdapter adapter = new ArrayAdapter(labview.this, R.layout.my_selected_item, ((Ipaddress) getApplication()).getCse());
                adapter.setDropDownViewResource(R.layout.my_dropdown_item);
                labspinner.setAdapter(adapter);
                break;
            case "ECE":
                ArrayAdapter adapter1 = new ArrayAdapter(labview.this, R.layout.my_selected_item, ((Ipaddress) getApplication()).getEce());
                adapter1.setDropDownViewResource(R.layout.my_dropdown_item);
                labspinner.setAdapter(adapter1);
                break;
            case "EEE":
                ArrayAdapter adapter2 = new ArrayAdapter(labview.this, R.layout.my_selected_item, ((Ipaddress) getApplication()).getEee());
                adapter2.setDropDownViewResource(R.layout.my_dropdown_item);
                labspinner.setAdapter(adapter2);
                break;
            case "CIVIL":
                ArrayAdapter adapter3 = new ArrayAdapter(labview.this, R.layout.my_selected_item, ((Ipaddress) getApplication()).getCivil());
                adapter3.setDropDownViewResource(R.layout.my_dropdown_item);
                labspinner.setAdapter(adapter3);
                break;
            case "MECH":
                ArrayAdapter adapter4 = new ArrayAdapter(labview.this, R.layout.my_selected_item, ((Ipaddress) getApplication()).getMech());
                adapter4.setDropDownViewResource(R.layout.my_dropdown_item);
                labspinner.setAdapter(adapter4);
                break;
            default:
                ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(labview.this,R.array.all_lab,R.layout.my_selected_item);
                adapter5.setDropDownViewResource(R.layout.my_dropdown_item);
                labspinner.setAdapter(adapter5);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    class MyAdapter extends ArrayAdapter<labclass> {

        Context context;
        ArrayList<labclass> rlast;
        String ipaddress;
        public MyAdapter(Context c, int resource,ArrayList<labclass> last,String ipaddress) {
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
            View row = layoutInflater.inflate(R.layout.cardview6, parent, false);
            TextView id = row.findViewById(R.id.id);
            TextView name1 = row.findViewById(R.id.name);
            TextView dept1 = row.findViewById(R.id.dept);
            TextView monitor = row.findViewById(R.id.monitor);
            TextView keyboard = row.findViewById(R.id.keyboard);
            TextView mouse = row.findViewById(R.id.mouse);
            TextView cpu = row.findViewById(R.id.cpu);
            Button edit = row.findViewById(R.id.edit);
            Button delete = row.findViewById(R.id.delete);
            final labclass product1= rlast.get(position);
            // now set our resources on views
            id.setText(product1.getId());
            name1.setText(product1.getLab());
            dept1.setText(product1.getDept());
            monitor.setText(product1.getMonitor());
            keyboard.setText(product1.getKeyboard());
            mouse.setText(product1.getMouse());
            cpu.setText(product1.getCpu());
            if(!entryid.equalsIgnoreCase("admin"))
            {
                edit.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);
            }
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", product1.getId());
                    bundle.putString("lab", product1.getLab());
                    bundle.putString("dept",product1.getDept());
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
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    androidx.appcompat.app.AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(labview.this);
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
                                            data[0] = product1.getId();
                                            data[1] = product1.getLab();
                                            data[2] = product1.getDept();
                                            data[3] = product1.getMonitor();
                                            data[4] = product1.getKeyboard();
                                            data[5] = product1.getMouse();
                                            data[6] = product1.getCpu();
                                            PutData putData = new PutData(ipaddress+"/labsysdelete.php", "POST", field, data);
                                            if (putData.startPut()) {
                                                if (putData.onComplete()) {
                                                    progressBar.setVisibility(View.GONE);
                                                    String result = putData.getResult();
                                                    if (result.equals("Lab System Deleted Successfully!")){
                                                        Toast.makeText(getApplicationContext(),"Deleted "+product1.getId()+" Successfully!!",Toast.LENGTH_SHORT).show();
                                                        if(entryid.equalsIgnoreCase("admin"))
                                                        {
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("entryid", entryid);
                                                            bundle.putString("name", name);//admin name
                                                            Intent ii = new Intent(labview.this, admin.class);
                                                            ii.putExtras(bundle);
                                                            startActivity(ii);
                                                        }
                                                        else
                                                        {
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("entryid", entryid);
                                                            bundle.putString("name", name);//admin name
                                                            Intent ii = new Intent(labview.this, lab.class);
                                                            ii.putExtras(bundle);
                                                            startActivity(ii);
                                                        }
                                                        //finish();
                                                    }
                                                    else{
                                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                                                    }
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