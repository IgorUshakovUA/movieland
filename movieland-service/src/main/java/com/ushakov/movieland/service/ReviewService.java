package com.ushakov.movieland.service;

import com.ushakov.movieland.entity.Review;
import com.ushakov.movieland.entity.User;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewsByMovieId(int movieId);
    Review addReview(int movieId, User user, String text);
}
