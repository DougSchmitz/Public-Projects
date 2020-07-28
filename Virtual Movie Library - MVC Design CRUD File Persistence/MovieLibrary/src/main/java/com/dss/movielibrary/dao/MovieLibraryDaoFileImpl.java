/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.movielibrary.dao;

import com.dss.movielibrary.dtos.Movie;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Schmi
 */
public class MovieLibraryDaoFileImpl implements MovieLibraryDao {

    public static final String FILE_PATH = "movies.txt";
    public static final String DELIMITER = "::";

    public static int randomInt(int incMin, int incMax) {
        Random random = new Random();
        int randNum = random.nextInt(incMax + 1 - incMin) + incMin;

        return randNum;
    }

    @Override
    public Movie addMovie(Movie newMovie) throws MovieLibraryDaoException {

        List<Movie> allMovies = getAllMovies();
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
        writeFile(allMovies);

        return newMovie;
    }

    @Override
    public List<Movie> getMovie(String title) {

        List<Movie> allMovies = getAllMovies();
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
        List<Movie> allMovies = getAllMovies();
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
        List<Movie> allMovies = new ArrayList<>();

        try {
            Scanner fileScanner = new Scanner(
                    new BufferedReader(
                            new FileReader(FILE_PATH)
                    )
            );
            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                Movie movieFromStorage = convertLineToMovie(row);

                allMovies.add(movieFromStorage);
            }
        } catch (FileNotFoundException ex) {
//            throw new MovieLibraryDaoException("-_- Could not load roster data into memory.", ex);
        }
        return allMovies;
    }

    private void writeFile(List<Movie> toWrite) throws MovieLibraryDaoException {

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH));

            for (Movie m : toWrite) {
                String line = convertMovieToLine(m);
                writer.println(line);
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            throw new MovieLibraryDaoException("Could not open file: " + FILE_PATH, ex);
        }
    }

    private String convertMovieToLine(Movie m) {
        return m.getMovieId() + DELIMITER
                + m.getTitle() + DELIMITER
                + m.getReleaseDate() + DELIMITER
                + m.getMpaaRating() + DELIMITER
                + m.getDirectorName() + DELIMITER
                + m.getStudio() + DELIMITER
                + m.getUserRating();
    }

    private Movie convertLineToMovie(String row) {

        String[] cells = row.split("::");

        int id = Integer.parseInt(cells[0]);
        String title = cells[1];
        LocalDate releaseDate = LocalDate.parse(cells[2]);
        String mpaaRating = cells[3];
        String directorName = cells[4];
        String Studio = cells[5];
        int userRating = Integer.parseInt(cells[6]);

        Movie movieFromStorage = new Movie();
        movieFromStorage.setMovieId(id);
        movieFromStorage.setTitle(title);
        movieFromStorage.setReleaseDate(releaseDate);
        movieFromStorage.setMpaaRating(mpaaRating);
        movieFromStorage.setDirectorName(directorName);
        movieFromStorage.setStudio(Studio);
        movieFromStorage.setUserRating(userRating);

        return movieFromStorage;
    }

    @Override
    public void deleteMovieById(int id) throws MovieLibraryDaoException {
        List<Movie> allMovies = getAllMovies();
        int index = -1;

        for (int i = 0; i < allMovies.size(); i++) {
            Movie toCheck = allMovies.get(i);
            if (toCheck.getMovieId() == id) {
                index = i;
                break;
            }
        }
        allMovies.remove(index);
        writeFile(allMovies);
    }

    @Override
    public Movie editMovie(Movie newMovie) throws MovieLibraryDaoException {
        List<Movie> allMovies = getAllMovies();
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
        writeFile(allMovies);
        return editedMovie;
    }
}
