package com.example.msk.onlinebotique;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by MSk on 14/02/2017.
 */

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Adding a Fragment

        if(savedInstanceState==null){

            // Instance of first fragment
            PlaceHolderLoginFragment placeHolderLoginFragment = new PlaceHolderLoginFragment();


            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft.add(R.id.container, placeHolderLoginFragment);                                // add    Fragment
            ft.commit();
        }




    }




}
