package com.example.msk.onlinebotique.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.msk.onlinebotique.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MSk on 09/03/2017.
 */

public class SetupFragment extends android.support.v4.app.Fragment {


    private static final int Gallery_Request = 1 ;
    private Uri mImageUri1 = null;

    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth mAuth;
    private StorageReference mStorageUserImage;

    private ProgressDialog mProgress;


    @BindView(R.id.CitySpinner)
    Spinner CitySpinner;

    @BindView(R.id.CountrySpinner)
    Spinner CountrySpinner;


    @BindView(R.id.profile_image_setup)
    CircleImageView ProfilePicUpload;

    @BindView(R.id.emailVerification)
    TextView EmailVerification;

    @BindView(R.id.Seller)
    Button SellerButton;

    @BindView(R.id.buyer)
    Button buyerButton;

    @BindView(R.id.DoneButton)
    Button DoneButton;

    ArrayAdapter<CharSequence> CitySpinnerAdapter;
    ArrayAdapter<CharSequence> CountrySpinnerAdapter;



    public SetupFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_setup_image,container,false);
        ButterKnife.bind(this,rootView);

        mAuth = FirebaseAuth.getInstance();

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        mStorageUserImage = FirebaseStorage.getInstance().getReference();

        mProgress = new ProgressDialog(getActivity());

        CountrySpinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Country_Name, android.R.layout.simple_spinner_item);
        CountrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        CountrySpinner.setAdapter(CountrySpinnerAdapter);


        CitySpinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.City_Name, android.R.layout.simple_spinner_item);
        CitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        CitySpinner.setAdapter(CitySpinnerAdapter);


        return rootView;
    }


    @OnClick(R.id.upload_button)
    public void UploadImage(){

        Intent profile_gallerY_intent = new Intent(Intent.ACTION_GET_CONTENT);
        profile_gallerY_intent.setType("image/*");
        startActivityForResult(profile_gallerY_intent , Gallery_Request);

    }



    @OnClick(R.id.Seller)
    public void SellerButton(){

        SellerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        buyerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

    }



    @OnClick(R.id.buyer)
    public void BuyerButton(){

        buyerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        SellerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_Request && resultCode == RESULT_OK) {

            Uri ImageUri1 = data.getData();

            CropImage.activity(ImageUri1).
                    setGuidelines(CropImageView.Guidelines.ON).
                    setAspectRatio(1 , 1)
                    .start(getContext(), this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mImageUri1 = result.getUri();

                ProfilePicUpload.setImageURI(mImageUri1);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }







}
