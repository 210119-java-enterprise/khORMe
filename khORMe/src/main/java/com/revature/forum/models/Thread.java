package com.revature.forum.models;

import com.revature.annotations.Column;
import com.revature.annotations.ForeignKey;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Table;

@Table(name="threads")
public class Thread {
    @PrimaryKey(name="id")
    public int id;
    @Column(name="title")
    public String title;
    @Column(name="created_time")
    public String createdTime;
    @Column(name="up_votes")
    public int upVotes;
    @Column(name="down_votes")
    public int downVotes;
    @Column(name="is_locked")
    public boolean isLocked;
    @Column(name="is_sticky")
    public boolean isSticky;
    @ForeignKey(name="board_id")
    public int boardId;
    @ForeignKey(name="creator_id")
    public int creatorId;
}
