package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.GenreDao;
import com.ushakov.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.ushakov.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private static final String GET_ALL_SQL = "SELECT id, name FROM genre";
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcGenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genreList = jdbcTemplate.query(GET_ALL_SQL, GENRE_ROW_MAPPER);

        logger.trace("Genres: {}", genreList);

        return genreList;
    }
}
