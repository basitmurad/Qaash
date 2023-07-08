package com.example.qaash;


import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences sharedPreferences;

    private static final String USER_LATITUDE = "userLatitude";
    private static final String USER_LONGITUDE = "userLongitude";
    private static final String USER= "user";
    private static final String LOCATION_NAME= "locationName";
    private static final String IS_LOGIN = "isLogin";
    private static final String USER_NAME = "userName";
    private static final String USER_Id = "userId";

    public SessionManager(Context context) {


        sharedPreferences = context.getSharedPreferences(USER_LATITUDE,Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(USER_LONGITUDE, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(LOCATION_NAME, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(USER_NAME ,Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(USER_Id ,Context.MODE_PRIVATE);


    }
    public void UserId(String Id) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_Id, Id);
        editor.commit();
        editor.apply();
    }

    public String  fetchUserId() {
        return sharedPreferences.getString(USER_Id,"");
    }

    public void UserLocation(String location) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOCATION_NAME, location);
        editor.commit();
        editor.apply();
    }

    public String  fetchUserLocation() {
        return sharedPreferences.getString(LOCATION_NAME,"");
    }


    public void UserLatitude(String userLatitude) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_LATITUDE, userLatitude);
        editor.commit();
        editor.apply();
    }

    public String fetchUserLatitude() {
        return sharedPreferences.getString(USER_LATITUDE,"0.0");
    }


    public void UserLongitude(String userLongitude) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_LONGITUDE, userLongitude);
        editor.apply();
    }
    public String fetchUserLongitude()
    {
        return sharedPreferences.getString(USER_LONGITUDE,"0.0");

    }

    public void StoredData(boolean islogin)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN, islogin);
        editor.apply();
    }


    public boolean fetchStoredData()

    {
        return  sharedPreferences.getBoolean(IS_LOGIN,false);
    }

}
