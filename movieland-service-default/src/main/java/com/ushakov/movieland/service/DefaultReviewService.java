package com.ushakov.movieland.service;

import com.ushakov.movieland.dao.ReviewDao;
import com.ushakov.movieland.entity.Review;
import com.ushakov.movieland.entity.User;
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
    public Review addReview(int movieId, User user, String text) {
        return reviewDao.addReview(movieId, user, text);
    }
}
