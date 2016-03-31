package com.jackal.cowbeapp;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by jackalkao on 2/24/16.
 */
public class CustomRecyclerView {
    public static void setLayoutManager(Context context,RecyclerView recyclerView, String Direct) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        if (Direct == "HORIZONTAL") {
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            layoutManager.scrollToPosition(0);
            recyclerView.setLayoutManager(layoutManager);
        } else if (Direct == "VERTICAL") {
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutManager.scrollToPosition(0);
            recyclerView.setLayoutManager(layoutManager);
        } else if (Direct == "GRID") {
            recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        }

    }
}
