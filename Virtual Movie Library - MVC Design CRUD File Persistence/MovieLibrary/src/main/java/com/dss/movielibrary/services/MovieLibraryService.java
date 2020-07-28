/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.movielibrary.services;

import com.dss.movielibrary.dao.MovieLibraryDao;
import com.dss.movielibrary.dao.MovieLibraryDaoException;
import com.dss.movielibrary.dtos.Movie;
import java.util.List;

/**
 *
 * @author Schmi
 */
public class MovieLibraryService {

    MovieLibraryDao dao;

    public MovieLibraryService(MovieLibraryDao dao) {
        this.dao = dao;
    }

    public Movie addMovie(Movie newMovie) throws MovieLibraryDaoException, BlankTitleException {
        
        if ( newMovie.getTitle().isBlank()) {
            throw new BlankTitleException("The Title cannot be blank");
        }
        
        return dao.addMovie(newMovie);
    }

    public void editMovie(Movie updated) throws MovieLibraryDaoException {
        dao.editMovie(updated);
    }

    public Movie getMovieById(int id) {
        return dao.getMovieById(id);
    }

    public void deleteMovieById(int id) throws MovieLibraryDaoException {
        dao.deleteMovieById(id);
    }

    public List<Movie> getMovie(String title) {
        return dao.getMovie(title);
    }

    public List<Movie> getAllMovies() {
        return dao.getAllMovies();
    }
}
