package com.jackal.cowbeapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.gson.Gson;
import com.jackal.cowbeapp.CustomRecyclerView;
import com.jackal.cowbeapp.DataModel.Band;
import com.jackal.cowbeapp.MainActivity;
import com.jackal.cowbeapp.R;
import com.jackal.cowbeapp.adapter.BandAdapter;
import com.jackal.cowbeapp.utility.Utility;
import com.mikepenz.iconics.view.IconicsImageView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    CallbackManager callbackManager;

    private AccessToken accessToken;

    private RecyclerView r_view;

    private String[] bandList;

    private ArrayList<Band> bands = new ArrayList();

    private IconicsImageView Iconics_test;

    public MainActivityFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        accessToken = AccessToken.getCurrentAccessToken();
        startRequest();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    Toolbar toolBar;

    protected Toolbar setupToolBar(AppCompatActivity a) {
        toolBar = (Toolbar) getView().findViewById(R.id.toolbar);
        if (toolBar == null) return null;
        a.setSupportActionBar(toolBar);
        a.getSupportActionBar().setTitle("XXX");
        a.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return toolBar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setupToolBar((AppCompatActivity) getActivity());

        r_view = (RecyclerView) view.findViewById(R.id.r_view);

        bandList = getResources().getStringArray(R.array.cowbaband);

        startRequest();
    }



    public static String str2;

    public void startRequest() {
        for (String str : bandList) {
            str2 = str;
            GraphRequest request = GraphRequest.newGraphPathRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/" + str,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            try {
                                Band band = new Gson().fromJson(response.getJSONObject().toString(), Band.class);

                                bands.add(band);

                                CustomRecyclerView.setLayoutManager(getActivity(), r_view, "GRID");

                                setView(bands);
                            } catch (Exception ex) {
                                Log.e(MainActivity.TAG, response.toString());
                                Log.e(MainActivity.TAG, str2);
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "cover,engagement,name, picture.width(300).height(300){url}");

            request.setParameters(parameters);
            request.executeAsync();
        }


    }

    public void setView(ArrayList<Band> data) {
        BandAdapter adapter = new BandAdapter(data);
        r_view.setAdapter(adapter);
        r_view.setItemAnimator(new DefaultItemAnimator());
    }

}
