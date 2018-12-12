package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.common.ReviewRequest;
import com.ushakov.movieland.dao.ReviewDao;
import com.ushakov.movieland.dao.jdbc.mapper.ReviewRowMapper;
import com.ushakov.movieland.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcReviewDao implements ReviewDao {
    private static final String GET_REVIEWS_BY_MOVIE_ID_SQL = "SELECT review.id, review.userId, review.comment, app_user.nickName FROM review, app_user WHERE app_user.id = review.userId AND movieId = ?";
    private static final String GET_NEW_REVIEW_ID_SQL = "SELECT nextval('seq_review')";
    private static final String ADD_REVIEW_SQL = "INSERT INTO review (id, movieId, userId, comment) VALUES(?, ?, ?, ?)";
    private static final ReviewRowMapper REVIEW_ROW_MAPPER = new ReviewRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcReviewDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public List<Review> getReviewsByMovieId(int movieId) {
        List<Review> reviewList = jdbcTemplate.query(GET_REVIEWS_BY_MOVIE_ID_SQL, REVIEW_ROW_MAPPER, movieId);

        logger.debug("Reviews by movieId = {}, size: {}", movieId, reviewList.size());
        logger.trace("Reviews: {}", reviewList);

        return reviewList;
    }

    @Override
    public Review addReview(ReviewRequest reviewRequest) {
        Integer id = jdbcTemplate.queryForObject(GET_NEW_REVIEW_ID_SQL, Integer.class);

        jdbcTemplate.update(ADD_REVIEW_SQL, id, reviewRequest.getMovieId(), reviewRequest.getUser().getId(), reviewRequest.getText());

        Review review = new Review(id, reviewRequest.getUser(), reviewRequest.getText());

        logger.debug("New review was added for movieId: {}", reviewRequest.getMovieId());
        logger.trace("Review: {}", review);

        return review;
    }


}
