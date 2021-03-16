package com.example.ontimetourismrecommender;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import static com.android.volley.Request.Method.POST;

public class main_explore extends AppCompatActivity {

    RecyclerView eventRecycler, RecommendationExploreRecycler;
    eventsAdaptar EventAdapter;
    recommendation_explore_Adaptar RecommendationExploreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site__recycle_view);


        List<siteData> EventDataList = new ArrayList<>();
        List<siteData> SiteDataList = new ArrayList<>();
       CircularImageView photo= findViewById(R.id.image);
        ImageView recommendgray= findViewById(R.id.recommendGray);

        recommendgray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), main_recommend.class);
                startActivity(intent);
                finish();
            }
        });
        Picasso.get().load( Sheardpref.getInstance(getApplicationContext()).getphoto()).into(photo);
photo.setOnClickListener (new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), profile1.class);
                startActivity(intent);
                finish();



            }});


        StringRequest stringRequest = new StringRequest(
                POST,
               "http://10.0.2.2/touristrecommender/Explore.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject record = array.getJSONObject(i);
if(record.getString("Type").equals("Site")){

  SiteDataList.add(new siteData(record.getString("Name"), record.getString("Img"), record.getString("Location"), record.getString("ID")));

}
else {
    EventDataList.add(new siteData(record.getString("Name"), record.getString("Img"), record.getString("Location"), record.getString("ID")));

}
}
                          setEventRecycler(EventDataList);
                            setRecommendationRecycler(SiteDataList);


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
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void setEventRecycler(List<siteData> recentsDataList) {

        eventRecycler = findViewById(R.id.recent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        eventRecycler.setLayoutManager(layoutManager);
        EventAdapter = new eventsAdaptar(this, recentsDataList);
        eventRecycler.setAdapter(EventAdapter);

    }

    private void setRecommendationRecycler(List<siteData> recommendationDataList) {

        RecommendationExploreRecycler = findViewById(R.id.RecommendationExplore_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RecommendationExploreRecycler.setLayoutManager(layoutManager);
        RecommendationExploreAdapter = new recommendation_explore_Adaptar (main_explore.this, recommendationDataList);
        RecommendationExploreRecycler.setAdapter(RecommendationExploreAdapter);

    }



}
