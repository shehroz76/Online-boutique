package com.example.msk.onlinebotique.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msk.onlinebotique.Fragments.SellerProductDetailFragment;
import com.example.msk.onlinebotique.Pojo.ProductDetail;
import com.example.msk.onlinebotique.Pojo.SellerProductDetail;
import com.example.msk.onlinebotique.R;
import com.example.msk.onlinebotique.Utilities.Keys;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DELL on 5/19/2017.
 */

public class ShopProductAdapter extends RecyclerView.Adapter<ShopProductAdapter.ShopProductViewholder> {


    private List<SellerProductDetail> mProductDetailslist;
    private Context mContext;

    private String price = "";
    private String image = "";
    private String key = "";
    private ImageView mproductImage;
    private TextView mproductPrice;
    private SellerProductDetail productDetail;


    public ShopProductAdapter(List<SellerProductDetail> mProductDetailslist , Context mContext) {
        this.mProductDetailslist = mProductDetailslist;
        this.mContext = mContext;
    }

    @Override
    public ShopProductViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_product_items,parent,false);
        return  new ShopProductViewholder(view);

    }

    @Override
    public void onBindViewHolder(ShopProductViewholder holder, int position) {

        productDetail = mProductDetailslist.get(position);
        price = productDetail.getProduct_price();
        image = productDetail.getImage1();

        mproductPrice.setText(price);
        Picasso.with(mContext).load(image).into(mproductImage);


    }

    @Override
    public int getItemCount() {
        return mProductDetailslist.size();
    }

    public class ShopProductViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ShopProductViewholder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mproductImage = (ImageView) itemView.findViewById(R.id.sellerProductImage);
            mproductPrice = (TextView) itemView.findViewById(R.id.sellerproductPrice);


        }

        @Override
        public void onClick(View v) {


            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            int itemPosition = getAdapterPosition();
            SellerProductDetailFragment myFragment = new SellerProductDetailFragment();


            productDetail = mProductDetailslist.get(itemPosition);
            key = productDetail.getKey();

            Bundle arguments = new Bundle();
            arguments.putString(Keys.ProductDetailKey, key);
            myFragment.setArguments(arguments);

            //Create a bundle to pass data, add data, set the bundle to your fragment and:
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.home_container, myFragment).addToBackStack(null).commit();


        }
    }

}
