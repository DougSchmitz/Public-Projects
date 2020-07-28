/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.bullsandcows.dtos;

/**
 *
 * @author Schmi
 */
public class Game {
    private int gameId;
    private String randomGameNumber;
    private boolean gameComplete;

    public Game(){
    }
    
    public Game( Game that ){
        this.gameId = that.gameId;
        this.randomGameNumber = that.randomGameNumber;
        this.gameComplete = that.gameComplete;
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

    /**
     * @return the randomGameNumber
     */
    public String getRandomGameNumber() {
        return randomGameNumber;
    }

    /**
     * @param randomGameNumber the randomGameNumber to set
     */
    public void setRandomGameNumber(String randomGameNumber) {
        this.randomGameNumber = randomGameNumber;
    }

    /**
     * @return the gameComplete
     */
    public boolean isGameComplete() {
        return gameComplete;
    }

    /**
     * @param gameComplete the gameComplete to set
     */
    public void setGameComplete(boolean gameComplete) {
        this.gameComplete = gameComplete;
    }
    
}
