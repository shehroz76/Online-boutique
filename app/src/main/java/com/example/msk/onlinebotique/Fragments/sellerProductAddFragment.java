package com.example.msk.onlinebotique.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.msk.onlinebotique.Pojo.ProductDetail;
import com.example.msk.onlinebotique.R;
import com.example.msk.onlinebotique.Utilities.KeyStore;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by DELL on 5/19/2017.
 */

public class sellerProductAddFragment extends Fragment {


    private String mProductNamestring,mProductCategorystring,mProductPricestring;

    private DatabaseReference mSellerAddProductDatabaseReference;
    private StorageReference mStorageProductImages;
    private String Uuid ="";
    private DatabaseReference curentUserProducts;

    public sellerProductAddFragment() {
    }


    private KeyStore mkKeyStore;
    private static final int Gallery_Request = 1 ;
    private Uri mImageUriMain = null;
    private Uri mImageUri1 = null;
    private Uri mImageUri2 = null;
    private Uri mImageUri3 = null;
    private Uri mImageUri4 = null;
    private Uri mImageUri5 = null;
    private Uri mImageUri6 = null;


    private ProgressDialog mProgress;

    @BindView(R.id.add_product_pic_fab)
    FloatingActionButton mFabAddProductImage;

    @BindView(R.id.upload_product_pic)
    ImageView mProductMainImage;

    @BindView(R.id.productImage1)
    ImageView mImage1;

    @BindView(R.id.productImage2)
    ImageView mImage2;

    @BindView(R.id.productImage3)
    ImageView mImage3;

    @BindView(R.id.productImage4)
    ImageView mImage4;

    @BindView(R.id.productImage5)
    ImageView mImage5;

    @BindView(R.id.productImage6)
    ImageView mImage6;

    @BindView(R.id.editText_ProductName)
    EditText mProductName;

    @BindView(R.id.editText_ProductCategory)
    EditText mProductCategory;

    @BindView(R.id.editText_ProductPrice)
    EditText mProductPrice;

    @BindView(R.id.product_done)
    Button mproductdone;


