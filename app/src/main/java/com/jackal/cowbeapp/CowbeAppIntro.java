package com.jackal.cowbeapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.jackal.cowbeapp.utility.Utility;

/**
 * Created by jackalkao on 4/1/16.
 */
public class CowbeAppIntro extends AppIntro {
    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(AppIntroFragment.newInstance("Step 1", "Description here...\nYeah, I've added this fragment programmatically",
                R.drawable.com_facebook_button_icon_blue, Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance("Step 2", "Description here...\nYeah, I've added this fragment programmatically",
                R.drawable.com_facebook_button_icon_blue, Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance("Step 3", "Description here...\nYeah, I've added this fragment programmatically",
                R.drawable.com_facebook_button_icon_blue, Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance("Step 4", "Description here...\nYeah, I've added this fragment programmatically",
                R.drawable.com_facebook_button_icon_blue, Color.parseColor("#2196F3")));

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        //addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed() {
        Log.d(Utility.TAG , "onSkipPressed");
    }

    @Override
    public void onNextPressed() {
        Log.d(Utility.TAG , "onNextPressed");
    }

    @Override
    public void onDonePressed() {
        Log.d(Utility.TAG , "onDonePressed");
    }

    @Override
    public void onSlideChanged() {
        Log.d(Utility.TAG , "onSlideChanged");
    }
}
