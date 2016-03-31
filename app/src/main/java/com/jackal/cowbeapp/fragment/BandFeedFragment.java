package com.jackal.cowbeapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.flipboard.bottomsheet.BottomSheetLayout;

import com.google.gson.Gson;

import com.jackal.cowbeapp.CustomRecyclerView;
import com.jackal.cowbeapp.DataModel.Band;
import com.jackal.cowbeapp.DataModel.FakeFeed;
import com.jackal.cowbeapp.Interface.OnLoadMoreListener;
import com.jackal.cowbeapp.MainActivity;
import com.jackal.cowbeapp.R;

import com.jackal.cowbeapp.adapter.BandFeedsAdapter;
import com.jackal.cowbeapp.utility.Utility;


import java.util.ArrayList;

/**
 * Created by jackalkao on 2/24/16.
 */
public class BandFeedFragment extends Fragment {

    BottomSheetLayout bottomSheetLayout;

    private String id;

    private RecyclerView r_bandfeed;

    private Band band;

    private String Next;

    private String until;
    private ArrayList<Band.Data> Data;

    protected Handler handler;

    private View view;

    public static BandFeedFragment newInstance(String id) {
        BandFeedFragment myFragment = new BandFeedFragment();
        Bundle args = new Bundle();
        args.putString("ID", id);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        this.id = getArguments().getString("ID", "");

        Log.d(MainActivity.TAG, id);

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bandfeed, container, false);

        r_bandfeed = (RecyclerView) view.findViewById(R.id.r_bandfeed);

        bottomSheetLayout = (BottomSheetLayout) view.findViewById(R.id.bottomsheet);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.view = view;
        startRequest(view);


    }

    public void startRequest(final View view) {
        Utility.logStatus(String.valueOf(this.id));
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + this.id,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            band = new Gson().fromJson(response.getJSONObject().toString(), Band.class);


                            Next = band.getFeed().getPaging().getNext();
                            until = Next.substring(Next.indexOf("until=") + 6).split("&")[0];
                            Utility.logStatus("until " + until);

                            CustomRecyclerView.setLayoutManager(getActivity(), r_bandfeed, "VERTICAL");

                            setRecyclerView(band.getFeed().getData(), view);


                        } catch (Exception ex) {
                            Log.e(MainActivity.TAG, response.toString());

                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "cover,engagement,name,feed.limit(10){picture,link,full_picture,likes.limit(0).summary(true),comments.limit(0).summary(true),message,created_time}");
        //parameters.putString("fields", "cover,engagement,name,feed{picture,link,full_picture,likes.limit(0).summary(true),comments.limit(0).summary(true),message,created_time}");
        request.setParameters(parameters);
        request.executeAsync();

    }

    Band.Feed Feeds;

    public void startRequestNext() {
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + this.id + "/feed",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        Utility.logStatus("log=" + response.getJSONObject().toString());
                        Feeds = new Gson().fromJson(response.getJSONObject().toString(), Band.Feed.class);


                        //Get Next link
                        Next = Feeds.getPaging().getNext();
                        Utility.logStatus("Next " + Next);
                        until = Next.substring(Next.indexOf("until=") + 6).split("&")[0];
                        Utility.logStatus("until " + until);

                        //   remove progress item
                        Data.remove(Data.size() - 1);
                        adapter.notifyItemRemoved(Data.size());
                        //add items one by one
                        for (int i = 1; i < Feeds.getData().size(); i++) {
                            Band.Data d = Feeds.getData().get(i);
                            Data.add(d);
                            adapter.notifyItemInserted(Data.size());
                        }

                        adapter.setLoaded();
                        //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                    }
                });


        Bundle parameters = new Bundle();
        parameters.putString("limit", "10");
        parameters.putString("until", until);
        parameters.putString("fields", "picture,link,full_picture,likes.limit(0).summary(true),comments.limit(0).summary(true),message,created_time");
        parameters.putString("__paging_token", Next);
        request.setParameters(parameters);
        request.executeAsync();
    }

    BandFeedsAdapter adapter;

    public void setRecyclerView(final ArrayList<Band.Data> FeedData, View view) {
        Data = FeedData;

        adapter = new BandFeedsAdapter(getContext(), FeedData, r_bandfeed);

        r_bandfeed.setAdapter(adapter);

        r_bandfeed.setItemAnimator(new DefaultItemAnimator());

        Utility.logStatus(String.valueOf(view.getHeight()));

        bottomSheetLayout.setPeekSheetTranslation(view.getHeight() - 200);


        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Data.add(null);
                adapter.notifyItemInserted(Data.size() - 1);
                startRequestNext();

            }
        });

        r_bandfeed.addOnItemTouchListener(new RecyclerTouchListener(getContext(), r_bandfeed, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Band.Data data = Data.get(position);
                Utility.logStatus("r_bandfeed click " + position + "  " + data.getMessage());

                MainActivity mainActivity = (MainActivity) view.getContext();
                BandFeedDetailFragment2.newInstance(data.getId())
                        .show(mainActivity.getSupportFragmentManager(), R.id.bottomsheet);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


}
