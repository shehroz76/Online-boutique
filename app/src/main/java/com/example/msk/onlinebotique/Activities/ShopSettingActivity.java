package com.example.msk.onlinebotique.Activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msk.onlinebotique.R;
import com.example.msk.onlinebotique.Utilities.KeyStore;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 5/18/2017.
 */

public class ShopSettingActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, NumberPicker.OnValueChangeListener {

    private KeyStore mkKeyStore;
    private Boolean mShopCreated = false;
    private GoogleMap mMap;
    private boolean isMapClicked = false;

    private static final int Gallery_Request = 1 ;
    private Uri mImageUri1 = null;
    private ProgressDialog mProgress;

    private boolean mAddFenceEnable;
    private Location currentLocation;
    private Circle mCircle;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private String ShopName,ShopDesc,ShopCategory;

    private double lat , Longit;

    private DatabaseReference mShopDatabaseReference;
    private DatabaseReference mUserInfoDatabaseReference;
    private StorageReference mStorageShopImage;

    @BindView(R.id.editText_ShopName)
    EditText shopName;

    @BindView(R.id.choose_shop_category)
    TextView shopCategory;

    @BindView(R.id.editText_Shop_Desc)
    EditText shopDesc;

    @BindView(R.id.shop_image_setup)
    CircleImageView shopImage;


    private Marker mMarker;
    private String LOG_TAG = "ShopSettingActivity";
    private String Uuid = null;
    private String OwenerName = "";
    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_setup);
        ButterKnife.bind(this);
        mkKeyStore = KeyStore.getInstance(this);

        mProgress = new ProgressDialog(this);

        mShopDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Shops");
        mUserInfoDatabaseReference = FirebaseDatabase.getInstance().getReference().child("USers_Info");
        mStorageShopImage = FirebaseStorage.getInstance().getReference();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }



    @OnClick(R.id.createSHop_button)
    public void createShop() {

        mShopCreated = true;
        mkKeyStore.putBoolean("isShopCreated", mShopCreated);

        String ShopName11 = shopName.getText().toString().trim();



        ShopDesc = shopDesc.getText().toString().trim();
        Uuid = mkKeyStore.getString("User_UId");
        OwenerName = mkKeyStore.getString("Name");

        ShopName = ShopName11 + "_" + Uuid;

        if(Uuid.equals("") || OwenerName.equals("") || mImageUri1 == null ||  ShopName.equals("")
                || ShopDesc.equals("") || ShopCategory ==null || lat==0.0 || Longit==0.0){

            View parent = findViewById(R.id.root);
            Snackbar.make(parent, "Please fill all Fileds" ,Snackbar.LENGTH_LONG).show();

        } else {

            StorageReference imageFilepath = mStorageShopImage.child("Shop Cover Images").child(mImageUri1.getLastPathSegment());
            imageFilepath.putFile(mImageUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mUserInfoDatabaseReference.child(Uuid).child("isShopOpened").setValue("true");

                    String profilePicUrl = taskSnapshot.getDownloadUrl().toString();

                    mShopDatabaseReference.child(ShopName).child("OwnerName").setValue(OwenerName);
                    mShopDatabaseReference.child(ShopName).child("OwnerUuid").setValue(Uuid);
                    mShopDatabaseReference.child(ShopName).child("ShopName").setValue(ShopName);
                    mShopDatabaseReference.child(ShopName).child("ShopDesc").setValue(ShopDesc);
                    mShopDatabaseReference.child(ShopName).child("ShopCategory").setValue(ShopCategory);
                    mShopDatabaseReference.child(ShopName).child("ShopImage").setValue(profilePicUrl);
                    mShopDatabaseReference.child(ShopName).child("Location").setValue("Latitude = " +lat + " Longitde = "+ Longit);

                }
            });






            Intent intent = new Intent(ShopSettingActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }




    }

    @OnClick(R.id.upload_button)
    public void upload(){

        Intent profile_gallerY_intent = new Intent(Intent.ACTION_GET_CONTENT);
        profile_gallerY_intent.setType("image/*");
        startActivityForResult(profile_gallerY_intent , Gallery_Request);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_Request && resultCode == RESULT_OK) {

            Uri ImageUri1 = data.getData();

            CropImage.activity(ImageUri1).
                    setGuidelines(CropImageView.Guidelines.ON).
                    setAspectRatio(1 , 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri1 = result.getUri();

                shopImage.setImageURI(mImageUri1);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    @OnClick(R.id.choose_shop_category)
    public void choosecategory(){



        final Dialog d = new Dialog(ShopSettingActivity.this , R.style.MaterialBaseTheme_Light_AlertDialog);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.shop_category_dialog);
        Button b1 = (Button) d.findViewById(R.id.select);
        Button b2 = (Button) d.findViewById(R.id.cancel);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.chooseShopCategoryNp);

        final String[] arrayPicker= new String[]{"Clothing","Food","Sports","technology","Vehicle"};

        np.setMaxValue(arrayPicker.length-1);
        np.setMinValue(0);
        np.setDisplayedValues(arrayPicker);
        //disable soft keyboard
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        //set wrap true or false, try it you will know the difference
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = np.getValue();
                //get string from number picker and position
                ShopCategory = arrayPicker[pos];


                shopCategory.setText(ShopCategory);
                d.dismiss();

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               d.dismiss();

            }
        });

        d.show();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
//        if(lat==0.0 && Longit==0.0) {
//            LatLng TutorialsPoint = new LatLng(20, 60);
//
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
//            mCircle = mMap.addCircle(new CircleOptions()
//                            .center(TutorialsPoint)
//                            .radius(100)
//                            .fillColor(Color.argb(50, 48, 183, 239))
//                            .strokeColor(Color.rgb(250, 175, 64))
////
//            );
//            mMarker = googleMap.addMarker(new MarkerOptions()
//                    .position(TutorialsPoint)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.fence_icon))
//            );
//
//
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
//
//        }



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Creating a marker
                isMapClicked =true;

                MarkerOptions markerOptions = new MarkerOptions();

                LatLng TutorialsPoint = new LatLng(latLng.latitude, latLng.longitude);


//                mCircle = mMap.addCircle(new CircleOptions()
//                                .center(TutorialsPoint)
//                                .fillColor(Color.argb(50, 48, 183, 239))
//                                .strokeColor(Color.rgb(250, 175, 64))
//                );

                // Setting the position for the marker
                markerOptions.position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.fence_icon));

                // Setting the title for the marker.
                // This will be displayed on taping the marker

                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                mMap.clear();



                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.addCircle(new CircleOptions()
                        .center(TutorialsPoint)
                        .radius(100)
                        .fillColor(Color.argb(50, 48, 183, 239))
                        .strokeColor(Color.rgb(250, 175, 64)));
                lat = latLng.latitude;
                Longit = latLng.longitude;
                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10); // Update location every second

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOG_TAG, "GoogleApiClient connection has been suspend");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.i(LOG_TAG, "GoogleApiClient connection has failed");
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i(LOG_TAG, location.toString());



        if(isMapClicked==false){

            lat = location.getLatitude();
            Longit = location.getLongitude();


            LatLng TutorialsPoint = new LatLng(lat, Longit);

            mMap.clear();

            mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
            mCircle = mMap.addCircle(new CircleOptions()
                            .center(TutorialsPoint)
                            .radius(100)
                            .fillColor(Color.argb(50, 48, 183, 239))
                            .strokeColor(Color.rgb(250, 175, 64))
//
            );
            mMarker = mMap.addMarker(new MarkerOptions()
                    .position(TutorialsPoint)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.fence_icon))
            );


            mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));


        }



    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }
}
