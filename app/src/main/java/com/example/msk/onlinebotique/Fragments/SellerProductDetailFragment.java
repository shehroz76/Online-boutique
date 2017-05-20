package com.example.msk.onlinebotique.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msk.onlinebotique.Adapter.CommentsAdapter;
import com.example.msk.onlinebotique.Adapter.orderAdapter;
import com.example.msk.onlinebotique.Pojo.Comments;
import com.example.msk.onlinebotique.Pojo.orders;
import com.example.msk.onlinebotique.R;
import com.example.msk.onlinebotique.Utilities.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DELL on 5/20/2017.
 */

public class SellerProductDetailFragment extends Fragment {

    public SellerProductDetailFragment() {
    }

    private List<Comments> mComments;
    private List<orders> mOrders;


    @BindView(R.id.ordersRecylerView)
    RecyclerViewEmptySupport recyclerOrders;

    @BindView(R.id.commentsRecylerView)
    RecyclerViewEmptySupport recyclerComments;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.seller_product_status,container,false);

        ButterKnife.bind(this,view);

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
