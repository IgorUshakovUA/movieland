package com.ushakov.movieland.service;

import com.ushakov.movieland.common.ReviewRequest;
import com.ushakov.movieland.dao.ReviewDao;
import com.ushakov.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultReviewService implements ReviewService {
    private ReviewDao reviewDao;

    @Autowired
    public DefaultReviewService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public List<Review> getReviewsByMovieId(int movieId) {
        return reviewDao.getReviewsByMovieId(movieId);
    }

    @Override
    public Review addReview(ReviewRequest reviewRequest) {
        return reviewDao.addReview(reviewRequest);
    }
}
