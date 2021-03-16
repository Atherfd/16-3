package com.example.ontimetourismrecommender;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.*;
import com.android.volley.toolbox.Volley;

public class Sheardpref {

        private static Sheardpref instance;
        private static Context ctx;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "username";
private static final String SHARED_PREF_SiteID="TouristSiteID";
private static final String KEY_ID="SiteID";
    private static final String SHARED_PREF_photo="Touristphoto";
    private static final String KEY_photo="photo";
    private  static String  SHARED_PREF_SiteInfoID="TouristSiteIDInfo";
    private static final String SiteIDIfo="SiteIDInfo";
        private Sheardpref(Context context) {
            ctx = context;

        }

        public static synchronized Sheardpref getInstance(Context context) {
            if (instance == null) {
                instance = new Sheardpref(context);
            }
            return instance;
        }
        public boolean storephoto(String url){

            SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_photo, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();


            editor.putString(KEY_photo, url);

            editor.apply();

            return true;
        }
        public boolean StoreId(String ID){
            SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_SiteID, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();


            editor.putString(KEY_ID, ID);

            editor.apply();

            return true;
        }


    public boolean SetSiteInfoId(String SiteInfo ){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_SiteInfoID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(SiteIDIfo, SiteInfo);

        editor.apply();

        return true;
    }

    public String GetSiteIDInfo(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_SiteInfoID, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SiteIDIfo, null);
    }


    public boolean userLogin(String username ){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(KEY_USERNAME, username);

        editor.apply();

        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME, null) != null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        SharedPreferences sharedPreference = ctx.getSharedPreferences(SHARED_PREF_photo, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreference.edit();
        editor1.clear();
        editor1.apply();

        return true;
    }
    public boolean detetSiteID(){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_SiteID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
    public String getUsername(){
            SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_USERNAME, null);
    }
    public String getphoto(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_photo, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_photo, null);
    }

public String getSiteId(){

    SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_SiteID, Context.MODE_PRIVATE);
    return sharedPreferences.getString(KEY_ID, null);

}


}
