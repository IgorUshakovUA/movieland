package com.ushakov.movieland.service;

import com.ushakov.movieland.common.ReviewRequest;
import com.ushakov.movieland.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewsByMovieId(int movieId);
    Review addReview(ReviewRequest reviewRequest);
}
