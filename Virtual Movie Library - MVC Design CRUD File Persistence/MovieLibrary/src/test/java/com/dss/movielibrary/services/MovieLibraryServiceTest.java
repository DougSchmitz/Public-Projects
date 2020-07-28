/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.movielibrary.services;

import com.dss.movielibrary.dao.MovieLibraryDaoException;
import com.dss.movielibrary.dao.MovieLibraryInMemDao;
import com.dss.movielibrary.dtos.Movie;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Schmi
 */
public class MovieLibraryServiceTest {

    public MovieLibraryServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addMovie method, of class MovieLibraryService.
     */
    @Test
    public void testAddMovieGoldenPath() {

        MovieLibraryService toTest = new MovieLibraryService(new MovieLibraryInMemDao());
        LocalDate date = LocalDate.of(2020,01,01);
        
        Movie toAdd = new Movie();
        toAdd.setTitle("Gun Slinger");
        toAdd.setDirectorName("Eastwood");
        toAdd.setMpaaRating("R");
        toAdd.setReleaseDate(date);
        toAdd.setStudio("Horse Studios");
        toAdd.setUserRating(5);

        
        //because id# is created in DaoFileImpl should I manually add a id#?
        Movie toAdd2 = new Movie();
        toAdd2.setTitle("Second");
        toAdd2.setDirectorName("Eastwood");
        toAdd2.setMpaaRating("R");
        toAdd2.setReleaseDate(date);
        toAdd2.setStudio("Horse Studios");
        toAdd2.setUserRating(5);

        try {

            Movie returned = toTest.addMovie(toAdd);
            
            assertEquals(1, returned.getMovieId());
            assertEquals("Gun Slinger", returned.getTitle());
            assertEquals("Eastwood", returned.getDirectorName());
            assertEquals("R", returned.getMpaaRating());
            assertEquals(date, returned.getReleaseDate());
            assertEquals("Horse Studios", returned.getStudio());
            assertEquals(5, returned.getUserRating());

            Movie saveValidation = toTest.getMovieById(1);

            assertEquals(1, saveValidation.getMovieId());
            assertEquals("Gun Slinger", saveValidation.getTitle());
            assertEquals("Eastwood", saveValidation.getDirectorName());
            assertEquals("R", saveValidation.getMpaaRating());
            assertEquals(date, saveValidation.getReleaseDate());
            assertEquals("Horse Studios", saveValidation.getStudio());
            assertEquals(5, saveValidation.getUserRating());

            Movie returned2 = toTest.addMovie(toAdd2);

            assertEquals(2, returned2.getMovieId());
            assertEquals("Second", returned2.getTitle());
            assertEquals("Eastwood", returned2.getDirectorName());
            assertEquals("R", returned2.getMpaaRating());
            assertEquals(date, returned2.getReleaseDate());
            assertEquals("Horse Studios", returned2.getStudio());
            assertEquals(5, returned2.getUserRating());

            Movie saveValidation2 = toTest.getMovieById(2);

            assertEquals(2, saveValidation2.getMovieId());
            assertEquals("Second", saveValidation2.getTitle());
            assertEquals("Eastwood", saveValidation2.getDirectorName());
            assertEquals("R", saveValidation2.getMpaaRating());
            assertEquals(date, saveValidation2.getReleaseDate());
            assertEquals("Horse Studios", saveValidation2.getStudio());
            assertEquals(5, saveValidation2.getUserRating());
            
        } catch (MovieLibraryDaoException ex) {
            fail( "Should not hit MovieLibraryDaoException during golden path test." );
        } catch (BlankTitleException ex) {
            fail("Should not hit BlankTitleException during golden path test.");
        }

    }
    
    @Test
    public void testAddMovieBlankTitle() {

        MovieLibraryService toTest = new MovieLibraryService(new MovieLibraryInMemDao());

        Movie toAdd = new Movie();
        toAdd.setTitle("");
        toAdd.setDirectorName("Eastwood");
        toAdd.setMpaaRating("R");
        toAdd.setReleaseDate(LocalDate.parse("2020-01-01"));
        toAdd.setStudio("Horse Studios");
        toAdd.setUserRating(5);

        try {

            toTest.addMovie(toAdd);
            
            fail();
            
        } catch (MovieLibraryDaoException ex) {
            fail();
        } catch (BlankTitleException ex) {

        }

    }

    /**
     * Test of editMovie method, of class MovieLibraryService.
     */
    @Test
    public void testEditMovie() {
    }

    /**
     * Test of getMovieById method, of class MovieLibraryService.
     */
    @Test
    public void testGetMovieById() {
    }

    /**
     * Test of deleteMovieById method, of class MovieLibraryService.
     */
    @Test
    public void testDeleteMovieById() {
    }

    /**
     * Test of getMovie method, of class MovieLibraryService.
     */
    @Test
    public void testGetMovie() {
    }

    /**
     * Test of getAllMovies method, of class MovieLibraryService.
     */
    @Test
    public void testGetAllMovies() {
    }

}
