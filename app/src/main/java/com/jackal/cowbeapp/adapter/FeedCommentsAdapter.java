package com.jackal.cowbeapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jackal.cowbeapp.DataModel.Band;
import com.jackal.cowbeapp.DataModel.Comment;
import com.jackal.cowbeapp.MainActivity;
import com.jackal.cowbeapp.R;
import com.jackal.cowbeapp.app.AppController;
import com.jackal.cowbeapp.fragment.BandFeedDetailFragment;
import com.jackal.cowbeapp.utility.Utility;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jackalkao on 2/27/16.
 */
public class FeedCommentsAdapter extends RecyclerView.Adapter<FeedCommentsAdapter.ViewHolder> {

    private ArrayList<Comment.Datum> CommentsData;
    private Context context;

    public FeedCommentsAdapter(ArrayList<Comment.Datum> CommentsData) {
        this.CommentsData = CommentsData;

    }

    @Override
    public int getItemCount() {
        return CommentsData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_feed_data_comment, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView, CommentsData);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        String url = CommentsData.get(position).getFrom().getPicture().getData().getUrl();
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        if (url != "" || url != null) {
            holder.comment_user_picture.setImageUrl(url, imageLoader);
        } else {
            url = "http://iconizer.net/files/Facebook/orig/genericfriendicon.png";
            holder.comment_user_picture.setImageUrl(url, imageLoader);
        }


        holder.comment_name.setText(CommentsData.get(position).getFrom().getName());
        holder.comment_message.setText(CommentsData.get(position).getMessage());
        holder.comment_likes.setText(String.valueOf(CommentsData.get(position).getLikeCount()));


    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        NetworkImageView comment_user_picture;

        TextView comment_name;

        TextView comment_message;

        TextView comment_likes;

        TextView feed_commentCount;

        ArrayList<Comment.Datum> CommentsData;

        View itemLayoutView;

        public ViewHolder(View itemLayoutView, ArrayList<Comment.Datum> CommentsData) {
            super(itemLayoutView);
            this.itemLayoutView = itemLayoutView;
            this.CommentsData = CommentsData;


            comment_user_picture=(NetworkImageView)itemLayoutView.findViewById(R.id.comment_user_picture);
            comment_name = (TextView) itemLayoutView.findViewById(R.id.comment_name);
            comment_message = (TextView) itemLayoutView.findViewById(R.id.comment_message);
            comment_likes = (TextView) itemLayoutView.findViewById(R.id.comment_likes);

        }

        @Override
        public void onClick(View view) {

        }
    }
}


