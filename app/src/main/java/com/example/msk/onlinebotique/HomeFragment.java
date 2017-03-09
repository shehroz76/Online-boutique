package com.example.msk.onlinebotique;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MSk on 09/03/2017.
 */

public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG;

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



        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
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

        ButterKnife.bind(this,rootView);

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



}
