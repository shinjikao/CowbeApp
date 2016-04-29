package com.jackal.cowbeapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import com.jackal.cowbeapp.CustomRecyclerView;
import com.jackal.cowbeapp.DataModel.Band;
import com.jackal.cowbeapp.DataModel.Comment;
import com.jackal.cowbeapp.MainActivity;
import com.jackal.cowbeapp.R;
import com.jackal.cowbeapp.adapter.FeedCommentsAdapter;
import com.jackal.cowbeapp.app.AppController;

import java.util.ArrayList;



/**
 * Created by jackalkao on 2/24/16.
 */
public class BandFeedDetailFragment extends Fragment {

    private ArrayList<Band.Feed> feedComment = new ArrayList();

    private String id;
    private String FullPicture;
    private String Message;

    private RecyclerView r_feedComment;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public static BandFeedDetailFragment newInstance(String id, String FullPicture, String Message) {
        BandFeedDetailFragment myFragment = new BandFeedDetailFragment();
        Bundle args = new Bundle();
        args.putString("ID", id);
        args.putString("FULL_PICTURE", FullPicture);
        args.putString("MESSAGE", Message);

        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        this.id = getArguments().getString("ID", "");
        this.FullPicture = getArguments().getString("FULL_PICTURE", "");
        this.Message = getArguments().getString("MESSAGE", "");

        Log.d(MainActivity.TAG, id);


        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bandfeed_detail, container, false);
        r_feedComment = (RecyclerView) view.findViewById(R.id.r_bandfeed);
        NetworkImageView mNetworkImageView = (NetworkImageView) getActivity().findViewById(R.id.feed_cover);
        mNetworkImageView.setImageUrl(this.FullPicture, imageLoader);
        startRequest();
        return view;
    }

    public void startRequest() {

        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + this.id,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            Comment comments = new Gson().fromJson(response.getJSONObject().toString(), Comment.class);

                            CustomRecyclerView.setLayoutManager(getActivity(), r_feedComment, "VERTICAL");

                            setRecyclerView(comments.getComments().getData(), Message);

                        } catch (Exception ex) {
                            Log.e(MainActivity.TAG, response.toString());

                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "full_picture,comments.limit(200){from{name,picture.type(normal)},message,user_likes,comment_count,like_count}");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void setRecyclerView(ArrayList<Comment.Datum> data, String Message) {

        ArrayList<Comment.Datum> mData = new ArrayList<Comment.Datum>();
        mData.add(null);
        mData.addAll(data);

        FeedCommentsAdapter adapter = new FeedCommentsAdapter(mData, Message);
        r_feedComment.setAdapter(adapter);
        r_feedComment.setItemAnimator(new DefaultItemAnimator());
    }
}
