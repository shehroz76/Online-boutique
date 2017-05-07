package com.example.msk.onlinebotique.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.msk.onlinebotique.Activities.LoginActivity;
import com.example.msk.onlinebotique.Activities.VerifyAcountActivity;
import com.example.msk.onlinebotique.Adapter.MenShopAdapter;
import com.example.msk.onlinebotique.Pojo.Shop;
import com.example.msk.onlinebotique.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MSk on 09/03/2017.
 */

public class HomeFragment extends android.support.v4.app.Fragment implements BaseSliderView.OnSliderClickListener ,ViewPagerEx.OnPageChangeListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG;
    private List<Shop> mShopItemList;

    @BindView(R.id.recyclerView_Men)
    RecyclerView mrecRecyclerViewMen;

    @BindView(R.id.slider)
    SliderLayout mImageSlider;


    public HomeFragment() {
        super();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,rootView);

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

        mrecRecyclerViewMen.setHasFixedSize(true);
        populatedList();
        mrecRecyclerViewMen.setLayoutManager(new GridLayoutManager(getActivity(),2));

        MenShopAdapter menShopAdapter = new MenShopAdapter(mShopItemList);
        mrecRecyclerViewMen.setAdapter(menShopAdapter);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null ) {

                    checkIfEmailVerified();

                    // User is signed in




                    Log.d(TAG, "onAuthSt" +
                            "ateChanged:signed_in:" + user.getUid());
//
//                    mProgress.setMessage("Updating..");
//                    mProgress.show();
//
//                    UserUid = mAuth.getCurrentUser().getUid();
//                    DatabaseReference mref=mDatabaseReferenceUserInfo.child(UserUid);
//                    mref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                            Map<String, String> map = (Map)dataSnapshot.getValue();
//                            String name = map.get("Name");
//                            String batch =map.get("Batch");
//                            photoUrl =map.get("Profile_Image");
//                            String Username= map.get("User_Name");
//
//                            if (!TextUtils.isEmpty(name)){
//                                mProfileUserNameMain.setText(name);}
//                            else
//                                mProfileUserNameMain.setText("User Name");
//
//                            if (!TextUtils.isEmpty(batch)) {
//                                mProfileUserBatchMain.setText(batch);
//                            }
//                            else
//                                mProfileUserBatchMain.setText("Batch No : -- -- --");
//
//                            if(!TextUtils.isEmpty(photoUrl)){
//
//                                Picasso.with(MainActivity.this).load(photoUrl).into(mProfileImageMain);
//
//                                Picasso.with(MainActivity.this).load(photoUrl).into(mCircleImageViewNavheaderPic);
//                            }else
//                                Picasso.with(MainActivity.this).load(R.drawable.profile_placeholder).into(mProfileImageMain);
//
//                            if(!TextUtils.isEmpty(Username)){
//
//                                mTextViewUserNameNavHead.setText(Username);
//
//                            }else
//                                mTextViewUserNameNavHead.setText("User Name");
//
//
//                            mProgress.dismiss();
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });






                } else {
                    // User is signed out

                    Intent loginActivityIntent = new Intent(getActivity() , LoginActivity.class);
                    loginActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginActivityIntent);

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };




        return rootView;
    }



    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }











    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.



            Snackbar snackbar = Snackbar
                    .make(getView(), "Successfully Login", Snackbar.LENGTH_SHORT);

            snackbar.show();




        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.

            Snackbar snackbar = Snackbar
                    .make(getView(), "Please Verify Your Email", Snackbar.LENGTH_SHORT);

            snackbar.show();

            FirebaseAuth.getInstance().signOut();

            //restart this activity

        }




    }

    private void populatedList() {
        mShopItemList = new ArrayList<Shop>();
        for(int i=0;i<20;i++)
        {
            Shop shop = new Shop();
            shop.setItemPrice("$ "+i);
//            shop.setItemImage("FName " + i);
            mShopItemList.add(shop);


        }
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
