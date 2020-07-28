/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.movielibrary.controller;

import com.dss.movielibrary.dao.MovieLibraryDaoException;
import com.dss.movielibrary.dtos.Movie;
import com.dss.movielibrary.services.BlankTitleException;
import com.dss.movielibrary.services.MovieLibraryService;
import com.dss.movielibrary.ui.MovieLibraryView;
import java.util.List;

/**
 *
 * @author Schmi
 */
public class MovieLibraryController {

    private MovieLibraryView view;
    private MovieLibraryService service;

    public MovieLibraryController(MovieLibraryView view, MovieLibraryService service) {

        this.view = view;
        this.service = service;
    }

    public void run() {

        boolean keepGoing = true;

        while (keepGoing) {

            try {
                int menuChoice = view.getMainMenuChoice();

                switch (menuChoice) {
                    case 1:
                        addMovie();
                        break;
                    case 2:
                        deleteMovie();
                        break;
                    case 3:
                        editMovie();
                        break;
                    case 4:
                        displayAllMoviesByTitle();
                        break;
                    case 5:
                        displayMovieDetails();
                        break;
                    case 6:
                        searchByTitle();
                        break;
                    case 7:
                        searchById();
                        break;
                    case 8:
                        keepGoing = false;
                        break;
                    default:
                        unknownSelection();
                }
            } catch (MovieLibraryDaoException | BlankTitleException ex) {
                view.displayErrorMessage(ex.getMessage());
            }
        }
        goodBye();
    }

    private void addMovie() throws MovieLibraryDaoException, BlankTitleException {
        view.displayAddMovieBanner();
        Movie newMovie = view.getNewMovieInfo();
        Movie addedMovie = service.addMovie(newMovie);
        view.displayAddMovieSuccessBanner();
        view.displayAddedMovie(addedMovie);
    }

    private void displayAllMoviesByTitle() {
        view.displayAllMoviesBanner();
        List<Movie> allMovies = service.getAllMovies();
        view.displayAllMovies(allMovies);
        view.displayCloseAllMoviesBanner();
    }

    private void displayMovieDetails() {
        view.displayMovieDetailsBanner();
        String title = view.getMovieTitle();
        List<Movie> retrievedMovies = (List<Movie>) service.getMovie(title);
        view.displayMovieDetails(retrievedMovies);
        view.displayCloseDetailsBanner();
    }

    private void searchByTitle() {
        view.displaySearchBanner();
        String title = view.getMovieTitle();
        List<Movie> retrievedMovie = service.getMovie(title);
        view.displayMovieDetails(retrievedMovie);
        view.displayCloseSearchBanner();
    }

    private void searchById() {
        view.displaySearchBanner();
        int id = view.getMovieId();
        Movie retrievedMovie = service.getMovieById(id);
        view.displayMovieDetailByID(retrievedMovie);
        view.displayCloseSearchBanner();
    }

    private void deleteMovie() throws MovieLibraryDaoException {
        view.displayDeleteMovieBanner();
        int id = view.getMovieId();
        service.deleteMovieById(id);
        view.displayMovieDeletedBanner();
    }

    private void editMovie() throws MovieLibraryDaoException {
        view.displayEditMovieBanner();
        int id = view.getMovieId();
        Movie retrieved = service.getMovieById(id);
        if (retrieved != null) {
            Movie updated = view.getEditedMovie(retrieved);
            service.editMovie(updated);
            view.displayEditMovieSuccessful();
        } else {
            view.displayInvalidIdMessage();
        }
    }

    private void unknownSelection() {
        view.unknownSelection();
    }

    private void goodBye() {
        view.goodBye();
    }
}
