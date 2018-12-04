package com.ushakov.movieland.dao;

import com.ushakov.movieland.common.Credentials;
import com.ushakov.movieland.common.SecurityToken;

public interface SecurityDao {
    SecurityToken logon(Credentials credentials);
}
