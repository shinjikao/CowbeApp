package com.jackal.cowbeapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
public class FeedCommentsAdapter extends RecyclerView.Adapter {
    private final int VIEW_HEADER = 0;
    private final int VIEW_NORMAL = 1;
    private final int VIEW_FOOTER = 1;
    private String Message;

    private ArrayList<Comment.Datum> CommentsData;
    private Context context;


    public FeedCommentsAdapter(ArrayList<Comment.Datum> CommentsData, String Message) {
        this.CommentsData = CommentsData;
        this.Message = Message;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0)
            return VIEW_HEADER;
        else
            return VIEW_NORMAL;

    }

    @Override
    public int getItemCount() {
        return CommentsData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        //ViewHolder viewHolder;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_HEADER) {
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_message, null);
            viewHolder = new MessageViewHolder(itemLayoutView);
        } else {
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_feed_data_comment, null);
            viewHolder = new ViewHolder(itemLayoutView, CommentsData);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String url = "";
        Comment.Datum data = null;
        if (CommentsData.get(position) != null) {
            data = CommentsData.get(position);
            url = data.getFrom().getPicture().getData().getUrl();
        }

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        if (holder instanceof ViewHolder) {
            if (url != "" || url != null) {
                ((ViewHolder) holder).comment_user_picture.setImageUrl(url, imageLoader);
            } else {
                url = "http://iconizer.net/files/Facebook/orig/genericfriendicon.png";
                ((ViewHolder) holder).comment_user_picture.setImageUrl(url, imageLoader);
            }
            ((ViewHolder) holder).comment_name.setText(data.getFrom().getName());
            ((ViewHolder) holder).comment_message.setText(data.getMessage());
            ((ViewHolder) holder).comment_likes.setText(String.valueOf(data.getLikeCount()));
        } else {
            ((MessageViewHolder) holder).textView.setText(Message);
        }


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


            comment_user_picture = (NetworkImageView) itemLayoutView.findViewById(R.id.comment_user_picture);
            comment_name = (TextView) itemLayoutView.findViewById(R.id.comment_name);
            comment_message = (TextView) itemLayoutView.findViewById(R.id.comment_message);
            comment_likes = (TextView) itemLayoutView.findViewById(R.id.comment_likes);

        }

        @Override
        public void onClick(View view) {

        }
    }


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MessageViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.tv_message);

        }
    }
}



