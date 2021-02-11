package com.revature.forum.models;

import com.revature.annotations.Table;

@Table(name="user_roles")
public enum UserRole {
    ADMIN, BASIC_USER, MODERATOR
}
