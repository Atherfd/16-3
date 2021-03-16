package com.example.ontimetourismrecommender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
        import android.os.Bundle;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class site_ifo extends AppCompatActivity {

    String[] SiteInfo= new String[8];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_info);
        String ID=Sheardpref.getInstance(getApplicationContext()).GetSiteIDInfo();

ImageView back=findViewById(R.id.backtoexplore);

back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),main_explore.class );
        startActivity(intent);
        finish();
    }
});
      ImageButton AddToHistory= findViewById(R.id.AddToHistoryFeed);
      AddToHistory.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              AddToFeed();
          }
      });

ImageView image=findViewById(R.id.slider);
TextView name= findViewById(R.id.SiteInformationName);
TextView des=findViewById(R.id.description);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,"http://10.0.2.2/touristrecommender/SiteInfo.php"
                ,   new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject obj=  array.getJSONObject(0);
                    Picasso.get().load(obj.getString("Img")).into(image);
                    name.setText(obj.getString("Name"));
                    des.setText(obj.getString("Description"));

                    int i=0;
                    if(!obj.getString("ALongRangePlaceWithAmpleVisibility").equals("No"))
                    SiteInfo[i++]="Wide view";
                    if(!obj.getString("SeaSide").equals("No"))
                    SiteInfo[i++]="Sea side";
                    SiteInfo[i++]=obj.getString("PlaceStyle");
                    if(!obj.getString("WalkAndMove").equals("No"))
                    SiteInfo[i++]="Walking paths";
                    if(!obj.getString("Camping").equals("No"))
                    SiteInfo[i++]="Camping site";
                    ListView LIST2=(ListView)findViewById(R.id.LView_info);
                    CoustomInfo CoustomAdapter2=new CoustomInfo();
                    LIST2.setAdapter(CoustomAdapter2);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(
                            getApplicationContext(),
                            e.getMessage(),
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
                params.put("Site",ID);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }










    private void AddToFeed() {

        // write the nedid code to input all info into the tabel

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,"http://10.0.2.2/touristrecommender/AddToHistory.php"
                ,   new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                JSONObject obj=  new JSONObject(response);
                String secssus= obj.getString("success");
                if (secssus.equals("1")){

                Toast.makeText(
                        getApplicationContext(),
                       "Successfully added to history feed !",
                        Toast.LENGTH_LONG
                ).show();}

            } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(
                            getApplicationContext(),
                            "Error catch",
                            Toast.LENGTH_LONG
                    ).show();
                }}

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
                params.put("Site", Sheardpref.getInstance(getApplicationContext()).GetSiteIDInfo());
                params.put("username", Sheardpref.getInstance(getApplicationContext()).getUsername());
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);








    }


    class CoustomInfo extends BaseAdapter{
        @Override
        public int getCount() {
            return SiteInfo.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView =getLayoutInflater().inflate(R.layout.coustomforinfo,null);
            TextView textView_info=(TextView)convertView.findViewById(R.id.textView_infos);
            textView_info.setText(SiteInfo[position]);


            return convertView;
        }
    }
}
