package com.ushakov.movieland.service;

import com.ushakov.movieland.dao.GenreDao;
import com.ushakov.movieland.entity.Genre;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultGenreServiceTest {

    @Test
    public void testGetAll() {
        // Preparation
        GenreDao genreDao = mock(GenreDao.class);

        GenreService genreService = new DefaultGenreService(genreDao);

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
        when(genreDao.getAll()).thenReturn(expectedGenreList);

        // Then
        List<Genre> actualGenreList = genreService.getAll();

        assertEquals(expectedGenreList.size(),actualGenreList.size());

        for (Genre expectedGenre : expectedGenreList) {
            assertTrue(actualGenreList.indexOf(expectedGenre) > -1);
        }
    }

}