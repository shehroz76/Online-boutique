package com.example.msk.onlinebotique.Adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msk.onlinebotique.Fragments.BuyerShopDetailFragment;
import com.example.msk.onlinebotique.Pojo.Shop;
import com.example.msk.onlinebotique.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 5/7/2017.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private List<Shop> mShop;
    private ImageView mshopImageViewItem;
    private CircleImageView mshopOwnerImage;
    private TextView mshopCategory;
    private TextView mshopName;
    private Context mContext;

    public ShopAdapter(List<Shop> mShop, Context mContext) {

        this.mShop = mShop;
        this.mContext = mContext;

    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_item,parent,false);
        return new ShopViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {


        Shop shop = mShop.get(position);


        mshopName.setText(shop.getShopName());
        Picasso.with(mContext).load(shop.getShopImage()).into(mshopImageViewItem);
        mshopCategory.setText(shop.getShopCategory());

    }

    @Override
    public int getItemCount() {
        return mShop.size();
    }


    public class ShopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public ShopViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mshopImageViewItem = (ImageView) itemView.findViewById(R.id.Shopimage);
            mshopName = (TextView) itemView.findViewById(R.id.shop_name);
            mshopCategory = (TextView) itemView.findViewById(R.id.shop_category_name);
            mshopOwnerImage = (CircleImageView) itemView.findViewById(R.id.shop_owner_Image);

        }


        @Override
        public void onClick(View v) {

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            BuyerShopDetailFragment myFragment = new BuyerShopDetailFragment();
            //Create a bundle to pass data, add data, set the bundle to your fragment and:
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.home_container, myFragment).addToBackStack(null).commit();

        }
    }


}
