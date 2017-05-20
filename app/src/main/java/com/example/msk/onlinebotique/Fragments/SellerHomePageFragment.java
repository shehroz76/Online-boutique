package com.example.msk.onlinebotique.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msk.onlinebotique.Adapter.ShopProductAdapter;
import com.example.msk.onlinebotique.Pojo.ProductDetail;
import com.example.msk.onlinebotique.R;
import com.example.msk.onlinebotique.Utilities.RecyclerViewEmptySupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.msk.onlinebotique.Fragments.sellerProductAddFragment.mshopProductList;

/**
 * Created by DELL on 5/13/2017.
 */

public class SellerHomePageFragment extends Fragment {

    public SellerHomePageFragment() {
    }

    @BindView(R.id.ShopProductsRecyclerview)
    RecyclerViewEmptySupport mRecyclerView;

//    private List<ProductDetail>  mProductDetailList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.seller_home_page_frament,container,false);
        ButterKnife.bind(this,view);
        View emptyView = view.findViewById(R.id.empty_view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));



        if(mshopProductList==null){
            mRecyclerView.setEmptyView(emptyView);
        }else {

            emptyView.setVisibility(View.GONE);
            ShopProductAdapter shopProductAdapter = new ShopProductAdapter(mshopProductList);
            mRecyclerView.setAdapter(shopProductAdapter);
        }



        return view;
    }

    @OnClick(R.id.add_shop_fab)
    public void AddShop(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, 0, 0, R.anim.slide_out_left);

        sellerProductAddFragment addProduct = new sellerProductAddFragment();
        ft.replace(R.id.home_container,addProduct,"abcfragment").addToBackStack(null);
        ft.commit();
    }

}
