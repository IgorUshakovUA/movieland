package com.ushakov.movieland.dao;

import com.ushakov.movieland.common.ReviewRequest;
import com.ushakov.movieland.entity.Review;

import java.util.List;

public interface ReviewDao {
    List<Review> getReviewsByMovieId(int movieId);
    Review addReview(ReviewRequest reviewRequest);
}
