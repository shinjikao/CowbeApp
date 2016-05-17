package com.jackal.cowbeapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private TextView like_count, love_count, haha_count, wow_count, sad_count, angry_count;
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
        Log.d(MainActivity.TAG, "A" + FullPicture);
        Log.d(MainActivity.TAG, Message);
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bandfeed_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        like_count = (TextView) view.findViewById(R.id.like_count);
        love_count = (TextView) view.findViewById(R.id.love_count);
        haha_count = (TextView) view.findViewById(R.id.haha_count);
        wow_count = (TextView) view.findViewById(R.id.wow_count);
        sad_count = (TextView) view.findViewById(R.id.sad_count);
        angry_count = (TextView) view.findViewById(R.id.angry_count);

        r_feedComment = (RecyclerView) view.findViewById(R.id.r_bandfeed_detail);
        AppBarLayout appbar = (AppBarLayout) view.findViewById(R.id.appbar);

        startRequest();
        Log.d(MainActivity.TAG, String.valueOf(!FullPicture.isEmpty()));
        if (!FullPicture.isEmpty()) {
            NetworkImageView mNetworkImageView = (NetworkImageView) view.findViewById(R.id.feed_cover);
            mNetworkImageView.setImageUrl(this.FullPicture, imageLoader);
        } else {
            appbar.setExpanded(false);
            appbar.setMinimumHeight(0);

        }
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

                            CustomRecyclerView.setLayoutManager(getActivity(), r_feedComment, "VERTICAL", 0);
                            SetReactions(comments);

                            if (comments.getComments() != null) {
                                setRecyclerView(comments.getComments().getData(), Message);
                            } else {
                                setRecyclerView(null, Message);
                            }

                        } catch (Exception ex) {
                            Log.e(MainActivity.TAG, ex.getMessage());
                            Log.e(MainActivity.TAG, response.toString());

                        }
                    }
                });

        Bundle parameters = new Bundle();
        //parameters.putString("fields", "full_picture,comments.limit(10){from{name,picture.type(normal)},message,user_likes,comment_count,like_count},message");
        parameters.putString("fields", "full_picture,comments.limit(10){from{name,picture.type(normal)},message,user_likes,comment_count,like_count},message," +
                "reactions.type(HAHA).limit(0).summary(true).as(haha)," +
                "reactions.type(LOVE).limit(0).summary(true).as(love)," +
                "reactions.type(WOW).limit(0).summary(true).as(wow)," +
                "reactions.type(LIKE).limit(0).summary(true).as(like)," +
                "reactions.type(SAD).limit(0).summary(true).as(sad)," +
                "reactions.type(ANGRY).limit(0).summary(true).as(angry)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void setRecyclerView(ArrayList<Comment.Datum> comments, String Message) {

        ArrayList<Comment.Datum> mData = new ArrayList<Comment.Datum>();
        mData.add(null);
        if (comments != null) mData.addAll(comments);

        FeedCommentsAdapter adapter = new FeedCommentsAdapter(mData, Message);
        r_feedComment.setAdapter(adapter);
        r_feedComment.setItemAnimator(new DefaultItemAnimator());
    }

    public void SetReactions(Comment comment) {
        String Like = String.valueOf(comment.getLike().getSummary().getTotalCount());
        String Love= String.valueOf(comment.getLove().getSummary().getTotalCount());
        String Haha= String.valueOf(comment.getHaha().getSummary().getTotalCount());
        String Wow = String.valueOf(comment.getWow().getSummary().getTotalCount());
        String Sad  =String.valueOf(comment.getSad().getSummary().getTotalCount());
        String Angry = String.valueOf(comment.getAngry().getSummary().getTotalCount());
        like_count.setText(Like);
        love_count.setText(Love);
        haha_count.setText(Haha);
        wow_count.setText(Wow);
        sad_count.setText(Sad);
        angry_count.setText(Angry);
    }

}
