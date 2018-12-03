package com.ushakov.movieland.service;

import com.ushakov.movieland.common.Credentials;
import com.ushakov.movieland.common.SecurityToken;

public interface SecurityService {
    SecurityToken logon(Credentials credentials);
    SecurityToken logout(String uuid);
    boolean isLoggedOn(String uuid);
}
