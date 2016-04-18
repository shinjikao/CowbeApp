package com.jackal.cowbeapp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.jackal.cowbeapp.DataModel.Band;
import com.jackal.cowbeapp.Interface.OnLoadMoreListener;
import com.jackal.cowbeapp.MainActivity;
import com.jackal.cowbeapp.R;
import com.jackal.cowbeapp.app.AppController;
import com.jackal.cowbeapp.fragment.BandFeedDetailFragment;
import com.jackal.cowbeapp.fragment.BandFeedDetailFragmentBottom;
import com.jackal.cowbeapp.utility.Utility;
import com.mikepenz.iconics.view.IconicsImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jackalkao on 2/27/16.
 */
public class BandFeedsAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private ArrayList<Band.Data> feedData;

    public ViewHolder viewHolder;

    private Context context;

    private RecyclerView recyclerView;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    public OnItemClickListener mItemClickListener;


    public BandFeedsAdapter(Context context, ArrayList<Band.Data> feedData) {
        this(context, feedData, null);
    }

    public BandFeedsAdapter(Context context, ArrayList<Band.Data> feedData, RecyclerView recyclerView) {
        this.context = context;
        this.feedData = feedData;
        this.recyclerView = recyclerView;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition();
                    if (!loading
                            && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something


                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        return feedData.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        RecyclerView.ViewHolder viewHolder;

        if (viewType == VIEW_ITEM) {
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_feed_data, parent, false);

            viewHolder = new ViewHolder(itemLayoutView);

        } else {
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);

            viewHolder = new ProgressViewHolder(itemLayoutView);
        }

        return viewHolder;
    }

    static String result;

    public void Like(String objID) {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + objID + "/likes",
                null,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                         /* handle the result */
                        JSONObject jsonObject = response.getJSONObject();
                        try {

                            result = jsonObject.getString("success");

                            if (result == "true") {

                                //viewHolder.changeLikeIconColor();
                            } else
                                Utility.logStatus("false");
                        } catch (Exception ee) {

                        }
                    }
                }
        ).executeAsync();
    }


    public Band.Data getItem(int position) {
        return feedData.get(position);
    }

    public ViewHolder holder2;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        Band.Data data = (Band.Data) feedData.get(position);


        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        if (holder instanceof ViewHolder) {
            Date d = Utility.FBStringToDate(data.getCreated_time().toString());
            String strDate = Utility.FBDateToString(d);
            String url = data.getFullPicture();
            ((ViewHolder) holder).feed_full_picture.setImageUrl(url, imageLoader);
            ((ViewHolder) holder).feed_message.setText(data.getMessage());
            ((ViewHolder) holder).feed_date.setText(strDate);
            ((ViewHolder) holder).feed_likesCount.setText(String.valueOf(data.getLikes().getSummary().getTotalCount()));
            ((ViewHolder) holder).feed_commentCount.setText(String.valueOf(data.getComments().getSummary().getTotalCount()));

            holder.setIsRecyclable(false);
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }


    @Override
    public int getItemCount() {
        return feedData.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        NetworkImageView feed_full_picture;

        TextView feed_message;

        TextView feed_date;

        IconicsImageView feed_likeIcon;

        IconicsImageView feed_commentIcon;

        TextView feed_likesCount;

        TextView feed_commentCount;

        View mItemLayoutView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            this.mItemLayoutView = itemLayoutView;

            feed_full_picture = (NetworkImageView) itemLayoutView.findViewById(R.id.feed_full_picture);
            feed_message = (TextView) itemLayoutView.findViewById(R.id.feed_message);
            feed_date = (TextView) itemLayoutView.findViewById(R.id.feed_date);
            feed_likeIcon = (IconicsImageView) itemLayoutView.findViewById(R.id.feed_likeIcon);
            feed_commentIcon = (IconicsImageView) itemLayoutView.findViewById(R.id.feed_commentIcon);

            feed_likesCount = (TextView) itemLayoutView.findViewById(R.id.feed_likesCount);
            feed_commentCount = (TextView) itemLayoutView.findViewById(R.id.feed_commentCount);


            feed_full_picture.setOnClickListener(this);
            feed_likeIcon.setOnClickListener(this);
            feed_likesCount.setOnClickListener(this);
            feed_commentIcon.setOnClickListener(this);
            feed_commentCount.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            Utility.logStatus("onClick()  v.getId()" + v.getId());


            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
                if (v.getId() == feed_likeIcon.getId()) {
                    //Change Text Color
                    feed_likeIcon.setColor(v.getContext().getResources().getColor(R.color.com_facebook_blue));
                } else if (v.getId() == feed_commentIcon.getId()) {

                    //Launch Bottom Layout
                    MainActivity mainActivity = (MainActivity) v.getContext();
                    BandFeedDetailFragmentBottom.newInstance(feedData.get(getAdapterPosition()).getId())
                            .show(mainActivity.getSupportFragmentManager(), R.id.bottomsheet);
                } else if (v.getId() == feed_full_picture.getId()) {
                    MainActivity mainActivity = (MainActivity) v.getContext();
                    mainActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment, BandFeedDetailFragment.newInstance(feedData.get(getAdapterPosition()).getId(),
                                    feedData.get(getAdapterPosition()).getFullPicture(),
                                    feedData.get(getAdapterPosition()).getMessage(), ""))
                            .addToBackStack("BandDetail")
                            .commit();
                }

            }
        }

        public void changeLikeIconColor() {
            IconicsImageView btn = (IconicsImageView) mItemLayoutView.findViewById(R.id.feed_likeIcon);
            btn.setColor(mItemLayoutView.getContext().getResources().getColor(R.color.com_facebook_blue));

        }

    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }
}



