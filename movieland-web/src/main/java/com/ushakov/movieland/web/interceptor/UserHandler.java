package com.ushakov.movieland.web.interceptor;

import com.ushakov.movieland.entity.User;

public class UserHandler {
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static User getCurrentUser() {
        return currentUser.get();
    }
}
