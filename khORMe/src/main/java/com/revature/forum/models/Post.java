package com.revature.forum.models;

import com.revature.annotations.Column;
import com.revature.annotations.Table;

import java.util.Date;

@Table(name="Posts")
public class Post {
    @Column(name="id", key="pk")
    public int id;
    @Column(name="thread_id", key="fk")
    public int threadId;
    @Column(name="poster_id", key="fk")
    public int posterId;
    @Column(name="replying_to", key="fk")
    public int replyTo=1;
    @Column(name="body")
    public String body;
    @Column(name="created_time")
    public Date createdTime ;
    @Column(name="up_votes")
    public int upVotes=0;
    @Column(name="down_votes")
    public int downVotes=0;

    public String username;

    public Post(){
        super();
    }


    public Post(int posterId, int threadId, String body){
        this.posterId=posterId;
        this.threadId=threadId;
        this.body=body;
        //createdTime= new Timestamp(Calendar.getInstance().getTime().getTime());
    }



    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDateTime() {
        return createdTime;
    }

    public void setDateTime(Date dateTime) {
        this.createdTime = dateTime;
    }

    public int getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(int replyTo) {
        this.replyTo = replyTo;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getPosterId() {
        return posterId;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }
}
