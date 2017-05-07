package com.example.msk.onlinebotique.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.msk.onlinebotique.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Map;

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
    private DatabaseReference mEmailVerfiedDatabaseRefernce;


    @BindView(R.id.CitySpinner)
    Spinner CitySpinner;

    @BindView(R.id.CountrySpinner)
    Spinner CountrySpinner;


    @BindView(R.id.profile_image_setup)
    CircleImageView ProfilePicUpload;

    @BindView(R.id.Seller)
    Button SellerButton;

    @BindView(R.id.buyer)
    Button buyerButton;

    @BindView(R.id.DoneButton)
    Button DoneButton;

    @BindView(R.id.emailVerification)
    TextView isEmailVerified;



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

        View rootView = inflater.inflate(R.layout.fragment_setup,container,false);
        ButterKnife.bind(this,rootView);

        mAuth = FirebaseAuth.getInstance();
        mEmailVerfiedDatabaseRefernce = FirebaseDatabase.getInstance().getReference().child("Email_Verification").child("Email");


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


        EmailVerification();




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


    private void EmailVerification(){



        mEmailVerfiedDatabaseRefernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, String> map = (Map)dataSnapshot.getValue();
                            String check_email_verification = map.get("isEmailVerified");


                Log.d("abc","value "+dataSnapshot.getValue());

                if(check_email_verification.equals("1")){



                    isEmailVerified.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
                    isEmailVerified.setText("EMAIL VERIFIED");


                }else if(check_email_verification.equals("0")){



                    isEmailVerified.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    isEmailVerified.setText("FIRST VERIFY YOUR EMAIL");

                }

//                String abc = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }



    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.


//            final String userEmail = emailSIgnUpEditText.getText().toString().trim();

            mEmailVerfiedDatabaseRefernce.child("userEmail");


            // 1 for verified

            mEmailVerfiedDatabaseRefernce.child("isEmailVerified").setValue("1");



            isEmailVerified.setTextColor(getResources().getColor(R.color.colorGreen));

            Toast.makeText(getActivity(), "Successfully logged in", Toast.LENGTH_SHORT).show();


        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            mEmailVerfiedDatabaseRefernce.child("userEmail");


            mEmailVerfiedDatabaseRefernce.child("isEmailVerified").setValue("0");

            //restart this activity

        }
    }







    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            checkIfEmailVerified();

        }
    };










}
