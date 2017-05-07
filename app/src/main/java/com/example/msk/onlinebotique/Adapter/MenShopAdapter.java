package com.example.msk.onlinebotique.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msk.onlinebotique.Pojo.Shop;
import com.example.msk.onlinebotique.R;

import java.util.List;

/**
 * Created by DELL on 5/7/2017.
 */

public class MenShopAdapter extends RecyclerView.Adapter<MenShopAdapter.MenShopViewHolder> {

    private List<Shop> mShop;

    public MenShopAdapter(List<Shop> mShop) {

        this.mShop = mShop;

    }

    @Override
    public MenShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_item,parent,false);
        return new MenShopViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MenShopViewHolder holder, int position) {


        Shop shop = mShop.get(position);
//        holder.imageViewItem.setImageURI(Uri.parse(shop.getItemImage()));
        holder.mtTextViewItem.setText(shop.getItemPrice());

    }

    @Override
    public int getItemCount() {
        return mShop.size();
    }


    public class MenShopViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewItem;
        private TextView mtTextViewItem;

        public MenShopViewHolder(View itemView) {
            super(itemView);

            imageViewItem = (ImageView) itemView.findViewById(R.id.imageItem);
            mtTextViewItem = (TextView) itemView.findViewById(R.id.textItem);

        }





    }


}
