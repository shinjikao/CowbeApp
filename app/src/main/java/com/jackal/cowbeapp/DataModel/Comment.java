package com.jackal.cowbeapp.DataModel;

import java.util.ArrayList;

/**
 * Created by jackalkao on 2/28/16.
 */
public class Comment {
    private String full_picture;
    private Comments comments;
    private String id;
    private String message;
    private Haha haha;
    private Love love;
    private Wow wow;
    private Like like;
    private Sad sad;
    private Angry angry;




    public String getFullPicture() { return this.full_picture; }
    public Comments getComments() { return this.comments; }
    public String getId() { return this.id; }
    public String getMessage(){return this.message;}
    public Haha getHaha() { return this.haha; }
    public Love getLove() { return this.love; }
    public Wow getWow() { return this.wow; }
    public Like getLike() { return this.like; }
    public Sad getSad() { return this.sad; }
    public Angry getAngry() { return this.angry; }

    public class Data
    {
        private boolean is_silhouette;
        private String url;

        public boolean getIsSilhouette() { return this.is_silhouette; }
        public String getUrl() { return this.url; }


    }

    public class Picture
    {
        private Data data;

        public Data getData() { return this.data; }


    }

    public class From
    {


        private Picture picture;
        private String id;
        private String name;

        public Picture getPicture() { return this.picture; }
        public String getId() { return this.id; }
        public String getName() { return this.name; }


    }

    public class Datum
    {
        private From from;
        private String message;
        private boolean user_likes;
        private int comment_count;
        private int like_count;
        private String id;
        public From getFrom() { return this.from; }

        public String getMessage() { return this.message; }

        public boolean getUserLikes() { return this.user_likes; }

        public int getCommentCount() { return this.comment_count; }

        public int getLikeCount() { return this.like_count; }

        public String getId() { return this.id; }


    }

    public class Cursors
    {
        private String after;
        private String before;

        public String getAfter() { return this.after; }

        public String getBefore() { return this.before; }


    }

    public class Paging
    {
        private Cursors cursors;

        private String next;

        public Cursors getCursors() { return this.cursors; }

        public String getNext() { return this.next; }


    }

    public class Comments
    {
        private ArrayList<Datum> data;
        private Paging paging;

        public ArrayList<Datum> getData() { return this.data; }

        public Paging getPaging() { return this.paging; }


    }

    public class Summary
    {
        private int total_count;
        private String viewer_reaction;

        public int getTotalCount() { return this.total_count; }

        public void setTotalCount(int total_count) { this.total_count = total_count; }

        public String getViewerReaction() { return this.viewer_reaction; }

        public void setViewerReaction(String viewer_reaction) { this.viewer_reaction = viewer_reaction; }
    }
    public class Haha
    {
        private ArrayList<Object> data;

        public ArrayList<Object> getData() { return this.data; }

        public void setData(ArrayList<Object> data) { this.data = data; }

        private Summary summary;

        public Summary getSummary() { return this.summary; }

        public void setSummary(Summary summary) { this.summary = summary; }
    }
    public class Love
    {
        private ArrayList<Object> data;

        public ArrayList<Object> getData() { return this.data; }

        public void setData(ArrayList<Object> data) { this.data = data; }

        private Summary summary;

        public Summary getSummary() { return this.summary; }

        public void setSummary(Summary summary) { this.summary = summary; }
    }
    public class Wow
    {
        private ArrayList<Object> data;

        public ArrayList<Object> getData() { return this.data; }

        public void setData(ArrayList<Object> data) { this.data = data; }

        private Summary summary;

        public Summary getSummary() { return this.summary; }

        public void setSummary(Summary summary) { this.summary = summary; }
    }
    public class Like
    {
        private ArrayList<Object> data;

        public ArrayList<Object> getData() { return this.data; }

        public void setData(ArrayList<Object> data) { this.data = data; }

        private Summary summary;

        public Summary getSummary() { return this.summary; }

        public void setSummary(Summary summary) { this.summary = summary; }
    }
    public class Sad
    {
        private ArrayList<Object> data;

        public ArrayList<Object> getData() { return this.data; }

        public void setData(ArrayList<Object> data) { this.data = data; }

        private Summary summary;

        public Summary getSummary() { return this.summary; }

        public void setSummary(Summary summary) { this.summary = summary; }
    }
    public class Angry
    {
        private ArrayList<Object> data;

        public ArrayList<Object> getData() { return this.data; }

        public void setData(ArrayList<Object> data) { this.data = data; }

        private Summary summary;

        public Summary getSummary() { return this.summary; }

        public void setSummary(Summary summary) { this.summary = summary; }
    }
}
