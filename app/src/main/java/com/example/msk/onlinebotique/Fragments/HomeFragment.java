package com.example.msk.onlinebotique.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msk.onlinebotique.Activities.LoginActivity;
import com.example.msk.onlinebotique.Activities.VerifyAcountActivity;
import com.example.msk.onlinebotique.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MSk on 09/03/2017.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG;

    @BindView(R.id.isEmailVerified)
    TextView isEmailVerified;


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



        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null ) {

                    checkIfEmailVerified();

                    // User is signed in




                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
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

     @OnClick(R.id.signOut)
    public void Signout() {
        FirebaseAuth.getInstance().signOut();
    }










    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.

            isEmailVerified.setTextColor(getResources().getColor(R.color.colorGreen));

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









}
