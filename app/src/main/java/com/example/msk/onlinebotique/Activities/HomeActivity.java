package com.example.msk.onlinebotique.Activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;


public class HomeActivity extends AppCompatActivity {


    private DatabaseReference mUserInfoDatabaseReference;
    private String UserUid = "";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private KeyStore mKeyStore;
    private Context context;
    Toolbar toolbar;

    private String mName,mEmail;
    private String TAG = "Main";
    private String Category = "";
    private Boolean isShopCreated = false;
    private boolean isEmailVerifies = false;
    private ProgressDialog progress;
//    ProgressDialog progress;


    //save our header or result
    private AccountHeader headerResult = null;
    public static Drawer result = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Handle Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Seller");

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
                                    String isShopOpened = user.getIsShopOpened();
                                    if(isShopOpened.equals("true")){
                                        isShopCreated = true;
                                    }
                                    mKeyStore.putBoolean("isShopCreated",isShopCreated);



                                    // Adding a Fragment

                                    if (savedInstanceState == null) {

                                        if (Category.equals("Buyer")) {

                                            // Instance of first fragment
                                            BuyerHomePageFragment homeFragment = new BuyerHomePageFragment();
                                            // Add Fragment to FrameLayout (flContainer), using FragmentManager
                                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                            ft.add(R.id.home_container, homeFragment);
                                            ft.commit();


                                            // add buyer nav-drawer

                                            // Create a few sample profile
                                            // NOTE you have to define the loader logic too. See the CustomApplication for more details
                                            final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
                                            final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));

                                            // Create the AccountHeader
                                            headerResult = new AccountHeaderBuilder()
                                                    .withActivity(HomeActivity.this)
                                                    .withHeaderBackground(R.drawable.header)
                                                    .addProfiles(
                                                            profile, profile2
                                                    )
                                                    .withSavedInstance(savedInstanceState)
                                                    .build();

