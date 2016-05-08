package com.jackal.cowbeapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.jackal.cowbeapp.app.AppController;
import com.jackal.cowbeapp.fragment.FacebookLoginFragment;
import com.jackal.cowbeapp.utility.Utility;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "JACKAL";
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.logStatus(" MainActivity onResume()");
//        NetworkImageView mNetworkImageView = (NetworkImageView) findViewById(R.id.feed_cover);
//        mNetworkImageView.setImageUrl("http://c2.staticflickr.com/4/3353/3518135305_79b7cbe233_z.jpg", imageLoader);
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
                onBackPressed();
//                NetworkImageView mNetworkImageView = (NetworkImageView) findViewById(R.id.feed_cover);
//                mNetworkImageView.setImageUrl("http://c2.staticflickr.com/4/3353/3518135305_79b7cbe233_z.jpg", imageLoader);
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
