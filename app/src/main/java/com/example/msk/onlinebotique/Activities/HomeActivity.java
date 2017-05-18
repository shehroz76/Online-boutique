package com.example.msk.onlinebotique.Activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.msk.onlinebotique.Fragments.BuyerHomePageFragment;
import com.example.msk.onlinebotique.Fragments.SellerHomePageFragment;
import com.example.msk.onlinebotique.Pojo.User;
import com.example.msk.onlinebotique.R;

import com.example.msk.onlinebotique.Utilities.KeyStore;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;


public class HomeActivity extends AppCompatActivity {


    private DatabaseReference mUserInfoDatabaseReference;
    private String UserUid = "";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private KeyStore mKeyStore;
    private Context context;

    private String mName,mEmail;
    private String TAG = "Main";
    private String Category = "";
    private boolean isEmailVerifies = false;
    private ProgressDialog progress;
//    ProgressDialog progress;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();


        progress = new ProgressDialog(this, R.layout.custome_progressbar_in_center);
        progress.getWindow().setGravity(Gravity.CENTER);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);



        mKeyStore = KeyStore.getInstance(getApplicationContext());



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null ) {

                   Boolean check = mKeyStore.getBoolean("isEmailVeridied");

                    if(check==false) {


                        isEmailVerifies = checkIfEmailVerified();

                        if (isEmailVerifies == true) {


                            UserUid = mAuth.getCurrentUser().getUid();
                            mUserInfoDatabaseReference = FirebaseDatabase.getInstance().getReference().child("USers_Info").child(UserUid);


                            mUserInfoDatabaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {


                                    User user = dataSnapshot.getValue(User.class);


                                    mKeyStore.putString("Name", user.getName());
                                    mKeyStore.putString("Email", user.getEmail());
                                    mKeyStore.putString("Category", user.getCategory());
                                    mKeyStore.putString("Country", user.getCountry());
                                    mKeyStore.putString("City", user.getCity());
                                    mKeyStore.putString("Profile_pic", user.getProfile_Pic());
                                    mKeyStore.putString("User_id", user.getUser_Id());
                                    mKeyStore.putString("User_UId", user.getUser_UId());
                                    mKeyStore.putString("password", user.getUser_Pass());
                                    mKeyStore.putBoolean("isEmailVeridied", isEmailVerifies);

                                    Category = user.getCategory();


                                    // Adding a Fragment

                                    if (savedInstanceState == null) {

                                        if (Category.equals("Buyer")) {

                                            // Instance of first fragment
                                            BuyerHomePageFragment homeFragment = new BuyerHomePageFragment();
                                            // Add Fragment to FrameLayout (flContainer), using FragmentManager
                                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                            ft.add(R.id.home_container, homeFragment);
                                            ft.commit();
                                            progress.dismiss();


                                        } else if (Category.equals("Seller")) {

                                            // Instance of first fragment
                                            SellerHomePageFragment sellerhomeFragment = new SellerHomePageFragment();
                                            // Add Fragment to FrameLayout (flContainer), using FragmentManager
                                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                            ft.add(R.id.home_container, sellerhomeFragment);
                                            ft.commit();
                                            progress.dismiss();

                                            Intent intent =new Intent(HomeActivity.this,SellerIntroActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);

                                        }


                                    }

                                }


                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                            // User is signed in


                        }
                    }
                    Log.d(TAG, "onAuthSt" +
                            "ateChanged:signed_in:" + user.getUid());






                } else {
                    // User is signed out

                    Intent loginActivityIntent = new Intent(HomeActivity.this , LoginActivity.class);
                    loginActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginActivityIntent);

                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };



        Category = mKeyStore.getString("Category");

        if(savedInstanceState==null){

            if(Category.equals("")){

                progress.show();

            }else if(Category.equals("Buyer")){

                // Instance of first fragment
                BuyerHomePageFragment homeFragment = new BuyerHomePageFragment();
                // Add Fragment to FrameLayout (flContainer), using FragmentManager
                FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                ft.add(R.id.home_container,homeFragment);
                ft.commit();
                progress.dismiss();


            }else if (Category.equals("Seller")){

                // Instance of first fragment
                SellerHomePageFragment sellerhomeFragment = new SellerHomePageFragment();
                // Add Fragment to FrameLayout (flContainer), using FragmentManager
                FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                ft.add(R.id.home_container,sellerhomeFragment);
                ft.commit();
                progress.dismiss();


                Intent intent =new Intent(HomeActivity.this,SellerIntroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);



            }


        }






    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private boolean checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.
            return true;
        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            FirebaseAuth.getInstance().signOut();
            return false;

            //restart this activity

        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                // About option clicked.
//                mKeyStore = KeyStore.getInstance(getApplicationContext());
                mKeyStore.clear();
                FirebaseAuth.getInstance().signOut();



                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


