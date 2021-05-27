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

public class labservice extends AppCompatActivity {

    ProgressBar progressBar;
    EditText id1,type1,name1,remarks1;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labservice);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor));
        }
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        progressBar=findViewById(R.id.progress);
        id1=findViewById(R.id.id);
        type1=findViewById(R.id.type);
        name1=findViewById(R.id.name);
        remarks1=findViewById(R.id.remarks);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id,type,name,remark;
                id=id1.getText().toString().trim();
                type=type1.getText().toString().trim();
                name=name1.getText().toString().trim();
                remark=remarks1.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                if(!id.equals("") && !type.equals("") && !name.equals("") && !remark.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "id";
                            field[1] = "type";
                            field[2] = "name";
                            field[3] = "remark";
                            field[4] = "status";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = id;
                            data[1] = type;
                            data[2] = name;
                            data[3] = remark;
                            data[4] = "Pending";
                            PutData putData = new PutData(ipaddress+"/labserviceinsert.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Service Insertion Success")){
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
        bundle.putString("entryid","lab");
        bundle.putString("name","lab");
        Intent i = new Intent(labservice.this,lab.class);
        i.putExtras(bundle);
        startActivity(i);
    }
}