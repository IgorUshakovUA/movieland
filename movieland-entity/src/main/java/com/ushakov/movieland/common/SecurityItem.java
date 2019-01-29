package com.ushakov.movieland.common;

import java.time.LocalDateTime;
import java.util.Objects;

public class SecurityItem {
    private SecurityToken securityToken;
    private Credentials credentials;
    private LocalDateTime created;

    public SecurityItem(SecurityToken securityToken, Credentials credentials) {
        this.securityToken = securityToken;
        this.credentials = credentials;
        created = LocalDateTime.now();
    }

    public SecurityToken getSecurityToken() {
        return securityToken;
    }

    public Credentials getCredentials() {
        return credentials;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityItem that = (SecurityItem) o;
        return Objects.equals(securityToken, that.securityToken) &&
                Objects.equals(credentials, that.credentials) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(securityToken, credentials, created);
    }
}
