package com.example.ontimetourismrecommender;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class dialog  extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    setContentView(R.layout.dialog);
        final RatingBar ratingRatingBar = (RatingBar) findViewById(R.id.rating_rating_bar);
        Button submitButton = (Button) findViewById(R.id.rate);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
String UserName=  Sheardpref.getInstance(getApplicationContext()).getUsername();
String SiteId= Sheardpref.getInstance(getApplicationContext()).getSiteId();
                Sheardpref.getInstance(getApplicationContext()).detetSiteID();
String rate = String.valueOf(ratingRatingBar.getRating());
                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,"http://10.0.2.2/touristrecommender/Rate.php"
                        ,   new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj=  new JSONObject(response);
                            String secssus= obj.getString("success");
                            if (secssus.equals("1")){

                                Toast.makeText(
                                        getApplicationContext(),
                                        "Success!",
                                        Toast.LENGTH_LONG

                                ).show();
                                Intent intent= new Intent(getApplicationContext(), HistoryFeed.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error catch"+ e.getMessage(),
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
                        params.put("username",UserName);
                        params.put("SiteID",SiteId );
                        params.put("rate", rate);

                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);


            }});



    }
}
