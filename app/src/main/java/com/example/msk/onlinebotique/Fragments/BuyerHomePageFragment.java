package com.example.msk.onlinebotique.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.msk.onlinebotique.Adapter.ShopAdapter;
import com.example.msk.onlinebotique.Pojo.Shop;
import com.example.msk.onlinebotique.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MSk on 09/03/2017.
 */

public class BuyerHomePageFragment extends android.support.v4.app.Fragment implements BaseSliderView.OnSliderClickListener ,
        ViewPagerEx.OnPageChangeListener
{


    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseShopNodeReference;
    private Query mDatabaseSHopQuery;
    private ShopAdapter shopAdapter;

    private String TAG;
    private List<Shop>  mShopItemList = new ArrayList<Shop>();
    private Shop mShop;

    @BindView(R.id.recyclerView_Shop)
    RecyclerView mrecRecyclerViewShop;

    @BindView(R.id.slider)
    SliderLayout mImageSlider;
    private String keysaved = "" ;


    public BuyerHomePageFragment() {
        super();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_buyer_home,container,false);
        ButterKnife.bind(this,rootView);

        mDatabaseShopNodeReference = FirebaseDatabase.getInstance().getReference().child("Shops");


        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.drawable.hannibal);
        file_maps.put("Big Bang Theory",R.drawable.bigbang);
        file_maps.put("House of Cards",R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mImageSlider.addSlider(textSliderView);
        }

        mImageSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mImageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mImageSlider.setCustomAnimation(new DescriptionAnimation());
        mImageSlider.setDuration(4000);
        mImageSlider.addOnPageChangeListener(this);

        mrecRecyclerViewShop.setHasFixedSize(true);
        mrecRecyclerViewShop.setLayoutManager(new GridLayoutManager(getActivity(),2));
        populateShopList();
        shopAdapter = new ShopAdapter(mShopItemList, getContext());
        mrecRecyclerViewShop.setAdapter(shopAdapter);


        return rootView;
    }


    @Override
    public void onStop() {
        super.onStop();
    }



    public void populateShopList() {

        mDatabaseShopNodeReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String key = dataSnapshot.getKey();


                if(key != keysaved) {

                    mShop = dataSnapshot.getValue(Shop.class);

                    mShop.setKey(key);

                    mShopItemList.add(mShop);

//                    shopAdapter.notifyItemInserted(mShopItemList.size() - 1);
                    shopAdapter.notifyDataSetChanged();
                    keysaved = mShopItemList.get(0).getKey();
                }
                else if(key ==keysaved){



                    mShop = dataSnapshot.getValue(Shop.class);

                    mShop.setKey(key);
                    mShopItemList.remove(0);
                    shopAdapter.notifyItemRemoved(0);
                    mShopItemList.add(mShop);
                    shopAdapter.notifyItemInserted(mShopItemList.size() - 1);



                }




            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(),slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
