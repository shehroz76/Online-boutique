package com.example.msk.onlinebotique.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msk.onlinebotique.Pojo.ProductDetail;
import com.example.msk.onlinebotique.R;
import com.google.android.gms.vision.text.Text;

import java.util.List;

/**
 * Created by DELL on 5/8/2017.
 */

public class CurrentShopingCartAdapter  extends RecyclerView.Adapter<CurrentShopingCartAdapter.currentShopingViewholder>{

    private List<ProductDetail> mProductDetails;

    public CurrentShopingCartAdapter(List<ProductDetail> mProductDetails) {
        this.mProductDetails = mProductDetails;

    }

    @Override
    public currentShopingViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_shoping_card_item,parent,false);
        return  new currentShopingViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(currentShopingViewholder holder, int position) {

        ProductDetail productDetail = mProductDetails.get(position);
        holder.mproductName.setText(productDetail.getProduct_name());
        holder.mproductcategory.setText(productDetail.getProduct_category());

    }

    @Override
    public int getItemCount() {
        return mProductDetails.size();
    }

    public class currentShopingViewholder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mproductName;
        private TextView mproductcategory;
        private TextView mproductPrice;
        private TextView mproductTotalPrice;
        private TextView mProductQuantity;

        public currentShopingViewholder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.product_Image);
            mproductName = (TextView) itemView.findViewById(R.id.product_name);
            mproductcategory = (TextView) itemView.findViewById(R.id.product_category);
            mproductPrice = (TextView) itemView.findViewById(R.id.product_price);
            mproductTotalPrice = (TextView) itemView.findViewById(R.id.product_total_price);
            mProductQuantity = (TextView) itemView.findViewById(R.id.product_quantity);


        }
    }
}
