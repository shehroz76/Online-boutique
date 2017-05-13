package com.example.msk.onlinebotique.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DELL on 5/13/2017.
 */

public class KeyStore {

    private static KeyStore skKeyStore;
    private SharedPreferences sharedPreferences;
    private static String fileName = "keys";

    private KeyStore(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(fileName,0);
    }

    public static KeyStore getInstance(Context context){

        if(skKeyStore==null){

            skKeyStore = new KeyStore(context);
        }

        return skKeyStore;
    }

    public void putString(String  key,String value){

        SharedPreferences.Editor editor;
        editor =  sharedPreferences.edit();

        editor.putString(key,value);
        editor.commit();
    }

    public String getString(String key){

        return sharedPreferences.getString(key,null);

    }


    public void putInt(String key, int num) {//Log.v("Keystore","PUT INT "+key+" "+String.valueOf(num));
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();

        editor.putInt(key, num);
        editor.commit();
    }

    public int getInt(String key) {//Log.v("Keystore","GET INT from "+key);
        return sharedPreferences.getInt(key, 0);
    }


    public void clear(){
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();

        editor.clear();
        editor.commit();
    }

    public void remove(){
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();

        editor.remove(fileName);
        editor.commit();
    }




}
