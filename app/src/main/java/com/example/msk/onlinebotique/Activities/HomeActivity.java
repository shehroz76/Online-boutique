package com.example.msk.onlinebotique.Activities;


import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.msk.onlinebotique.Fragments.ChooseMenWomenFragmnet;
import com.example.msk.onlinebotique.R;
import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Adding a Fragment
        if(savedInstanceState==null){

            // Instance of first fragment
            ChooseMenWomenFragmnet homeFragment = new ChooseMenWomenFragmnet();
            // Add Fragment to FrameLayout (flContainer), using FragmentManager
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.add(R.id.home_container,homeFragment);
            ft.commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                // About option clicked.
                FirebaseAuth.getInstance().signOut();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


