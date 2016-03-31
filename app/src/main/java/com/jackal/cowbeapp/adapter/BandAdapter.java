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

import com.jackal.cowbeapp.MainActivity;
import com.jackal.cowbeapp.R;
import com.jackal.cowbeapp.app.AppController;
import com.jackal.cowbeapp.fragment.BandFeedFragment;

import java.util.ArrayList;


/**
 * Created by jackalkao on 2/24/16.
 */
public class BandAdapter extends RecyclerView.Adapter<BandAdapter.ViewHolder> {
    private ArrayList<Band> bands;
    private Context context;

    public BandAdapter(ArrayList<Band> bands) {
        this.bands = bands;

    }

    @Override
    public int getItemCount() {
        return bands.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_browse_categories, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView, bands);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.txt_bandName.setText(bands.get(position).getName());


        String url = bands.get(position).getPicture().getData().getUrl();
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        holder.txt_bandPicture.setImageUrl(url, imageLoader);
        holder.txt_bandPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //call MainActivity switch fragment
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, BandFeedFragment.newInstance( bands.get(position).getId()))
                        .addToBackStack("Band")
                        .commit();

            }
        });


    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_bandName;

        NetworkImageView txt_bandPicture;

        ArrayList<Band> bands;


        public ViewHolder(View itemLayoutView, ArrayList<Band> bands) {
            super(itemLayoutView);
            this.bands = bands;
            txt_bandName = (TextView) itemLayoutView.findViewById(R.id.item_title);
            txt_bandPicture = (NetworkImageView) itemLayoutView.findViewById(R.id.item_icon);

        }

        @Override
        public void onClick(View view) {

        }
    }


}
