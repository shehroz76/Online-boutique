package com.example.msk.onlinebotique;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by MSk on 09/03/2017.
 */

public class SetupActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);


        // Adding a Fragment

        if (savedInstanceState == null) {

            // Instance of first fragment
            SetupFragment setupFragmentFragment = new SetupFragment();


            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft.add(R.id.setupContainer, setupFragmentFragment);                                // add    Fragment
            ft.commit();
        }
    }
}
