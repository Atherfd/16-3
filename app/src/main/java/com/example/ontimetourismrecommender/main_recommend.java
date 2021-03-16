package com.example.ontimetourismrecommender;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
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
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

import static com.android.volley.Request.Method.POST;

public class main_recommend extends AppCompatActivity {

    RecyclerView eventRecycler, RecommendationExploreRecycler;
    eventsAdaptar EventAdapter;
    recommendation_explore_Adaptar RecommendationExploreAdapter;
    private AlertDialog.Builder dialogBuilder;
     private AlertDialog dialog;
     private EditText city;
     private Button search;

private String getcity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site2_recycle);

        ImageView exploregray= findViewById(R.id.ExploreGray);
        exploregray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), main_explore.class);
                startActivity(intent);
                finish();
            }
        });



        // Now here we will add some dummy data in our model class
        createNewContactDialog();


        CircularImageView photo= findViewById(R.id.image);
        Picasso.get().load( Sheardpref.getInstance(getApplicationContext()).getphoto()).into(photo);
        photo.setOnClickListener (new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), profile1.class);
                startActivity(intent);
                finish();



            }});




    }
    public void request(){


        List<siteData> EventDataList = new ArrayList<>();
        List<siteData> SiteDataList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(
                POST,
                "http://10.0.2.2/touristrecommender/R.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONArray array = new JSONArray(response);
if(array.length()==0){

    Toast.makeText(
            getApplicationContext(),
            "Sorry, there is no sites avaliable :( ",
            Toast.LENGTH_LONG

    ).show();

}
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
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new Hashtable<String, String>();
                params.put("city",getcity );
                params.put("Username",Sheardpref.getInstance(getApplicationContext()).getUsername() );
                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
   public void createNewContactDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View ContactPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        city= (EditText) ContactPopupView.findViewById(R.id.editTextTextPersonName);
        search=(Button) ContactPopupView.findViewById(R.id.serch);
        dialogBuilder.setView(ContactPopupView);
        dialog= dialogBuilder.create();
        dialog.show();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getcity=city.getText().toString();
               dialog.dismiss();
                request();
            }
        });
    }

    private void setEventRecycler(List<siteData> recentsDataList) {

        eventRecycler = findViewById(R.id.recentEvent_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        eventRecycler.setLayoutManager(layoutManager);
        EventAdapter = new eventsAdaptar(this, recentsDataList);
        eventRecycler.setAdapter(EventAdapter);

    }

    private void setRecommendationRecycler(List<siteData> recommendationDataList) {

        RecommendationExploreRecycler = findViewById(R.id.Recommend_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        RecommendationExploreRecycler.setLayoutManager(layoutManager);
        RecommendationExploreAdapter = new recommendation_explore_Adaptar (main_recommend.this, recommendationDataList);
        RecommendationExploreRecycler.setAdapter(RecommendationExploreAdapter);

    }



}

