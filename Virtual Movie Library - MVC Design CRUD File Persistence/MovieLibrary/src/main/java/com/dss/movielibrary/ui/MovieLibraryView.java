/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.movielibrary.ui;

import com.dss.movielibrary.controller.MovieLibraryController;
import com.dss.movielibrary.dao.MovieLibraryDao;
import com.dss.movielibrary.dtos.Movie;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Schmi
 */
public class MovieLibraryView {

    private UserIO io;

    public MovieLibraryView(UserIO io) {
        this.io = io;
    }

    public int getMainMenuChoice() {

        io.print("=== MAIN MENU ===");
        io.print("1. Add Movie");
        io.print("2. Remove Movie");
        io.print("3. Edit Movie");
        io.print("4. List Movie Library");
        io.print("5. Display Movie Details");
        io.print("6. Search By Movie Title");
        io.print("7. Search By Movie Id#");
        io.print("8. Exit");

        int choice = io.readInt("Please select from the above choices.", 1, 7);
        return choice;
    }

    public void displayAddMovieBanner() {
        io.print("=== ADD MOVIE ===");
    }

    public Movie getNewMovieInfo() {
        Movie newMovie = new Movie();
        newMovie.setTitle(io.readString("Please enter the movie TITLE"));
        newMovie.setDirectorName(io.readString("Please enter the Director's NAME"));
        newMovie.setMpaaRating(io.readString("Please enter the MPAA RATING NUMBER"));
        newMovie.setReleaseDate(io.readDate("Please enter the movie RELEASE DATE as (MM/dd/yyyy)"));
        newMovie.setStudio(io.readString("Please enter the movie STUDIO"));
        newMovie.setUserRating(io.readInt("Please enter you movie RATING (1-5)"));

        return newMovie;
    }

    public void displayAddMovieSuccessBanner() {
        io.print("--- MOVIE ADDED SUCCESSFULLY ---");
    }

    public void displayAddedMovie(Movie added) {
        io.print("TITLE: " + added.getTitle());
        io.print("DIRECTOR: " + added.getDirectorName());
        io.print("MPAA RATING: " + added.getMpaaRating());
        io.print("RELEASE DATE: " + added.getReleaseDate());
        io.print("STUDIO: " + added.getStudio());
        io.print("USER RATING: " + added.getUserRating());
        io.print("MOVIE ID#: " + added.getMovieId());
    }

    public void displayAllMovies(List<Movie> allMovies) {
        for (Movie toPrint : allMovies) {
            io.print(toPrint.getTitle() + " / ID# " + toPrint.getMovieId());
        }
    }

    public String getMovieTitle() {
        String title = io.readString("What is the movie TITLE?");
        return title;
    }

    public int getMovieId() {
        int id = io.readInt("What is the Movie ID#?");
        return id;
    }

    public void displayMovieDetails(List<Movie> retrievedMovies) {
        for (Movie toPrint : retrievedMovies) {
            io.print("TITLE: " + toPrint.getTitle());
            io.print("DIRECTOR: " + toPrint.getDirectorName());
            io.print("MPAA RATING: " + toPrint.getMpaaRating());
            io.print("RELEASE DATE: " + toPrint.getReleaseDate());
            io.print("STUDIO: " + toPrint.getStudio());
            io.print("USER RATING: " + toPrint.getUserRating());
            io.print("MOVIE ID#: " + toPrint.getMovieId());
            io.print("");
        }
    }

    public void goodBye() {
        io.print("--- GOOD BYE ---");
    }

    public void unknownSelection() {
        io.print("UNKNOWN SELECTION");
    }

    public void displaySearchBanner() {
        io.print("--- SEARCH MOVIES ---");
    }

    public void displayCloseSearchBanner() {
        io.print("--- SEARCH ENDED ---");
    }

    public void displayCloseDetailsBanner() {
        io.print("--- MOVIE DETAILS CLOSED ---");
    }

    public void displayMovieDetailsBanner() {
        io.print("--- MOVIE DETAILS ---");
    }

    public void displayAllMoviesBanner() {
        io.print("--- DISPLAYING ALL MOVIES ---");
    }

    public void displayCloseAllMoviesBanner() {
        io.print("--- CLOSING LIST OF ALLMOVIES ---");
    }

    public void displayMovieDetailByID(Movie retrievedMovie) {
        io.print("TITLE: " + retrievedMovie.getTitle());
        io.print("DIRECTOR: " + retrievedMovie.getDirectorName());
        io.print("MPAA RATING: " + retrievedMovie.getMpaaRating());
        io.print("RELEASE DATE: " + retrievedMovie.getReleaseDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        io.print("STUDIO: " + retrievedMovie.getStudio());
        io.print("USER RATING: " + retrievedMovie.getUserRating());
        io.print("MOVIE ID#: " + retrievedMovie.getMovieId());
        io.print("");
    }

    public void displayDeleteMovieBanner() {
        io.print("--- DELETE MOVIE ---");
    }

    public void displayMovieDeletedBanner() {
        io.print("--- MOVIE DELETED SUCCESSFULLY ---");
    }

    public void displayEditMovieBanner() {
        io.print("--- EDIT MOVIE ---");
    }

    public void displayEditMovieSuccessful() {
        io.print("--- EDIT MOVIE SUCCESSFUL ---");
    }

    public void displayErrorMessage(String message) {
        io.print(message);
    }

    public Movie getEditedMovie(Movie retrieved) {
        Movie updatedMovie = new Movie();

        updatedMovie.setMovieId(retrieved.getMovieId());

        updatedMovie.setTitle(io.editString("Enter new TITLE or press enter to keep [" + retrieved.getTitle() + "]: ", retrieved.getTitle()));
        updatedMovie.setDirectorName(io.editString("Enter new DIRECTOR NAME or press enter to keep [" + retrieved.getDirectorName() + "]: ", retrieved.getDirectorName()));
        updatedMovie.setMpaaRating(io.editString("Enter new MPAA RATING or press enter to keep [" + retrieved.getMpaaRating() + "]: ", retrieved.getMpaaRating()));
        updatedMovie.setReleaseDate(io.editDate("Enter new RELEASE DATE (MM/dd/yyyy) or press enter to keep [" + retrieved.getReleaseDate() + "]: ", retrieved.getReleaseDate()));
        updatedMovie.setStudio(io.editString("Enter new STUDIO or press enter to keep [" + retrieved.getStudio() + "]: ", retrieved.getStudio()));
        updatedMovie.setUserRating(io.editNumber("Enter new USER RATING (1-5) or press enter to keep [" + retrieved.getUserRating() + "]: ", retrieved.getUserRating()));

        return updatedMovie;
    }

    public void displayInvalidIdMessage() {
        io.print( "Please select a valid id.\n");
    }
}