                                            //Create the drawer
                                            result = new DrawerBuilder()
                                                    .withActivity(HomeActivity.this)
                                                    .withToolbar(toolbar)
                                                    .withHasStableIds(true)
                                                    .withDrawerLayout(R.layout.crossfade_drawer)
                                                    .withDrawerWidthDp(72)
                                                    .withGenerateMiniDrawer(true)
                                                    .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                                                    .addDrawerItems(
                                                            new PrimaryDrawerItem().withName("Orders").withIcon(FontAwesome.Icon.faw_home).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false),
                                                            new PrimaryDrawerItem().withName("Favourites").withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
                                                            new PrimaryDrawerItem().withName("Messages").withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4),
                                                            new PrimaryDrawerItem().withDescription("3 items").withName("draft").withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5),
                                                            new SecondaryDrawerItem().withName("Fb Page").withIcon(GoogleMaterial.Icon.gmd_facebook).withTag("Bullhorn")
                                                    ) // add the items we want to use with our Drawer
                                                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                                        @Override
                                                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                                            if (drawerItem instanceof Nameable) {
                                                                Toast.makeText(HomeActivity.this, ((Nameable) drawerItem).getName().getText(HomeActivity.this), Toast.LENGTH_SHORT).show();
                                                            }
                                                            //we do not consume the event and want the Drawer to continue with the event chain
                                                            return false;
                                                        }
                                                    })
                                                    .withSavedInstance(savedInstanceState)
                                                    .withShowDrawerOnFirstLaunch(true)
                                                    .build();

                                            //get the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
                                            //the CrossfadeDrawerLayout library can be found here: https://github.com/mikepenz/CrossfadeDrawerLayout
                                            crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();

                                            //define maxDrawerWidth
                                            crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(HomeActivity.this));
                                            //add second view (which is the miniDrawer)
                                            final MiniDrawer miniResult = result.getMiniDrawer();
                                            //build the view for the MiniDrawer
                                            View view = miniResult.build(HomeActivity.this);
                                            //set the background of the MiniDrawer as this would be transparent
                                            view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(HomeActivity.this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
                                            //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
                                            crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                                            //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                                            miniResult.withCrossFader(new ICrossfader() {
                                                @Override
                                                public void crossfade() {
                                                    boolean isFaded = isCrossfaded();
                                                    crossfadeDrawerLayout.crossfade(400);

                                                    //only close the drawer if we were already faded and want to close it now
                                                    if (isFaded) {
                                                        result.getDrawerLayout().closeDrawer(GravityCompat.START);
                                                    }
                                                }

                                                @Override
                                                public boolean isCrossfaded() {
                                                    return crossfadeDrawerLayout.isCrossfaded();
                                                }
                                            });
                                            progress.dismiss();





                                        } else if (Category.equals("Seller")) {

                                            // Create a few sample profile
                                            // NOTE you have to define the loader logic too. See the CustomApplication for more details
                                            final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
                                            final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));

                                            // Create the AccountHeader
                                            headerResult = new AccountHeaderBuilder()
                                                    .withActivity(HomeActivity.this)
                                                    .withHeaderBackground(R.drawable.header)
                                                    .addProfiles(
                                                            profile, profile2
                                                    )
                                                    .withSavedInstance(savedInstanceState)
                                                    .build();

                                            //Create the drawer
                                            result = new DrawerBuilder()
                                                    .withActivity(HomeActivity.this)
                                                    .withToolbar(toolbar)
                                                    .withHasStableIds(true)
                                                    .withDrawerLayout(R.layout.crossfade_drawer)
                                                    .withDrawerWidthDp(72)
                                                    .withGenerateMiniDrawer(true)
                                                    .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                                                    .addDrawerItems(
                                                            new PrimaryDrawerItem().withName("Orders").withIcon(FontAwesome.Icon.faw_home).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false),
                                                            new PrimaryDrawerItem().withName("Favourites").withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
                                                            new PrimaryDrawerItem().withName("Messages").withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4),
                                                            new PrimaryDrawerItem().withDescription("3 items").withName("draft").withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5),
                                                            new SecondaryDrawerItem().withName("Fb Page").withIcon(GoogleMaterial.Icon.gmd_facebook).withTag("Bullhorn")
                                                    ) // add the items we want to use with our Drawer
                                                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                                        @Override
                                                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                                            if (drawerItem instanceof Nameable) {
                                                                Toast.makeText(HomeActivity.this, ((Nameable) drawerItem).getName().getText(HomeActivity.this), Toast.LENGTH_SHORT).show();
                                                            }
                                                            //we do not consume the event and want the Drawer to continue with the event chain
                                                            return false;
                                                        }
                                                    })
                                                    .withSavedInstance(savedInstanceState)
                                                    .withShowDrawerOnFirstLaunch(true)
                                                    .build();

                                            //get the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
                                            //the CrossfadeDrawerLayout library can be found here: https://github.com/mikepenz/CrossfadeDrawerLayout
                                            crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();

                                            //define maxDrawerWidth
                                            crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(HomeActivity.this));
                                            //add second view (which is the miniDrawer)
                                            final MiniDrawer miniResult = result.getMiniDrawer();
                                            //build the view for the MiniDrawer
                                            View view = miniResult.build(HomeActivity.this);
                                            //set the background of the MiniDrawer as this would be transparent
                                            view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(HomeActivity.this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
                                            //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
                                            crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                                            //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
                                            miniResult.withCrossFader(new ICrossfader() {
                                                @Override
                                                public void crossfade() {
                                                    boolean isFaded = isCrossfaded();
                                                    crossfadeDrawerLayout.crossfade(400);

                                                    //only close the drawer if we were already faded and want to close it now
                                                    if (isFaded) {
                                                        result.getDrawerLayout().closeDrawer(GravityCompat.START);
                                                    }
                                                }

                                                @Override
                                                public boolean isCrossfaded() {
                                                    return crossfadeDrawerLayout.isCrossfaded();
                                                }
                                            });

                                            if(isShopCreated){

                                                progress.dismiss();
                                                // Instance of first fragment
                                                SellerHomePageFragment sellerhomeFragment = new SellerHomePageFragment();
                                                // Add Fragment to FrameLayout (flContainer), using FragmentManager
                                                FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                                                ft.add(R.id.home_container,sellerhomeFragment);
                                                ft.commit();


                                            }else {

                                                // Instance of first fragment
                                                SellerHomePageFragment sellerhomeFragment = new SellerHomePageFragment();
                                                // Add Fragment to FrameLayout (flContainer), using FragmentManager
                                                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                                                ft.add(R.id.home_container, sellerhomeFragment);
                                                ft.commit();
                                                progress.dismiss();

                                                Intent intent = new Intent(HomeActivity.this, SellerIntroActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);

                                            }




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


        isShopCreated = mKeyStore.getBoolean("isShopCreated");


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


                if(isShopCreated){


                // Instance of first fragment
                SellerHomePageFragment sellerhomeFragment = new SellerHomePageFragment();
                // Add Fragment to FrameLayout (flContainer), using FragmentManager
                FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                ft.add(R.id.home_container,sellerhomeFragment);
                ft.commit();
                progress.dismiss();

                }else {

                    // Instance of first fragment
                    SellerHomePageFragment sellerhomeFragment = new SellerHomePageFragment();
                    // Add Fragment to FrameLayout (flContainer), using FragmentManager
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.add(R.id.home_container, sellerhomeFragment);
                    ft.commit();
                    progress.dismiss();

                    Intent intent = new Intent(HomeActivity.this, SellerIntroActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
        }

        if(Category.equals("")){
            return;
        }else if(Category.equals("Buyer")){

            // add buyer nav-drawer

            // Create a few sample profile
            // NOTE you have to define the loader logic too. See the CustomApplication for more details
            final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
            final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));

            // Create the AccountHeader
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.header)
                    .addProfiles(
                            profile, profile2
                    )
                    .withSavedInstance(savedInstanceState)
                    .build();

            //Create the drawer
            result = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .withHasStableIds(true)
                    .withDrawerLayout(R.layout.crossfade_drawer)
                    .withDrawerWidthDp(72)
                    .withGenerateMiniDrawer(true)
                    .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName("Orders").withIcon(FontAwesome.Icon.faw_home).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false),
                            new PrimaryDrawerItem().withName("Favourites").withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
                            new PrimaryDrawerItem().withName("Messages").withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4),
                            new PrimaryDrawerItem().withDescription("3 items").withName("draft").withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5),
                            new SecondaryDrawerItem().withName("Fb Page").withIcon(GoogleMaterial.Icon.gmd_facebook).withTag("Bullhorn")
                    ) // add the items we want to use with our Drawer
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            if (drawerItem instanceof Nameable) {
                                Toast.makeText(HomeActivity.this, ((Nameable) drawerItem).getName().getText(HomeActivity.this), Toast.LENGTH_SHORT).show();
                            }
                            //we do not consume the event and want the Drawer to continue with the event chain
                            return false;
                        }
                    })
                    .withSavedInstance(savedInstanceState)
                    .withShowDrawerOnFirstLaunch(true)
                    .build();

            //get the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
            //the CrossfadeDrawerLayout library can be found here: https://github.com/mikepenz/CrossfadeDrawerLayout
            crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();

            //define maxDrawerWidth
            crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
            //add second view (which is the miniDrawer)
            final MiniDrawer miniResult = result.getMiniDrawer();
            //build the view for the MiniDrawer
            View view = miniResult.build(this);
            //set the background of the MiniDrawer as this would be transparent
            view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
            //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
            crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
            miniResult.withCrossFader(new ICrossfader() {
                @Override
                public void crossfade() {
                    boolean isFaded = isCrossfaded();
                    crossfadeDrawerLayout.crossfade(400);

                    //only close the drawer if we were already faded and want to close it now
                    if (isFaded) {
                        result.getDrawerLayout().closeDrawer(GravityCompat.START);
                    }
                }

                @Override
                public boolean isCrossfaded() {
                    return crossfadeDrawerLayout.isCrossfaded();
                }
            });


        }else{


            // Create a few sample profile
            // NOTE you have to define the loader logic too. See the CustomApplication for more details
            final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
            final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));

            // Create the AccountHeader
            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.header)
                    .addProfiles(
                            profile, profile2
                    )
                    .withSavedInstance(savedInstanceState)
                    .build();

            //Create the drawer
            result = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .withHasStableIds(true)
                    .withDrawerLayout(R.layout.crossfade_drawer)
                    .withDrawerWidthDp(72)
                    .withGenerateMiniDrawer(true)
                    .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName("Orders").withIcon(FontAwesome.Icon.faw_home).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false),
                            new PrimaryDrawerItem().withName("Items").withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
                            new PrimaryDrawerItem().withName("Messages").withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4),
                            new PrimaryDrawerItem().withDescription("3 items").withName("draft").withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5),
                            new SecondaryDrawerItem().withName("Fb Page").withIcon(GoogleMaterial.Icon.gmd_facebook).withTag("Bullhorn")
                    ) // add the items we want to use with our Drawer
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            if (drawerItem instanceof Nameable) {
                                Toast.makeText(HomeActivity.this, ((Nameable) drawerItem).getName().getText(HomeActivity.this), Toast.LENGTH_SHORT).show();
                            }
                            //we do not consume the event and want the Drawer to continue with the event chain
                            return false;
                        }
                    })
                    .withSavedInstance(savedInstanceState)
                    .withShowDrawerOnFirstLaunch(true)
                    .build();

            //get the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
            //the CrossfadeDrawerLayout library can be found here: https://github.com/mikepenz/CrossfadeDrawerLayout
            crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();

            //define maxDrawerWidth
            crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
            //add second view (which is the miniDrawer)
            final MiniDrawer miniResult = result.getMiniDrawer();
            //build the view for the MiniDrawer
            View view = miniResult.build(this);
            //set the background of the MiniDrawer as this would be transparent
            view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
            //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
            crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
            miniResult.withCrossFader(new ICrossfader() {
                @Override
                public void crossfade() {
                    boolean isFaded = isCrossfaded();
                    crossfadeDrawerLayout.crossfade(400);

                    //only close the drawer if we were already faded and want to close it now
                    if (isFaded) {
                        result.getDrawerLayout().closeDrawer(GravityCompat.START);
                    }
                }

                @Override
                public boolean isCrossfaded() {
                    return crossfadeDrawerLayout.isCrossfaded();
                }
            });


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

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


}


