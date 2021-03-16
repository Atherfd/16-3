package com.example.ontimetourismrecommender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class HistoryFeed extends AppCompatActivity {
    private static final String URL_HistoryFeed = "http://10.0.2.2/touristrecommender/Historyfeed.php";
    List<HistoryFeedData> historyFeedData;
    androidx.recyclerview.widget.RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_viwe);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyFeedData = new ArrayList<HistoryFeedData>();
        ImageView back=findViewById(R.id.BackToProfile);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), profile1.class);
                startActivity(intent);
                finish();
            }
        });

        load();

    }

    private void load() {
        String userName = Sheardpref.getInstance(getApplicationContext()).getUsername();

        StringRequest stringRequest = new StringRequest(
                POST,
                URL_HistoryFeed,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                              

                                    historyFeedData.add(new HistoryFeedData(product.getString("SiteName"), product.getString("Image"), product.getInt("Rating"), product.getString("SiteID")));


                            }
                            AdaptarRecycleViwe adapter = new AdaptarRecycleViwe(HistoryFeed.this, historyFeedData);
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Try Again " + e.toString(),
                                    Toast.LENGTH_LONG

                            ).show();
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(
                        getApplicationContext(),
                        "Try Again" + error.toString(),
                        Toast.LENGTH_LONG

                ).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new Hashtable<String, String>();
                params.put("username",userName );

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

                      }