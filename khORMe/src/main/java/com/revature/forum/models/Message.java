package com.revature.forum.models;

import com.revature.annotations.Column;
import com.revature.annotations.ForeignKey;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Table;

@Table(name="Messages")
public class Message {
    @PrimaryKey(name="id")
    public int id;
    @Column(name="subject")
    public String subject;
    @Column(name="body")
    public String body;
    @Column(name="send_datetime")
    public String dateTime;
    @ForeignKey(name="sender_id")
    public int creator;
}
