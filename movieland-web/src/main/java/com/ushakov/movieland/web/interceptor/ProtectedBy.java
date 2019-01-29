package com.ushakov.movieland.web.interceptor;

import com.ushakov.movieland.common.UserRole;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ProtectedBy {
    UserRole[] value();
}
