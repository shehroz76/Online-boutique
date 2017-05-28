package com.example.msk.onlinebotique.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msk.onlinebotique.Pojo.Comments;
import com.example.msk.onlinebotique.R;

import java.util.List;

/**
 * Created by DELL on 5/20/2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private List<Comments> mComments;



    public CommentsAdapter(List<Comments> mComments) {

        this.mComments = mComments;

    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_item,parent,false);
        return new CommentsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {


       Comments comments = mComments.get(position);

    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }


    public class CommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        public CommentsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {
//
//        AppCompatActivity activity = (AppCompatActivity) v.getContext();
//        BuyerProductDetailFragment myFragment = new BuyerProductDetailFragment();
//        //Create a bundle to pass data, add data, set the bundle to your fragment and:
//        activity.getSupportFragmentManager().beginTransaction().replace(R.id.home_container, myFragment).addToBackStack(null).commit();

        }
    }


}
