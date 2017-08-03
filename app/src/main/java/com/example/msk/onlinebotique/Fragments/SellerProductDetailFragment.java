package com.example.msk.onlinebotique.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msk.onlinebotique.Adapter.CommentsAdapter;
import com.example.msk.onlinebotique.Adapter.orderAdapter;
import com.example.msk.onlinebotique.Pojo.Comments;
import com.example.msk.onlinebotique.Pojo.SellerProductDetail;
import com.example.msk.onlinebotique.Pojo.orders;
import com.example.msk.onlinebotique.R;
import com.example.msk.onlinebotique.Utilities.KeyStore;
import com.example.msk.onlinebotique.Utilities.Keys;
import com.example.msk.onlinebotique.Utilities.RecyclerViewEmptySupport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DELL on 5/20/2017.
 */

public class SellerProductDetailFragment extends Fragment {

    private String ProductKey;
    private List<SellerProductDetail> mSellerProductDetailslist;
    private String name;
    private KeyStore mKeyStore;
    private SellerProductDetail productDetail;
    private String mainImage;
    private DatabaseReference mSellerAddProductDatabaseReference;
    private DatabaseReference curentUserProductDetail;
    private String ShopName;
    private boolean isShowOrder=false;
    private boolean isShowComments=false;

    public SellerProductDetailFragment() {
    }

    private List<Comments> mComments;
    private List<orders> mOrders;


    @BindView(R.id.ordersRecylerView)
    RecyclerViewEmptySupport recyclerOrders;

    @BindView(R.id.commentsRecylerView)
    RecyclerViewEmptySupport recyclerComments;

    @BindView(R.id.product_item_name)
    TextView productName;

    @BindView(R.id.upload_product_pic)
    ImageView mImageMain;

    @BindView(R.id.orders)
    ImageView mshowOrders;

    @BindView(R.id.commets)
    ImageView mshowComments;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.seller_product_status,container,false);

        ButterKnife.bind(this,view);

        mKeyStore = KeyStore.getInstance(getContext());

        mSellerAddProductDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Products");

        ShopName= mKeyStore.getString("ShopName");

        String ProductNode = ShopName;


        Bundle arguments = getArguments();


        if (getArguments() != null) {
            ProductKey = arguments.getString(Keys.ProductDetailKey);
            Toast.makeText(getActivity(),ProductKey,Toast.LENGTH_SHORT).show();
        }

        curentUserProductDetail = mSellerAddProductDatabaseReference.child(ProductNode).child(ProductKey);

        curentUserProductDetail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                Map<String, String> map = (Map)dataSnapshot.getValue();
                name = map.get("Product_Name");

                mainImage =map.get("Image1");

                productName.setText(name);
                Picasso.with(getActivity()).load(mainImage).into(mImageMain);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        //comments recylerview
        recyclerComments.setHasFixedSize(true);
        populateListView();
        recyclerComments.setLayoutManager(new LinearLayoutManager(getActivity()));
        CommentsAdapter commentsAdapter = new CommentsAdapter(mComments);
        recyclerComments.setAdapter(commentsAdapter);


        //orders recylerview
        recyclerOrders.setHasFixedSize(true);
        populateOrderListView();
        recyclerOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderAdapter orderAdapter = new orderAdapter(mOrders);
        recyclerOrders.setAdapter(orderAdapter);


        return view;
    }


    @OnClick(R.id.orders)
    public void showOrders(){

        if(isShowOrder==false) {

            mshowOrders.setImageResource(R.mipmap.up_button);
            recyclerOrders.setVisibility(View.VISIBLE);
            isShowOrder=true;

        }else if(isShowOrder==true){

            mshowOrders.setImageResource(R.mipmap.drop_downarrow);
        recyclerOrders.setVisibility(View.GONE);
        isShowOrder=false;}

    }

    @OnClick(R.id.commets)
    public void showComments(){

        if(isShowComments==false) {

            mshowComments.setImageResource(R.mipmap.up_button);
            recyclerComments.setVisibility(View.VISIBLE);
            isShowComments=true;

        }else if(isShowComments==true){

            mshowComments.setImageResource(R.mipmap.drop_downarrow);
            recyclerComments.setVisibility(View.GONE);
            isShowComments=false;

        }
    }


    private void populateListView() {

        mComments = new ArrayList<Comments>();
        for(int i=0;i<20;i++){

            Comments comments = new Comments();
            comments.setComments("Black Shirt");
            mComments.add(comments);
        }
    }


    private void populateOrderListView() {

        mOrders = new ArrayList<orders>();
        for(int i=0;i<20;i++){

            orders orders = new orders();
            orders.setName("Black Shirt");
            mOrders.add(orders);
        }
    }




}
