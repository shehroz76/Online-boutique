package com.example.msk.onlinebotique.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msk.onlinebotique.Activities.LoginActivity;
import com.example.msk.onlinebotique.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DELL on 5/8/2017.
 */

public class BuyerHomePageFragment extends Fragment {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "abc";


    public BuyerHomePageFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.buyer_home_page_fragment,container,false);
        ButterKnife.bind(this,view);

        mAuth = FirebaseAuth.getInstance();


        return view;
    }

    @OnClick(R.id.Men_show_all)
    public void MenFragment(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);

        MenCategeroyFragment newFragment = new MenCategeroyFragment();

        ft.replace(R.id.home_container, newFragment, "detailFragment").addToBackStack(null);

// Start the animated transition.
        ft.commit();

    }

    @OnClick(R.id.Women_show_all)
    public void WomenFragment(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);

        MenCategeroyFragment menCategeroyFragment = new MenCategeroyFragment();
        ft.replace(R.id.home_container,menCategeroyFragment,"abcfragment").addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }





}
