package com.ushakov.movieland.service;

import com.ushakov.movieland.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewsByMovieId(int movieId);
}
