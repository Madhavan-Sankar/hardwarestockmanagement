package com.example.stock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class requestinsert extends AppCompatActivity {

    String entryid,name;
    ProgressBar progressBar;
    EditText id1,name1,remarks1;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestinsert);
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
        id1=findViewById(R.id.id);
        id1.setText(entryid);
        name1=findViewById(R.id.name);
        remarks1=findViewById(R.id.remarks);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id,name,remark;
                id=id1.getText().toString().trim();
                name=name1.getText().toString().trim();
                remark=remarks1.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                if(!id.equals("") && !name.equals("") && !remark.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "id";
                            field[1] = "name";
                            field[2] = "remark";
                            field[3] = "status";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = id;
                            data[1] = name;
                            data[2] = remark;
                            data[3] = "Pending";
                            PutData putData = new PutData(ipaddress+"/requestinsert.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Request Insertion Success")){
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

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString("entryid", entryid);
        bundle.putString("name", name);
        Intent ii = new Intent(requestinsert.this, stafflogin.class);
        ii.putExtras(bundle);
        startActivity(ii);
    }
}