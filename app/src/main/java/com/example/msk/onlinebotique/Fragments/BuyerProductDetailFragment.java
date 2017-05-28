package com.example.msk.onlinebotique.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.msk.onlinebotique.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DELL on 5/8/2017.
 */

public class BuyerProductDetailFragment extends Fragment {

    public BuyerProductDetailFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_detail_fragment,container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    @OnClick(R.id.Add_to_cart)
    public void addToCart(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);

        ShopingCartFragment shopingCartFragment = new ShopingCartFragment();
        ft.replace(R.id.home_container,shopingCartFragment,"shopingcartfragment").addToBackStack(null);
        ft.commit();


    }

}
