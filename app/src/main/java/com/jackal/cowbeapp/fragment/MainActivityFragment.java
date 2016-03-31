package com.jackal.cowbeapp.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.jackal.cowbeapp.CustomRecyclerView;
import com.jackal.cowbeapp.DataModel.Band;

import com.jackal.cowbeapp.MainActivity;
import com.jackal.cowbeapp.R;
import com.jackal.cowbeapp.adapter.BandAdapter;
import com.jackal.cowbeapp.utility.Utility;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    CallbackManager callbackManager;

    private AccessToken accessToken;

    private RecyclerView r_view ;

    private String[] bandList;

    private ArrayList<Band> bands = new ArrayList();

    private IconicsImageView Iconics_test;

    public MainActivityFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        accessToken=  AccessToken.getCurrentAccessToken();
        startRequest();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        r_view=(RecyclerView) view.findViewById(R.id.r_view);


        bandList = getResources().getStringArray(R.array.cowbaband);

        startRequest();

        return view;
    }


    public static String str2 ;
    public void startRequest(){
        for(String str : bandList) {
            str2 = str;
            GraphRequest request = GraphRequest.newGraphPathRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/"+str,
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            try{
                                Band band = new Gson().fromJson(response.getJSONObject().toString(), Band.class);

                                Log.d(MainActivity.TAG, band.getName());

                                bands.add(band);

                                CustomRecyclerView.setLayoutManager(getActivity(), r_view, "GRID");

                                setView(bands);
                            }catch(Exception ex){
                                Log.e(MainActivity.TAG , response.toString());
                                Log.e(MainActivity.TAG , str2);
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "cover,engagement,name, picture.width(300).height(300){url}");

            request.setParameters(parameters);
            request.executeAsync();
        }



    }
    public void setView(ArrayList<Band> data){
        BandAdapter adapter = new BandAdapter(data);
        r_view.setAdapter(adapter);
        r_view.setItemAnimator(new DefaultItemAnimator());
    }

}
