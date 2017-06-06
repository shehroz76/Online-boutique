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
import com.example.msk.onlinebotique.Pojo.SellerProductDetail;
import com.example.msk.onlinebotique.R;
import com.example.msk.onlinebotique.Utilities.KeyStore;
import com.example.msk.onlinebotique.Utilities.RecyclerViewEmptySupport;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by DELL on 5/13/2017.
 */

public class SellerHomePageFragment extends Fragment {

    private String ShopName = "";

    public SellerHomePageFragment() {
    }
    private List<SellerProductDetail> mshopProductList = new ArrayList<SellerProductDetail>();
    private DatabaseReference mSellerAddProductDatabaseReference;
    private DatabaseReference mSellerShopDatabaseReference;
    private KeyStore mKeyStore;
    private String Uuid ="";
    private DatabaseReference curentUserProducts;
    private SellerProductDetail sellerProductDetail;
    private ShopProductAdapter shopProductAdapter;
    private View emptyView;

    @BindView(R.id.ShopProductsRecyclerview)
    RecyclerViewEmptySupport mRecyclerView;

//    private List<ProductDetail>  mProductDetailList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.seller_home_page_frament,container,false);
        ButterKnife.bind(this,view);
        emptyView = view.findViewById(R.id.empty_view);
        mKeyStore = KeyStore.getInstance(getContext());

        mSellerAddProductDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Products");

        Uuid = mKeyStore.getString("User_UId");

        Query query = FirebaseDatabase.getInstance().getReference().child("Shop_Info").child(Uuid);



       ShopName= mKeyStore.getString("ShopName");

        if(ShopName.equals("")){

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Map<String, String> map = (Map)dataSnapshot.getValue();
                    if (map!=null) {
                        String shopName = map.get("shopName");
                        ShopName = shopName;
                        mKeyStore.putString("ShopName", ShopName);


                        String ProductNode = ShopName;

                        curentUserProducts = mSellerAddProductDatabaseReference.child(ProductNode);


                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                        shopProductAdapter = new ShopProductAdapter(mshopProductList);


                        populateProductList();

                        if(mshopProductList==null){
                            mRecyclerView.setEmptyView(emptyView);
                            emptyView.setVisibility(View.VISIBLE);
                        }else {

                            emptyView.setVisibility(View.GONE);
                            shopProductAdapter = new ShopProductAdapter(mshopProductList);
                            mRecyclerView.setAdapter(shopProductAdapter);
                        }


                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else
        {

            String ProductNode = ShopName;

            curentUserProducts = mSellerAddProductDatabaseReference.child(ProductNode);


            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
            shopProductAdapter = new ShopProductAdapter(mshopProductList);


            populateProductList();

            if(mshopProductList==null){
                mRecyclerView.setEmptyView(emptyView);
            }else {

                emptyView.setVisibility(View.GONE);
                shopProductAdapter = new ShopProductAdapter(mshopProductList);
                mRecyclerView.setAdapter(shopProductAdapter);
            }


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



    public void populateProductList(){

        curentUserProducts.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                sellerProductDetail = dataSnapshot.getValue(SellerProductDetail.class);
                mshopProductList.add(sellerProductDetail);

                shopProductAdapter.notifyItemInserted(mshopProductList.size()-1);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onStop() {
        super.onStop();
        mshopProductList.clear();
    }
}
