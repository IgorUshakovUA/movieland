package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.GenreDao;
import com.ushakov.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.ushakov.movieland.entity.Genre;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
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

        Genre genre1 = new Genre(1, "драма");
        expectedGenreList.add(genre1);

        Genre genre2 = new Genre(2, "криминал");
        expectedGenreList.add(genre2);

        Genre genre3 = new Genre(3, "фэнтези");
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

    @Test
    public void testGetGenresByGenreGroupId() {
        // Prepare
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        List<Genre> expectedGenreList = new ArrayList<>();

        Genre genre1 = new Genre(1, "драма");
        expectedGenreList.add(genre1);

        Genre genre2 = new Genre(2, "криминал");
        expectedGenreList.add(genre2);

        // When
        when(jdbcTemplate.query(any(String.class), any(GenreRowMapper.class), any(Integer.class))).thenReturn(Arrays.asList(genre1, genre2));

        // Then
        GenreDao genreDao = new JdbcGenreDao(jdbcTemplate);
        List<Genre> actualGenreList = genreDao.getGenresGenreGroupId(1);

        assertEquals(expectedGenreList, actualGenreList);
    }
}