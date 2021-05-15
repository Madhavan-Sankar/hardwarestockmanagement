package com.example.stock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
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

public class facultyedit extends AppCompatActivity {

    String id,name,email,password,dept;//predefined and set
    TextView id1;
    EditText email1,password1,dept1;
    EditText name1;
    Button submit;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultyedit);
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
        email = bundle.getString("email");
        password = bundle.getString("password");
        dept = bundle.getString("dept");
        id1=findViewById(R.id.id);
        name1=findViewById(R.id.name);
        email1=findViewById(R.id.email);
        password1=findViewById(R.id.password);
        dept1=findViewById(R.id.dept);
        id1.setText(id);
        name1.setText(name);
        email1.setText(email);
        password1.setText(password);
        dept1.setText(dept);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id2,name2,email2,password2,dept2;
                id2=id1.getText().toString();
                name2=name1.getText().toString();
                email2=email1.getText().toString();
                password2=password1.getText().toString();
                dept2=dept1.getText().toString();
                if(id2.equals(id) && name2.equals(name) && email2.equals(email) && password2.equals(password) && dept2.equals(dept))
                {
                    Toast.makeText(facultyedit.this, "No change in data!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(id2.equals("") || name2.equals("") || email2.equals("") || password2.equals("") || dept2.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "All fields are Required", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressBar.setVisibility(View.VISIBLE);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[5];
                                field[0] = "faculty_id";
                                field[1] = "faculty_name";
                                field[2] = "email";
                                field[3] = "password";
                                field[4] = "dept";
                                //Creating array for data
                                String[] data = new String[9];
                                data[0] = id2;
                                data[1] = name2;
                                data[2] = email2;
                                data[3] = password2;
                                data[4] = dept2;
                                PutData putData = new PutData(ipaddress+"/facultyedit.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();
                                        if (result.equals("Faculty Details Update Success")){
                                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("entryid", "admin");
                                            bundle.putString("name", "admin");//admin name
                                            Intent ii = new Intent(facultyedit.this, admin.class);
                                            ii.putExtras(bundle);
                                            startActivity(ii);
                                            //finish();
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
                }
            }
        });
    }


}