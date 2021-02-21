package com.revature.forum.models;

import com.revature.annotations.Column;
import com.revature.annotations.Table;

@Table(name="Threads")
public class Thread {
    @Column(name="id", key="pk")
    public int id;
    @Column(name="creator_id", key="fk")
    public int creatorId;
    @Column(name="board_id", key="fk")
    public int boardId;
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

    public Thread() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
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

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isSticky() {
        return isSticky;
    }

    public void setSticky(boolean sticky) {
        isSticky = sticky;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

}
