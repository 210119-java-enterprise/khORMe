package com.revature.forum.models;

import com.revature.annotations.Column;
import com.revature.annotations.ForeignKey;
import com.revature.annotations.PrimaryKey;
import com.revature.annotations.Table;

@Table(name="Threads")
public class Thread {
    @PrimaryKey(name="id")
    int id;
    @Column(name="title")
    String title;
    @Column(name="created_time")
    String createdTime;
    @Column(name="up_votes")
    int upVotes;
    @Column(name="down_votes")
    int downVotes;
    @Column(name="is_locked")
    boolean isLocked;
    @Column(name="is_sticky")
    boolean isSticky;
    @ForeignKey(name="board_id")
    int boardId;
    @ForeignKey(name="creator_id")
    int creatorId;
}
