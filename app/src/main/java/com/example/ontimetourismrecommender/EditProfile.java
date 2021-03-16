package com.example.ontimetourismrecommender;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    public static final String TAG = "TAG";

    private Bitmap bitmap;
     CircularImageView Photo;
    String UserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);
        ImageView back=findViewById(R.id.BackToProfile1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), profile1.class);
                startActivity(intent);
                finish();
            }
        });
        Photo = findViewById(R.id.ChangePhoto);
        Intent data=getIntent();
        TextView uplaod= findViewById(R.id.editphto);
        String url= data.getStringExtra("Photo");
        String Name=data.getStringExtra("Name");
        UserName=data.getStringExtra("UserName");
        String Phone=data.getStringExtra("Phone");
        String Amount=data.getStringExtra("Amount");

        String Status= data.getStringExtra("Status");
        String Education= data.getStringExtra("Education");
        String Birthdate= data.getStringExtra("Birthdate");
        Log.d(TAG, "onCreate: "+url+" "+ Name+" "+UserName+" "+Phone+" "+Amount+" "+Status+" "+Education+" "+Birthdate);
        TextView username=findViewById(R.id.Editusername);
        username.setText(UserName);
        EditText name= findViewById(R.id.EditName);
        name.setText(Name);
        EditText phone =findViewById(R.id.EditPhone);
        phone.setText(Phone);
        EditText amont=findViewById(R.id.EditAmonutOfex);
        amont.setText(Amount);
        EditText status= findViewById(R.id.EditStatus);
        status.setText(Status);
        EditText education= findViewById(R.id.Editeducation);
        education.setText(Education);
        EditText birthdate= findViewById(R.id.EditBirthdate);
        birthdate.setText(Birthdate);
        TextView save=findViewById(R.id.Save);
        if(url!="null")
        {
            CircularImageView editphoto= findViewById(R.id.ChangePhoto);
            Picasso.get().load(url).networkPolicy(NetworkPolicy.NO_CACHE).into(editphoto);
        }
        uplaod.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                choosefile();
            }
        });
        save.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                String EditName= name.getText().toString();
                String EditPhone= phone.getText().toString();
                String EdidAmount= amont.getText().toString();

                String EditStatus= status.getText().toString();
                String EditEducation= education.getText().toString();
                String Editbirthdate= birthdate.getText().toString();

                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,"http://10.0.2.2/touristrecommender/edit_detail.php"
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
                                Intent intent= new Intent(getApplicationContext(), profile1.class);
                                startActivity(intent);
                                finish();
                            }
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
                        params.put("name",EditName);
                        params.put("username",UserName);
                        params.put("phone", EditPhone);
                        params.put("amount", EdidAmount);
                        params.put("Status", EditStatus);
                        params.put("Education", EditEducation);
                        params.put("birthday", Editbirthdate);
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);

            }
        });


    }



    private void choosefile(){
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK&& data!=null &&data.getData()!= null){
            Uri filepath= data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                Photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            UplaodPicture(UserName, getStringImage(bitmap));
        }
    }
    private void UplaodPicture(final String UserName, final String photo){



                final ProgressDialog progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Uplaoding...");
                progressDialog.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://10.0.2.2/touristrecommender/upload.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
progressDialog.dismiss();

                try {
                    JSONObject obj=  new JSONObject(response);
String result=obj.getString("success");
if(result.equals("1")){

    Intent i = new Intent(getApplicationContext(), profile1.class);
    startActivity(i);
    finish();
}



                }catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(
                            getApplicationContext(),
                            "Try Again "+ e.toString(),
                            Toast.LENGTH_LONG

                    ).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(
                        getApplicationContext(),
                        "Try Again"+ error.toString(),
                        Toast.LENGTH_LONG

                ).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {




                Map<String, String> params =new HashMap<>();
                params.put("photo",photo);
                params.put("userName",UserName);

                return params;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStreamt= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamt);
        byte[] imageByteArray= byteArrayOutputStreamt.toByteArray();
        return Base64.encodeToString(imageByteArray,Base64.DEFAULT);

    }

}