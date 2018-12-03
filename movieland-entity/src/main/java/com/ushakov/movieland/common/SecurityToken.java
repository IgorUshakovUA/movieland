package com.ushakov.movieland.common;

import java.util.Objects;

public class SecurityToken {
    private String uuid;
    private String nickName;

    public SecurityToken(String uuid, String nickName) {
        this.uuid = uuid;
        this.nickName = nickName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityToken that = (SecurityToken) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(nickName, that.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, nickName);
    }
}
