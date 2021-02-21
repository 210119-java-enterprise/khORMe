package com.revature.forum.models;

import com.revature.annotations.Column;
import com.revature.annotations.Table;

@Table(name="Messages")
public class Message {
    @Column(name="id", key="pk")
    public int id;
    @Column(name="creator_id", key="fk")
    public int creatorId;
    @Column(name="subject")
    public String subject;
    @Column(name="body")
    public String body;
    @Column(name="send_datetime")
    public String dateTime;

}