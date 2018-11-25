package com.ushakov.movieland.dao.jdbc;

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
    private static final String GET_REVIEWS_BY_MOVIE_ID = "SELECT id, userId, comment FROM review WHERE movieId = ?";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;
    private ReviewRowMapper reviewRowMapper;

    @Autowired
    public JdbcReviewDao(JdbcTemplate jdbcTemplate, ReviewRowMapper reviewRowMapper) {
        this.reviewRowMapper = reviewRowMapper;
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public List<Review> getReviewesByMovieId(int movieId) {
        List<Review> reviewList = jdbcTemplate.query(GET_REVIEWS_BY_MOVIE_ID, reviewRowMapper, movieId);

        logger.debug("Reviews by movieId = {}, size: {}", movieId, reviewList.size());
        logger.trace("Reviews: {}", reviewList);

        return reviewList;
    }
}
