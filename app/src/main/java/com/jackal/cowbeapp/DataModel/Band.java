package com.jackal.cowbeapp.DataModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jackalkao on 2/24/16.
 */
public class Band {

    private Cover cover;
    private Engagement engagement;
    private String name;
    private Feed feed;
    private Picture picture;
    private String id;


    public Cover getCover() { return this.cover; }

    public Engagement getEngagement() { return this.engagement; }

    public String getName() { return this.name; }

    public Feed getFeed() { return this.feed; }

    public String getId() { return this.id; }


    public Picture getPicture() { return this.picture; }

    public class Cover
    {
        private String cover_id;

        public String getCoverId() { return this.cover_id; }

        public void setCoverId(String cover_id) { this.cover_id = cover_id; }

        private int offset_x;

        public int getOffsetX() { return this.offset_x; }

        public void setOffsetX(int offset_x) { this.offset_x = offset_x; }

        private int offset_y;

        public int getOffsetY() { return this.offset_y; }

        public void setOffsetY(int offset_y) { this.offset_y = offset_y; }

        private String source;

        public String getSource() { return this.source; }

        public void setSource(String source) { this.source = source; }

        private String id;

        public String getId() { return this.id; }

        public void setId(String id) { this.id = id; }
    }

    public class Engagement
    {
        private int count;

        public int getCount() { return this.count; }

        public void setCount(int count) { this.count = count; }

        private String social_sentence;

        public String getSocialSentence() { return this.social_sentence; }

        public void setSocialSentence(String social_sentence) { this.social_sentence = social_sentence; }
    }

    public class Summary
    {
        private int total_count;

        public int getTotalCount() { return this.total_count; }

        public void setTotalCount(int total_count) { this.total_count = total_count; }

        private boolean can_like;

        public boolean getCanLike() { return this.can_like; }

        public void setCanLike(boolean can_like) { this.can_like = can_like; }

        private boolean has_liked;

        public boolean getHasLiked() { return this.has_liked; }

        public void setHasLiked(boolean has_liked) { this.has_liked = has_liked; }
    }

    public class Likes
    {
        private ArrayList<Object> data;

        public ArrayList<Object> getData() { return this.data; }

        public void setData(ArrayList<Object> data) { this.data = data; }

        private Summary summary;

        public Summary getSummary() { return this.summary; }

        public void setSummary(Summary summary) { this.summary = summary; }
    }

    public class Summary2
    {
        private String order;

        public String getOrder() { return this.order; }

        public void setOrder(String order) { this.order = order; }

        private int total_count;

        public int getTotalCount() { return this.total_count; }

        public void setTotalCount(int total_count) { this.total_count = total_count; }

        private boolean can_comment;

        public boolean getCanComment() { return this.can_comment; }

        public void setCanComment(boolean can_comment) { this.can_comment = can_comment; }
    }

    public class Comments
    {
        private ArrayList<Object> data;

        public ArrayList<Object> getData() { return this.data; }

        public void setData(ArrayList<Object> data) { this.data = data; }

        private Summary2 summary;

        public Summary2 getSummary() { return this.summary; }

        public void setSummary(Summary2 summary) { this.summary = summary; }
    }

    public class Data
    {
        private String picture;
        private String link;
        private String full_picture;
        private String message;
        private String created_time;
        private String id;
        private Likes likes;
        private Comments comments;

        public String getCreated_time(){ return this.created_time;}

        public void setCreated_time(String created_time){  this.created_time = created_time;}

        public String getPicture() { return this.picture; }

        public void setPicture(String picture) { this.picture = picture; }


        public String getLink() { return this.link; }

        public void setLink(String link) { this.link = link; }


        public String getFullPicture() { return this.full_picture; }

        public void setFullPicture(String full_picture) { this.full_picture = full_picture; }


        public String getMessage() { return this.message; }

        public void setMessage(String message) { this.message = message; }


        public String getId() { return this.id; }

        public void setId(String id) { this.id = id; }


        public Likes getLikes() { return this.likes; }

        public void setLikes(Likes likes) { this.likes = likes; }



        public Comments getComments() { return this.comments; }

        public void setComments(Comments comments) { this.comments = comments; }
    }

    public class Paging
    {
        private String previous;

        public String getPrevious() { return this.previous; }

        public void setPrevious(String previous) { this.previous = previous; }

        private String next;

        public String getNext() { return this.next; }

        public void setNext(String next) { this.next = next; }
    }

    public class PictureData
    {
        private String url;

        public String getUrl() { return this.url; }

        public void setUrl(String url) { this.url = url; }
    }
    public class Feed
    {
        private ArrayList<Data> data;

        public ArrayList<Data> getData() { return this.data; }

        public void setData(ArrayList<Data> data) { this.data = data; }

        private Paging paging;

        public Paging getPaging() { return this.paging; }

        public void setPaging(Paging paging) { this.paging = paging; }
    }
    public class Picture
    {

        @SerializedName("data")
        private PictureData data;

        public PictureData getData() { return this.data; }

        public void setData(PictureData data) { this.data = data; }
    }
}
