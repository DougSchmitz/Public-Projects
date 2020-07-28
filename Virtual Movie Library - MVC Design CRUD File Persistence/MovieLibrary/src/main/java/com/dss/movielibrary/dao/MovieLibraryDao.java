/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.movielibrary.dao;

import com.dss.movielibrary.dtos.Movie;
import java.util.List;

/**
 *
 * @author Schmi
 */
public interface MovieLibraryDao {

    public Movie addMovie(Movie newMovie)throws MovieLibraryDaoException;

    public List<Movie> getAllMovies();

    public List<Movie> getMovie(String title);

    public Movie getMovieById(int id);

    public void deleteMovieById(int id)throws MovieLibraryDaoException;

    public Movie editMovie(Movie newMovie)throws MovieLibraryDaoException;

    
}
