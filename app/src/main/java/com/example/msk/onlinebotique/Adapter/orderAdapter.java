package com.example.msk.onlinebotique.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.msk.onlinebotique.Pojo.orders;
import com.example.msk.onlinebotique.R;

import java.util.List;

/**
 * Created by DELL on 5/20/2017.
 */

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.ordersViewHolder> {

private List<orders> mOrders;



    public orderAdapter(List<orders> mOrders) {

        this.mOrders = mOrders;

        }

@Override
public ordersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_list_item,parent,false);
        return new ordersViewHolder(itemView);

        }

@Override
public void onBindViewHolder(ordersViewHolder holder, int position) {


        orders orders = mOrders.get(position);

        }

@Override
public int getItemCount() {
        return mOrders.size();
        }


public class ordersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



    public ordersViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
//
//        AppCompatActivity activity = (AppCompatActivity) v.getContext();
//        ProductDetailFragment myFragment = new ProductDetailFragment();
//        //Create a bundle to pass data, add data, set the bundle to your fragment and:
//        activity.getSupportFragmentManager().beginTransaction().replace(R.id.home_container, myFragment).addToBackStack(null).commit();

    }
}


}
