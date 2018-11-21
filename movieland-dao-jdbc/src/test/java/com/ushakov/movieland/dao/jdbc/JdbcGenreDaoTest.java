package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.ushakov.movieland.entity.Genre;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdbcGenreDaoTest {

    @Test
    public void testGetAll() {
        // Preparations
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        JdbcGenreDao genreDao = new JdbcGenreDao(jdbcTemplate);

        List<Genre> expectedGenreList = new ArrayList<>();

        Genre genre1 = new Genre();
        genre1.setId(1);
        genre1.setName("драма");
        expectedGenreList.add(genre1);

        Genre genre2 = new Genre();
        genre2.setId(2);
        genre2.setName("криминал");
        expectedGenreList.add(genre2);

        Genre genre3 = new Genre();
        genre3.setId(3);
        genre3.setName("фэнтези");
        expectedGenreList.add(genre3);

        // When
        when(jdbcTemplate.query(any(String.class), any(GenreRowMapper.class))).thenReturn(expectedGenreList);

        // Then
        List<Genre> actualGenreList = genreDao.getAll();

        assertEquals(expectedGenreList.size(), actualGenreList.size());

        for (Genre expectedGenre : expectedGenreList) {
            assertTrue(actualGenreList.indexOf(expectedGenre) > -1);
        }
    }
}