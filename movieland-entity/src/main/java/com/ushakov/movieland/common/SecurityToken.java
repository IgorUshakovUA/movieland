package com.ushakov.movieland.common;

import com.ushakov.movieland.entity.User;

import java.util.Objects;

public class SecurityToken {
    private String uuid;
    private String nickName;
    private UserRole userRole;
    private int id;

    public SecurityToken(String uuid, String nickName, UserRole userRole, int id) {
        this.uuid = uuid;
        this.nickName = nickName;
        this.userRole = userRole;
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getNickName() {
        return nickName;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public User getUser() {
        return new User(id, nickName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityToken that = (SecurityToken) o;
        return id == that.id &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(nickName, that.nickName) &&
                userRole == that.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, nickName, userRole, id);
    }
}
