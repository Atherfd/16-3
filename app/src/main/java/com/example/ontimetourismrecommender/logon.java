package com.example.ontimetourismrecommender;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class logon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);
        //Start ProgressBar first (Set visibility VISIBLE)
        EditText username= findViewById(R.id.username);
        EditText pass= findViewById(R.id.pass);
        Button log= findViewById(R.id.serch);
        ProgressBar pro= findViewById(R.id.progress);


        log.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                String name= username.getText().toString();
                String p= pass.getText().toString();

                if(name.equals("") || p.equals("")) {
                    Toast.makeText(getApplicationContext(), "All filed are require",Toast.LENGTH_SHORT).show();

                }else{
                    pro.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "pwd";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = name;
                            data[1] = p;
                            PutData putData = new PutData("http://10.0.2.2/touristrecommender/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    pro.setVisibility(View.GONE);

                                    String result = putData.getResult();
                                    if(result.equals("Login Success")){
                                        StringRequest stringRequest = new StringRequest(
                                                Request.Method.POST,"http://10.0.2.2/touristrecommender/getphoto.php"
                                                ,   new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONArray array = new JSONArray(response);
                                                    JSONObject obj=  array.getJSONObject(0);
                                                    String photo= obj.getString("photo");
                                                    Sheardpref.getInstance(getApplicationContext()).storephoto(photo);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(
                                                            getApplicationContext(),
                                                            "Error catch",
                                                            Toast.LENGTH_LONG
                                                    ).show();
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                Toast.makeText(
                                                        getApplicationContext(),
                                                        "Error",
                                                        Toast.LENGTH_LONG
                                                ).show();
                                            }
                                        }){
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params =new HashMap<>();
                                                params.put("UserName",name);

                                                return params;
                                            }
                                        };
                                        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                                        requestQueue.add(stringRequest);


                                        Sheardpref.getInstance(getApplicationContext()).userLogin(name);
                                        Intent i = new Intent(getApplicationContext(), main_explore.class);
                                        startActivity(i);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), result ,Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }
                            //End Write and Read data with URL
                        }
                    });



                }  }
        });
            }}
