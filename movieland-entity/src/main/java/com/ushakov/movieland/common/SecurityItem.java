package com.ushakov.movieland.common;

import java.time.LocalDateTime;
import java.util.Objects;

public class SecurityItem {
    private SecurityToken securityToken;
    private LocalDateTime created;
    private volatile boolean isAlive;

    public SecurityItem(SecurityToken securityToken) {
        this.securityToken = securityToken;
        created = LocalDateTime.now();
        isAlive = true;
    }

    public SecurityToken getSecurityToken() {
        return securityToken;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getUuid() {
        return securityToken.getUuid();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityItem that = (SecurityItem) o;
        return Objects.equals(securityToken, that.securityToken) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(securityToken, created);
    }
}
