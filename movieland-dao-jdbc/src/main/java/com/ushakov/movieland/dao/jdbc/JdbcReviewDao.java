package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.ReviewDao;
import com.ushakov.movieland.dao.jdbc.mapper.ReviewRowMapper;
import com.ushakov.movieland.entity.Review;
import com.ushakov.movieland.entity.User;
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
    public Review addReview(int movieId, User user, String text) {
        Integer id = jdbcTemplate.queryForObject(GET_NEW_REVIEW_ID_SQL, Integer.class);

        int rowsInserted = jdbcTemplate.update(ADD_REVIEW_SQL, id, movieId, user.getId(), text);
        if (rowsInserted == 1) {
            Review review = new Review(id, user, text);

            logger.debug("New review was added for movieId: {}", movieId);
            logger.trace("Review: {}", review);

            return review;
        } else {
            logger.warn("Fail to add new Review for movieId: {}", movieId);

            return null;
        }
    }


}
