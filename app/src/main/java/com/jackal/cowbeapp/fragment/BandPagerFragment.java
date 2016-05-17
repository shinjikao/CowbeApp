package com.jackal.cowbeapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import com.jackal.cowbeapp.CustomRecyclerView;
import com.jackal.cowbeapp.DataModel.Band;
import com.jackal.cowbeapp.MainActivity;
import com.jackal.cowbeapp.R;
import com.jackal.cowbeapp.adapter.BandAdapter;
import com.jackal.cowbeapp.utility.Utility;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BandPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BandPagerFragment extends Fragment {
    private String[] bandList;
    private ArrayList<Band> bands ;
    private RecyclerView mRecyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[] mParam3;


    public BandPagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param param3 Parameter 3.
     * @return A new instance of fragment BandFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BandPagerFragment newInstance(String param1, String param2 ,String[] param3) {
        BandPagerFragment fragment = new BandPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putStringArray(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getStringArray(ARG_PARAM3);
            bandList = mParam3;

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_band_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bands = new ArrayList();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rr_view);
        Utility.logStatus("???" + mParam1);
        startRequest();

    }
    public static String str2;

    public void startRequest() {
        Utility.logStatus(String.valueOf(bandList.length));
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
                                CustomRecyclerView.setLayoutManager(getActivity(), mRecyclerView, "GRID", 0);
                                SetRecyclerView(bands);

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
    public void SetRecyclerView(ArrayList<Band> data) {
        BandAdapter adapter = new BandAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
