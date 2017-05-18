package com.example.msk.onlinebotique;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.msk.onlinebotique.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

/**
 * Created by DELL on 5/18/2017.
 */

public class SellerIntroActivity extends IntroActivity {


    @Override protected void onCreate(Bundle savedInstanceState){
        setFullscreen(true);
        super.onCreate(savedInstanceState);

        /* Show/hide button */
        setButtonBackVisible(true);
/* Use skip button behavior */

//        setButtonBackFunction(BUTTON_BACK_FUNCTION_SKIP);

        /* Use back button behavior */
        setButtonBackFunction(BUTTON_BACK_FUNCTION_BACK);


        /* Show/hide button */
        setButtonNextVisible(true);
/* Use next and finish button behavior */
//        setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT_FINISH);

        /* Use next button behavior */
//        setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT);

        /* Show/hide button */
        setButtonCtaVisible(true);
/* Tint button text */
        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_TEXT);
/* Tint button background */
//        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_BACKGROUND);



        // Add slides, edit configuration...

        addSlide(new SimpleSlide.Builder()
                .title(R.string.seller_intro__heading_1)
                .description(R.string.seller_intro__desc_1)
                .image(R.drawable.profile_placeholder)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_bold)
                .scrollable(false)
                .build());



        addSlide(new SimpleSlide.Builder()
                .title(R.string.seller_intro__heading_2)
                .description(R.string.seller_intro__desc_2)
                .image(R.drawable.profile_placeholder)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_motion)
                .scrollable(false)
                .build());


        addSlide(new SimpleSlide.Builder()
                .title(R.string.seller_intro__heading_3)
                .description(R.string.seller_intro__desc_3)
                .image(R.drawable.profile_placeholder)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_shadow)
                .scrollable(false)
                .build());


        addSlide(new SimpleSlide.Builder()
                .title(R.string.seller_intro__heading_4)
                .description(R.string.seller_intro__desc_4)
                .image(R.drawable.profile_placeholder)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_metaphor)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.seller_intro__heading_5)
                .description(R.string.seller_intro__desc_5)
                .image(R.drawable.profile_placeholder)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_bold)
                .scrollable(false)
                .buttonCtaLabel("Open Shop")
                .buttonCtaClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast
                                .makeText(SellerIntroActivity.this, "Open a Shop", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                })
                .build());

    }
}
