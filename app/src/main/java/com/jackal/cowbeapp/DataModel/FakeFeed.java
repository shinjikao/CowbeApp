package com.jackal.cowbeapp.DataModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jackalkao on 3/8/16.
 */
public class FakeFeed {
    private ArrayList<Datum> data;

    public ArrayList<Datum> getData() { return this.data; }

    public void setData(ArrayList<Datum> data) { this.data = data; }

    private Paging paging;

    public Paging getPaging() { return this.paging; }

    public void setPaging(Paging paging) { this.paging = paging; }


    public class Summary
    {
        private boolean can_like;

        public boolean getCanLike() { return this.can_like; }

        public void setCanLike(boolean can_like) { this.can_like = can_like; }

        private int total_count;

        public int getTotalCount() { return this.total_count; }

        public void setTotalCount(int total_count) { this.total_count = total_count; }

        private boolean has_liked;

        public boolean getHasLiked() { return this.has_liked; }

        public void setHasLiked(boolean has_liked) { this.has_liked = has_liked; }
    }

    public class Likes
    {
        private Summary summary;

        public Summary getSummary() { return this.summary; }

        public void setSummary(Summary summary) { this.summary = summary; }

        private ArrayList<Object> data;

        public ArrayList<Object> getData() { return this.data; }

        public void setData(ArrayList<Object> data) { this.data = data; }
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
        private Summary2 summary;

        public Summary2 getSummary() { return this.summary; }

        public void setSummary(Summary2 summary) { this.summary = summary; }

        private ArrayList<Object> data;

        public ArrayList<Object> getData() { return this.data; }

        public void setData(ArrayList<Object> data) { this.data = data; }
    }

    public class Datum
    {
        private String id;
        private String message;
        private String picture;
        private String full_picture;
        private Likes likes;
        private String link;
        private String created_time;
        private Comments comments;

        public String getId() { return this.id; }

        public void setId(String id) { this.id = id; }


        public String getMessage() { return this.message; }

        public void setMessage(String message) { this.message = message; }


        public String getPicture() { return this.picture; }

        public void setPicture(String picture) { this.picture = picture; }


        public String getFullPicture() { return this.full_picture; }

        public void setFullPicture(String full_picture) { this.full_picture = full_picture; }


        public Likes getLikes() { return this.likes; }

        public void setLikes(Likes likes) { this.likes = likes; }


        public String getLink() { return this.link; }

        public void setLink(String link) { this.link = link; }


        public String getCreatedTime() { return this.created_time; }

        public void setCreatedTime(String created_time) { this.created_time = created_time; }



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



}