    @BindView(R.id.l2)
    LinearLayout mLinearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.seller_add_product,container,false);
        ButterKnife.bind(this,view);

        mProgress = new ProgressDialog(getActivity());

        mkKeyStore = KeyStore.getInstance(getContext());

        Uuid = mkKeyStore.getString("User_UId");

        mSellerAddProductDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Products");
        mStorageProductImages = FirebaseStorage.getInstance().getReference();



        String ShopName= mkKeyStore.getString("ShopName");


        String ProductNode = ShopName;

        curentUserProducts = mSellerAddProductDatabaseReference.child(ProductNode).push();


        return  view;

    }

    @OnClick(R.id.product_done)
    public void done(){


        addproduct();


    }


    @OnClick(R.id.add_product_pic_fab)
    public void uploadPic(){

        Intent profile_gallerY_intent = new Intent(Intent.ACTION_GET_CONTENT);
        profile_gallerY_intent.setType("image/*");
        startActivityForResult(profile_gallerY_intent , Gallery_Request);
    }


    private void addproduct(){

        mProductNamestring = mProductName.getText().toString().trim();
        mProductCategorystring = mProductCategory.getText().toString().trim();
        mProductPricestring = mProductPrice.getText().toString().trim();



        if(!mProductPricestring.equals("") || !mProductCategorystring.equals("") || !mProductNamestring.equals("") || mImageUriMain!=null){


            mProgress.setMessage("Uploading..");
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.show();

            final StorageReference imageFilePath1 = mStorageProductImages.child("Product Images").child(mImageUri1.getLastPathSegment());
            imageFilePath1.putFile(mImageUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    String profilePicUrl1 = taskSnapshot.getDownloadUrl().toString();

                    curentUserProducts.child("Product_Name").setValue(mProductNamestring);
                    curentUserProducts.child("Product_Type").setValue(mProductCategorystring);
                    curentUserProducts.child("Product_price").setValue(mProductPricestring);
                    curentUserProducts.child("Image1").setValue(profilePicUrl1);

                    if(mImageUri2==null){

                        mProgress.dismiss();

                        // opened previous fragment
                        getActivity().onBackPressed();
                    }



                }
            });

            if(mImageUri2!=null){

                StorageReference imageFilePath2 = mStorageProductImages.child("Product Images").child(mImageUri2.getLastPathSegment());
                imageFilePath2.putFile(mImageUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        String profilePicUrl2 = taskSnapshot.getDownloadUrl().toString();
                        curentUserProducts.child("Image2").setValue(profilePicUrl2);

                        if(mImageUri3==null){

                            mProgress.dismiss();

                            // opened previous fragment
                            getActivity().onBackPressed();
                        }



                    }
                });
            }

            if(mImageUri3!=null) {

                StorageReference imageFilePath3 = mStorageProductImages.child("Product Images").child(mImageUri3.getLastPathSegment());
                imageFilePath3.putFile(mImageUri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        String profilePicUrl3 = taskSnapshot.getDownloadUrl().toString();
                        curentUserProducts.child("Image3").setValue(profilePicUrl3);

                        if(mImageUri4==null){

                            mProgress.dismiss();

                            // opened previous fragment
                            getActivity().onBackPressed();
                        }


                    }
                });
            }

            if(mImageUri4!=null) {

                StorageReference imageFilePath4 = mStorageProductImages.child("Product Images").child(mImageUri4.getLastPathSegment());
                imageFilePath4.putFile(mImageUri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        String profilePicUrl4 = taskSnapshot.getDownloadUrl().toString();
                        curentUserProducts.child("Image4").setValue(profilePicUrl4);

                        if(mImageUri5==null){

                            mProgress.dismiss();

                            // opened previous fragment
                            getActivity().onBackPressed();
                        }


                    }
                });

            }

            if(mImageUri5!=null) {

                StorageReference imageFilePath5 = mStorageProductImages.child("Product Images").child(mImageUri5.getLastPathSegment());
                imageFilePath5.putFile(mImageUri5).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        String profilePicUrl5 = taskSnapshot.getDownloadUrl().toString();
                        curentUserProducts.child("Image5").setValue(profilePicUrl5);

                        if(mImageUri6==null){

                            mProgress.dismiss();

                            // opened previous fragment
                            getActivity().onBackPressed();
                        }


                    }
                });
            }

            if(mImageUri6!=null) {

                StorageReference imageFilePath6 = mStorageProductImages.child("Product Images").child(mImageUri6.getLastPathSegment());
                imageFilePath6.putFile(mImageUri6).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        String profilePicUrl6 = taskSnapshot.getDownloadUrl().toString();
                        curentUserProducts.child("Image6").setValue(profilePicUrl6);

                        mProgress.dismiss();

                        // opened previous fragment
                        getActivity().onBackPressed();

                    }
                });

            }



        }
        else
            Toast.makeText(getActivity(),"Fill all fields",Toast.LENGTH_SHORT).show();


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


                if(mImageUri1==null) {

                    mLinearLayout.setVisibility(View.VISIBLE);
                    mImageUri1 = result.getUri();

                    mProductMainImage.setImageURI(mImageUri1);
                    mImage1.setImageURI(mImageUri1);

                }else if(mImageUri2==null){

                    mImageUri2 = result.getUri();

                    mProductMainImage.setImageURI(mImageUri2);
                    mImage2.setImageURI(mImageUri2);

                }else if(mImageUri3==null){

                    mImageUri3 = result.getUri();

                    mProductMainImage.setImageURI(mImageUri3);
                    mImage3.setImageURI(mImageUri3);

                }else if(mImageUri4==null){

                    mImageUri4 = result.getUri();

                    mProductMainImage.setImageURI(mImageUri4);
                    mImage4.setImageURI(mImageUri4);

                }else if(mImageUri5==null){

                    mImageUri5 = result.getUri();

                    mProductMainImage.setImageURI(mImageUri5);
                    mImage5.setImageURI(mImageUri5);

                }else if(mImageUri6==null){

                    mImageUri6 = result.getUri();

                    mProductMainImage.setImageURI(mImageUri6);
                    mImage6.setImageURI(mImageUri6);

                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }




}
