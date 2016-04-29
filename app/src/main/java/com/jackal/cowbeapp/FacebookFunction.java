package com.jackal.cowbeapp;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.jackal.cowbeapp.utility.Utility;

/**
 * Created by jackalkao on 4/26/16.
 */
public class FacebookFunction {

    public static void AddLikeFeed(String ObjectId) {
        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + ObjectId + "/likes",
                null,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                        Utility.logStatus("onCompleted" + response.getRawResponse() );
                    }
                }
        ).executeAsync();
    }

    public static void DeleteLikeFeed(String ObjectId) {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + ObjectId + "/likes",
                null,
                HttpMethod.DELETE,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                        Utility.logStatus("onCompleted" + response.getRawResponse());

                    }
                }
        ).executeAsync();
    }


    public static void PostComment(String ObjectId, String comment) {
        Bundle params = new Bundle();
        params.putString("message", comment);
        /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/" + ObjectId + "/comments",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        /* handle the result */
                        Utility.logStatus("onCompleted " + response.getRawResponse());
                    }
                }
        ).executeAsync();
    }
}
