
package com.example.ontimetourismrecommender;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class profile1 extends AppCompatActivity {
  String url="";
    public static final String URL = "http://10.0.2.2/touristrecommender/profile.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        TextView logout= findViewById(R.id.logout);
        TextView edit_profile= findViewById(R.id.editProfile);
        TextView  historyfeed=findViewById(R.id.historyfeed);
        userLogin();
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView name=findViewById(R.id.name);
                TextView phone=findViewById(R.id.Phone);
                TextView amount=findViewById(R.id.AmonutOfex);


                TextView status=findViewById(R.id.Status);
                TextView education=findViewById(R.id.education);
                TextView Birthdate=findViewById(R.id.Birthdate);
                CircularImageView Photo= findViewById(R.id.photo);
                Intent intent=new Intent(getApplicationContext(), EditProfile.class);
                intent.putExtra("UserName",Sheardpref.getInstance(getApplicationContext()).getUsername());
                intent.putExtra("Name", name.getText().toString());
                intent.putExtra("Phone", phone.getText().toString());
                intent.putExtra("Amount", amount.getText().toString());

                intent.putExtra("Status", status.getText().toString());
                intent.putExtra("Education", education.getText().toString());
                intent.putExtra("Birthdate",Birthdate.getText().toString() );
                intent.putExtra("Photo",url);
                startActivity(intent);
                finish();


            }
        });


ImageView Back= findViewById(R.id.backto);

       Back.setOnClickListener (new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), main_explore.class);
                startActivity(intent);
                finish();



            }});

        historyfeed.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), HistoryFeed.class);
                startActivity(i);
                finish();

            }});

        logout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               if( Sheardpref.getInstance(getApplicationContext()).logout()){
                   Intent i = new Intent(getApplicationContext(), logon.class);
                   startActivity(i);
                   finish();
               }
            }
        });






    }

    private void userLogin(){

        TextView name=findViewById(R.id.name);
        TextView username=findViewById(R.id.username);
        TextView phone=findViewById(R.id.Phone);
        TextView amount=findViewById(R.id.AmonutOfex);
        TextView status=findViewById(R.id.Status);
        TextView education=findViewById(R.id.education);
        TextView Birthdate=findViewById(R.id.Birthdate);
        CircularImageView Photo= findViewById(R.id.photo);


        String Username = Sheardpref.getInstance(getApplicationContext()).getUsername();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                if(product.getString("UserName").equals(Username)){

                                username.setText(Username);
                                    amount.setText(product.getString("AmountOfexchane"));
                                    Birthdate.setText(product.getString("age"));
                            name.setText(product.getString("Name"));
                            phone.setText(product.getString("phone"));
                                    status.setText(product.getString("status"));
                                    education.setText(product.getString("education"));
                                          url=product.getString("Photo");
                                      if( !url.equals("null")) {
                                          Glide.with(getApplicationContext())
                                                  .load(url).into(Photo);


                                      }
                                }

                            }
                        } catch (JSONException  e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);}



}