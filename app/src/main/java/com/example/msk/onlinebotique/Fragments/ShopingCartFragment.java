package com.example.msk.onlinebotique.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msk.onlinebotique.Adapter.CurrentShopingCartAdapter;
import com.example.msk.onlinebotique.Pojo.ProductDetail;
import com.example.msk.onlinebotique.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 5/9/2017.
 */

public class ShopingCartFragment extends Fragment {


    List<ProductDetail> mProductDetailsList;

    @BindView(R.id.product_item_recylView)
    RecyclerView mRecyclerView;

    public ShopingCartFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shoping_cart_fragment,container,false);
        ButterKnife.bind(this,view);

        mRecyclerView.setHasFixedSize(true);
        populateListView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CurrentShopingCartAdapter currentShopingCartAdapter = new CurrentShopingCartAdapter(mProductDetailsList);
        mRecyclerView.setAdapter(currentShopingCartAdapter);




        return view;
    }

    private void populateListView() {

        mProductDetailsList = new ArrayList<ProductDetail>();
        for(int i=0;i<20l;i++){

            ProductDetail productDetail = new ProductDetail();
            productDetail.setProduct_name("Black Shirt");
            productDetail.setProduct_category("Shirt");
            mProductDetailsList.add(productDetail);
        }


    }
}
