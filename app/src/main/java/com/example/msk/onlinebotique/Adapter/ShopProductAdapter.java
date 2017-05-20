package com.example.msk.onlinebotique.Adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msk.onlinebotique.Fragments.ProductDetailFragment;
import com.example.msk.onlinebotique.Fragments.SellerProductDetailFragment;
import com.example.msk.onlinebotique.Pojo.ProductDetail;
import com.example.msk.onlinebotique.R;

import java.util.List;

/**
 * Created by DELL on 5/19/2017.
 */

public class ShopProductAdapter extends RecyclerView.Adapter<ShopProductAdapter.ShopProductViewholder> {


    private List<ProductDetail> mProductDetailslist;

    public ShopProductAdapter(List<ProductDetail> mProductDetailslist) {
        this.mProductDetailslist = mProductDetailslist;
    }

    @Override
    public ShopProductViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_product_items,parent,false);
        return  new ShopProductViewholder(view);

    }

    @Override
    public void onBindViewHolder(ShopProductViewholder holder, int position) {

        ProductDetail productDetail = mProductDetailslist.get(position);

    }

    @Override
    public int getItemCount() {
        return mProductDetailslist.size();
    }

    public class ShopProductViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mproductImage;
        private TextView mproductPrice;

        public ShopProductViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mproductImage = (ImageView) itemView.findViewById(R.id.sellerProductImage);
            mproductPrice = (TextView) itemView.findViewById(R.id.sellerproductPrice);
        }

        @Override
        public void onClick(View v) {

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            SellerProductDetailFragment myFragment = new SellerProductDetailFragment();
            //Create a bundle to pass data, add data, set the bundle to your fragment and:
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.home_container, myFragment).addToBackStack(null).commit();


        }
    }

}
