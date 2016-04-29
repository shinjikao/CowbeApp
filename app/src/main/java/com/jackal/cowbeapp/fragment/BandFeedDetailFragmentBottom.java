package com.jackal.cowbeapp.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.flipboard.bottomsheet.commons.BottomSheetFragment;
import com.google.gson.Gson;
import com.jackal.cowbeapp.CustomRecyclerView;
import com.jackal.cowbeapp.DataModel.Band;
import com.jackal.cowbeapp.DataModel.Comment;
import com.jackal.cowbeapp.FacebookFunction;
import com.jackal.cowbeapp.MainActivity;
import com.jackal.cowbeapp.R;
import com.jackal.cowbeapp.adapter.FeedCommentsAdapter;

import java.util.ArrayList;

/**
 * Created by jackalkao on 2/24/16.
 */
public class BandFeedDetailFragmentBottom extends BottomSheetFragment {

    private ArrayList<Band.Feed> feedComment = new ArrayList();

    private String id;

    private Button mSubmitComment;
    private EditText mEditComment;
    private RecyclerView mRecycViewFeedComment;

    public static BandFeedDetailFragmentBottom newInstance(String id) {
        BandFeedDetailFragmentBottom myFragment = new BandFeedDetailFragmentBottom();
        Bundle args = new Bundle();
        args.putString("ID", id);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        this.id = getArguments().getString("ID", "");

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bandfeed_bottom, container, false);


        mEditComment = (EditText) view.findViewById(R.id.user_comment);
        mSubmitComment = (Button) view.findViewById(R.id.submit_comment);
        mRecycViewFeedComment = (RecyclerView) view.findViewById(R.id.r_bandfeed);

        mSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FacebookFunction.PostComment(id , mEditComment.getText().toString() );
            }
        });

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

                            CustomRecyclerView.setLayoutManager(getActivity(), mRecycViewFeedComment, "VERTICAL");

                            setRecyclerView(comments.getComments().getData());
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

    public void setRecyclerView(ArrayList<Comment.Datum> data) {
        FeedCommentsAdapter adapter = new FeedCommentsAdapter(data, "");
        mRecycViewFeedComment.setAdapter(adapter);
        mRecycViewFeedComment.setItemAnimator(new DefaultItemAnimator());
    }
}
