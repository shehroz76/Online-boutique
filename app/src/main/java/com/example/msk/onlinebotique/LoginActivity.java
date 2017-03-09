package com.example.msk.onlinebotique;

import android.content.Intent;
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

        if (savedInstanceState == null) {

            // Instance of first fragment
            PlaceHolderLoginFragment placeHolderLoginFragment = new PlaceHolderLoginFragment();


            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft.add(R.id.container, placeHolderLoginFragment);                                // add    Fragment
            ft.commit();
        }


    }

//    @Override
//    public void onBackPressed() {
//
////        int count = getFragmentManager().getBackStackEntryCount();
////        if (count == 0) {
//            Intent startMain = new Intent(Intent.ACTION_MAIN);
//            startMain.addCategory(Intent.CATEGORY_HOME);
//            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(startMain);
//            super.onBackPressed();
//
////        } else if(count==1) {
////
////            super.onBackPressed();}
////        else
////            getFragmentManager().popBackStack();
//////            additional code
////
////        }
//
//    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();



        else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            super.onBackPressed();
            finish();
        }

    }
}