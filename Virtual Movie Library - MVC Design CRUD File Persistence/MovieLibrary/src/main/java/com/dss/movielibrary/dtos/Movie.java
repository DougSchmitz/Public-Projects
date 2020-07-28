/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.movielibrary.dtos;

import java.time.LocalDate;

/**
 *
 * @author Schmi
 */
public class Movie {

    private int movieId;
    private String title;
    private LocalDate releaseDate;
    private String mpaaRating;
    private String directorName;
    private String Studio;
    private int userRating;

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the releaseDate
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate the releaseDate to set
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return the mpaaRating
     */
    public String getMpaaRating() {
        return mpaaRating;
    }

    /**
     * @param mpaaRating the mpaaRating to set
     */
    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    /**
     * @return the directorName
     */
    public String getDirectorName() {
        return directorName;
    }

    /**
     * @param directorName the directorName to set
     */
    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    /**
     * @return the Studio
     */
    public String getStudio() {
        return Studio;
    }

    /**
     * @param Studio the Studio to set
     */
    public void setStudio(String Studio) {
        this.Studio = Studio;
    }

    /**
     * @return the userRating
     */
    public int getUserRating() {
        return userRating;
    }

    /**
     * @param userRating the userRating to set
     */
    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

}
