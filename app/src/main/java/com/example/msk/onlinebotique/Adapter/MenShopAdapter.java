package com.example.msk.onlinebotique.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msk.onlinebotique.Fragments.ProductDetailFragment;
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


    public class MenShopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageViewItem;
        private TextView mtTextViewItem;

        public MenShopViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            imageViewItem = (ImageView) itemView.findViewById(R.id.imageItem);
            mtTextViewItem = (TextView) itemView.findViewById(R.id.textItem);

        }


        @Override
        public void onClick(View v) {

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            ProductDetailFragment myFragment = new ProductDetailFragment();
            //Create a bundle to pass data, add data, set the bundle to your fragment and:
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.home_container, myFragment).addToBackStack(null).commit();

        }
    }


}
