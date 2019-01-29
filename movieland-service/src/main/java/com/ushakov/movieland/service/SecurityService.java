package com.ushakov.movieland.service;

import com.ushakov.movieland.common.Credentials;
import com.ushakov.movieland.common.SecurityToken;
import com.ushakov.movieland.common.UserRole;
import com.ushakov.movieland.entity.User;

public interface SecurityService {
    SecurityToken logon(Credentials credentials);
    SecurityToken logout(String uuid);
    boolean isLoggedOn(String uuid);
    String getEmail(String uuid);
    UserRole getUserRole(String uuid);
    User getUser(String uuid);
}
