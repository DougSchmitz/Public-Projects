/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.movielibrary.dao;

import com.dss.movielibrary.dtos.Movie;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Schmi
 */
public class MovieLibraryInMemDao implements MovieLibraryDao {

    List<Movie> allMovies = new ArrayList();

    @Override
    public Movie addMovie(Movie newMovie) throws MovieLibraryDaoException {

        int maxId = 0;
        for (Movie m : allMovies) {
            if (m.getMovieId() > maxId) {
                maxId = m.getMovieId();
            } else {
                break;
            }
        }
        newMovie.setMovieId(maxId + 1);
        allMovies.add(newMovie);

        return newMovie;
    }

    @Override
    public List<Movie> getMovie(String title) {

        List<Movie> matchingMovies = new ArrayList<>();

        for (Movie toCheck : allMovies) {
            if (toCheck.getTitle().equalsIgnoreCase(title)) {
                matchingMovies.add(toCheck);
            }
        }
        return matchingMovies;
    }

    @Override
    public Movie getMovieById(int id) {
        Movie matchingMovie = new Movie();

        for (Movie toCheck : allMovies) {
            if (toCheck.getMovieId() == id) {
                matchingMovie = toCheck;
            }
        }
        return matchingMovie;
    }

    @Override
    public List<Movie> getAllMovies() {

        return allMovies;
    }

    @Override
    public void deleteMovieById(int id) throws MovieLibraryDaoException {

        int index = -1;

        for (int i = 0; i < allMovies.size(); i++) {
            Movie toCheck = allMovies.get(i);
            if (toCheck.getMovieId() == id) {
                index = i;
                break;
            }
        }
        allMovies.remove(index);
    }

    @Override
    public Movie editMovie(Movie newMovie) throws MovieLibraryDaoException {

        Movie editedMovie = new Movie();

        int index = -1;

        for (int i = 0; i < allMovies.size(); i++) {

            Movie toCheck = allMovies.get(i);

            if (toCheck.getMovieId() == newMovie.getMovieId()) {
                index = i;
                editedMovie = newMovie;
                break;
            }
        }
        //if index = -1 throw a ClassDaoException
        allMovies.set(index, newMovie);

        return editedMovie;
    }
}
