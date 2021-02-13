package com.revature.forum.models;

import com.revature.annotations.Column;
import com.revature.annotations.ForeignKey;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Table;

@Table(name="posts")
public class Post {
    @PrimaryKey(name="id")
    public int id;
    @Column(name="body")
    public String body;
    @Column(name="created_datetime")
    public String dateTime;
    @ForeignKey(name="replying_to")
    public int replyTo;
    @ForeignKey(name="thread_id")
    public int threadId;
    @ForeignKey(name="poster_id")
    public int posterId;
}
