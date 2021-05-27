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

public class stockentry extends AppCompatActivity {

    Button submit;
    EditText type1,name1,quantity1;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockentry);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor));
        }
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();
        type1=findViewById(R.id.type);
        name1=findViewById(R.id.name);
        quantity1=findViewById(R.id.quantity);
        progressBar = findViewById(R.id.progress);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String type,name;
                final int quantity;
                type=type1.getText().toString().toLowerCase().trim();
                name=name1.getText().toString().toLowerCase().trim();
                quantity=Integer.parseInt(quantity1.getText().toString().trim());
                if(!name.equals("") && !type.equals("") && quantity!=0) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[3];
                            field[0] = "type";
                            field[1] = "name";
                            field[2] = "quantity";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = type;
                            data[1] = name;
                            data[2] = String.valueOf(quantity);
                            PutData putData = new PutData(ipaddress+"/stockapp.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Stock Update Success")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        finish();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("entryid", "admin");
                                        bundle.putString("name", "admin");//admin name
                                        Intent i = new Intent(stockentry.this,admin.class);
                                        i.putExtras(bundle);
                                        startActivity(i);
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
        bundle.putString("entryid", "admin");
        bundle.putString("name", "admin");//admin name
        Intent ii = new Intent(stockentry.this, admin.class);
        ii.putExtras(bundle);
        startActivity(ii);
    }
}