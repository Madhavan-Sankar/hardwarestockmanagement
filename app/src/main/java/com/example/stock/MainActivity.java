package com.example.stock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    Button submit,signup;
    EditText username,password1;
    stock s;
    TextView forgot;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window;
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbarcolor1));
        }
        username=findViewById(R.id.username);
        password1=findViewById(R.id.password);
        submit=findViewById(R.id.submit);
        signup=findViewById(R.id.signup);
        forgot=findViewById(R.id.forgot);
        progressBar = findViewById(R.id.progress);

//        http://192.168.0.112/app
        //https://hardwarestockmanagement.000webhostapp.com/   for madhavansankar007@gmail.com
        //https://hardwarestockmanagement1.000webhostapp.com/   for stockfirebase3@gmail.com
        ((Ipaddress) this.getApplication()).setIp("https://hardwarestockmanagement1.000webhostapp.com/");//change the IP address here if needed
        final String ipaddress = ((Ipaddress) this.getApplication()).getIp();


        //Initialise Departments and their labs
        ((Ipaddress) this.getApplication()).setDepartment(Arrays.asList("ALL","CSE","ECE","EEE","CIVIL","MECH"));
        ((Ipaddress) this.getApplication()).setCse(Arrays.asList("ALL","cisco","sap","oracle"));
        ((Ipaddress) this.getApplication()).setEce(Arrays.asList("ALL","Ece","Ece","Ece"));
        ((Ipaddress) this.getApplication()).setEee(Arrays.asList("ALL","Eee","Eee","Eee"));
        ((Ipaddress) this.getApplication()).setCivil(Arrays.asList("ALL","Civil","Civil","Civil"));
        ((Ipaddress) this.getApplication()).setMech(Arrays.asList("ALL","Mech","Mech","Mech"));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id,password;
                id=username.getText().toString();
                password=password1.getText().toString();

                if( !id.equals("") && !password.equals("")) {
                    if(id.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin"))
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("entryid", "admin");
                        bundle.putString("name", "admin");//admin name
                        Intent ii = new Intent(MainActivity.this, admin.class);
                        ii.putExtras(bundle);
                        startActivity(ii);
                        Toast.makeText(getApplicationContext(), "Welcome " + "admin", LENGTH_SHORT).show();//change the admin name "admin"
                    }
                    else if(id.equalsIgnoreCase("lab") && password.equalsIgnoreCase("lab"))
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("entryid", "lab");//lab admin id
                        bundle.putString("name", "lab");//lab admin name
                        Intent ii = new Intent(MainActivity.this, lab.class);
                        ii.putExtras(bundle);
                        startActivity(ii);
                        Toast.makeText(getApplicationContext(), "Welcome " + "Lab admin", LENGTH_SHORT).show();//change the lab admin name "lab admin"
                    }
                    else
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[2];
                                field[0] = "id";
                                field[1] = "password";
                                //Creating array for data
                                String[] data = new String[2];
                                data[0] = id;
                                data[1] = password;
                                PutData putData = new PutData(ipaddress+ "/login.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();
                                        if (result.equals("Username or Password is Incorrect") || result.equals("Error: Database connection") || result.equals("All fields are required")) {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("entryid", id);
                                            bundle.putString("name", result);
                                            Intent i = new Intent(MainActivity.this, stafflogin.class);
                                            i.putExtras(bundle);
                                            startActivity(i);
                                            Toast.makeText(getApplicationContext(), "Welcome " + result, LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                //End Write and Read data with URL
                            }
                        });
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields are Required", Toast.LENGTH_SHORT).show();
                }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(MainActivity.this,signup.class);
                startActivity(ii);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id;
                id=username.getText().toString();
                if(id.equalsIgnoreCase(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter ID !", LENGTH_SHORT).show();
                }
                else {
                    StringRequest stringRequest = new StringRequest(Request.Method.GET,  ipaddress +"/forgot.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);
                                    try {
                                        JSONArray array = new JSONArray(response);
                                        for (int i = 0; i<array.length(); i++){
                                            JSONObject object = array.getJSONObject(i);
                                            String id1 = object.getString("faculty_id");
                                            String email = object.getString("email");
                                            if(id1.equalsIgnoreCase(id))
                                            {/*     //Mail Sending module not supported for higher version
                                                final String username = "stockfirebase3@gmail.com";
                                                final String password = "StockFirebase3";
                                                Properties props = new Properties();
                                                props.put("mail.smtp.auth", "true");
                                                props.put("mail.smtp.starttls.enable", "true");
                                                props.put("mail.smtp.host", "smtp.gmail.com");
                                                props.put("mail.smtp.port", "587");
                                                Session session = Session.getInstance(props,
                                                        new javax.mail.Authenticator() {
                                                            protected PasswordAuthentication getPasswordAuthentication() {
                                                                return new PasswordAuthentication(username, password);
                                                            }
                                                        });
                                                try {
                                                    Message message = new MimeMessage(session);
                                                    message.setFrom(new InternetAddress("stockfirebase3@gmail.com"));
                                                    message.setRecipients(Message.RecipientType.TO,
                                                            InternetAddress.parse(email));
                                                    message.setSubject("Testing Subject");
                                                    message.setText("Dear Mail Crawler,"
                                                            + "\n\n No spam to my email, please!");
                                                    Transport.send(message);
                                                    Toast.makeText(MainActivity.this, "ok", LENGTH_SHORT).show();
                                                } catch (Exception e) {
                                                    throw new RuntimeException(e);
                                                }*/
                                                break;
                                            }
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, error.toString(),Toast.LENGTH_LONG).show();
                        }
                    });
                    Volley.newRequestQueue(MainActivity.this).add(stringRequest);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
