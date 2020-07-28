/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.bullsandcows.dtos;

import java.time.LocalDateTime;

/**
 *
 * @author Schmi
 */
public class Round {
    private int roundId;
    private String guess;
    private LocalDateTime guessTime;
    private int numOfMatches;
    private int numOfExactMatches;
    private int gameId;

    /**
     * @return the roundNum
     */
    public int getRoundId() {
        return roundId;
    }

    /**
     * @param roundNum the roundNum to set
     */
    public void setRoundId(int roundNum) {
        this.roundId = roundNum;
    }

    /**
     * @return the guess
     */
    public String getGuess() {
        return guess;
    }

    /**
     * @param guess the guess to set
     */
    public void setGuess(String guess) {
        this.guess = guess;
    }

    /**
     * @return the numOfMatches
     */
    public int getNumOfMatches() {
        return numOfMatches;
    }

    /**
     * @param numOfMatches the numOfMatches to set
     */
    public void setNumOfMatches(int numOfMatches) {
        this.numOfMatches = numOfMatches;
    }

    /**
     * @return the numOfExactMatches
     */
    public int getNumOfExactMatches() {
        return numOfExactMatches;
    }

    /**
     * @param numOfExactMatches the numOfExactMatches to set
     */
    public void setNumOfExactMatches(int numOfExactMatches) {
        this.numOfExactMatches = numOfExactMatches;
    }

    /**
     * @return the guessTime
     */
    public LocalDateTime getGuessTime() {
        return guessTime;
    }

    /**
     * @param guessTime the guessTime to set
     */
    public void setGuessTime(LocalDateTime guessTime) {
        this.guessTime = guessTime;
    }

    /**
     * @return the gameId
     */
    public int getGameId() {
        return gameId;
    }

    /**
     * @param gameId the gameId to set
     */
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
   
    
    
    }
