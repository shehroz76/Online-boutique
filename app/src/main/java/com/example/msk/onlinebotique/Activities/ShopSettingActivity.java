package com.example.msk.onlinebotique.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

import com.example.msk.onlinebotique.R;
import com.example.msk.onlinebotique.Utilities.KeyStore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DELL on 5/18/2017.
 */

public class ShopSettingActivity extends FragmentActivity {

    private KeyStore mkKeyStore;
    private Boolean mShopCreated = false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_setup);
        ButterKnife.bind(this);
        mkKeyStore =KeyStore.getInstance(this);

    }

    @OnClick(R.id.createSHop_button)
    public void createShop(){


        mShopCreated=true;
        mkKeyStore.putBoolean("isShopCreated",mShopCreated);
        Intent intent = new Intent(ShopSettingActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }


}
