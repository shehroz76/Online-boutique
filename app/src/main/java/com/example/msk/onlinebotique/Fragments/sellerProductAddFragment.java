package com.example.msk.onlinebotique.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msk.onlinebotique.Pojo.ProductDetail;
import com.example.msk.onlinebotique.R;
import com.example.msk.onlinebotique.Utilities.KeyStore;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DELL on 5/19/2017.
 */

public class sellerProductAddFragment extends Fragment {

    public static List<ProductDetail>  mshopProductList;

    public sellerProductAddFragment() {
    }


    private KeyStore mkKeyStore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.seller_add_product,container,false);
        ButterKnife.bind(this,view);

        mkKeyStore = KeyStore.getInstance(getContext());

        return  view;

    }

    @OnClick(R.id.product_done)
    public void done(){

        populatedList();

    }

    private void populatedList(){
        mshopProductList = new ArrayList<ProductDetail>();
        for(int i=0;i<20;i++)
        {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setProduct_name("Item "+i);
//            shop.setItemImage("FName " + i);
                mshopProductList.add(productDetail);


        }
    }

}
