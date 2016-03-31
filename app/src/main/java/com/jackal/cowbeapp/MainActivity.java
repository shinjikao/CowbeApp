package com.jackal.cowbeapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.jackal.cowbeapp.fragment.BandFeedFragment;
import com.jackal.cowbeapp.fragment.FacebookLoginFragment;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;


import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "JACKAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("靠北");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.filter).setIcon(
                new IconicsDrawable(this, CommunityMaterial.Icon.cmd_filter_variant)
                        .actionBar().color(Color.WHITE)
        );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                // mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
            case R.id.loginFB:
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, FacebookLoginFragment.newInstance("", ""))
                        .addToBackStack("FacebookLogin")
                        .commit();
                return true;
            case R.id.filter:
                //showHelp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
