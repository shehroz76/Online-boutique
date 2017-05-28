package com.example.msk.onlinebotique.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 5/18/2017.
 */

public class ShopSettingActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private KeyStore mkKeyStore;
    private Boolean mShopCreated = false;
    private GoogleMap mMap;
    private boolean isMapClicked = false;

    private boolean mAddFenceEnable;
    private Location currentLocation;
    private Circle mCircle;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private double lat , Longit;

    @BindView(R.id.editText_ShopName)
    EditText shopName;

    @BindView(R.id.shop_category)
    TextView shopCategory;

    @BindView(R.id.editText_Shop_Desc)
    EditText shopDesc;

    @BindView(R.id.shop_image_setup)
    CircleImageView shopImage;

    @BindView(R.id.upload_button)
    Button uploadImageButton;
    private Marker mMarker;
    private String LOG_TAG = "ShopSettingActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_setup);
        ButterKnife.bind(this);
        mkKeyStore = KeyStore.getInstance(this);

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
        Intent intent = new Intent(ShopSettingActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

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


                lat = latLng.latitude;
                Longit = latLng.longitude;
                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.addCircle(new CircleOptions()
                        .center(TutorialsPoint)
                        .radius(100)
                        .fillColor(Color.argb(50, 48, 183, 239))
                        .strokeColor(Color.rgb(250, 175, 64)));
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

        lat = location.getLatitude();
        Longit = location.getLongitude();


        LatLng TutorialsPoint = new LatLng(lat, Longit);

        if(isMapClicked==false){

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
}
