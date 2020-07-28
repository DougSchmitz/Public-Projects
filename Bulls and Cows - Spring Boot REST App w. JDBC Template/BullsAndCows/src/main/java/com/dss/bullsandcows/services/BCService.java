/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.bullsandcows.services;

import com.dss.bullsandcows.daos.BCDao;
import com.dss.bullsandcows.daos.DaoException;
import com.dss.bullsandcows.dtos.Game;
import com.dss.bullsandcows.dtos.Round;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Schmi
 */
@Service
public class BCService {

    @Autowired
    BCDao dao;

    public Game startGame() throws DaoException {

        Game newGame = new Game();

        //get and assign a random game number
        newGame.setRandomGameNumber(getRandomGameNumber());
        //set the game to not complete
        newGame.setGameComplete(false);
        //assign the id# and save the game
        newGame = dao.createGame(newGame);

        //return the new game object
        return newGame;
    }

    public Round userGuess(String guess, int gameId) throws DaoException, InvalidGuessException, GameException, GameIdException {

        if (gameId < 0) {
            throw new GameIdException("Error, invalid game id.  Must be >= 0");
        }

        //get the game and convert game number into array
        Game currentGame = dao.getGame(gameId);

        //validate the game check if null and not finished game
        validateCurrentGame(currentGame);

        //validate user guess and convert into array
        validateUserGuess(guess);
        int[] userGuess = convertStringToIntArray(guess);

        //check their guess to the game number
        Round completedRound = new Round();
        completedRound.setGameId(gameId);
        completedRound.setGuess(guess);

        performRound(userGuess, completedRound, currentGame);

        //save the round
        dao.saveRound(completedRound);

        //save the game
        dao.saveGame(currentGame);

        return completedRound;
    }

    public String getGameStatus(int gameId) throws DaoException {

        //get game status
        Game gameStatus = dao.getGame(gameId);

        //let user know if they won the game or not
        String messageStatus = "";
        if (gameStatus.isGameComplete() == false) {
            messageStatus = "Game is not complete, keep trying to guess my number";
        } else {
            messageStatus = "Congratulations, you have matched my number!  Game completed.";
        }
        return messageStatus;
    }

    private String getRandomGameNumber() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        String gameNumber = "";
        for (int i = 0; i < 4; i++) {
            gameNumber += numbers.get(i).toString();
        }
        return gameNumber;
    }

    private void validateUserGuess(String guess) throws InvalidGuessException {

        //convert String to int array
        int[] userGuess = convertStringToIntArray(guess);

        //check to ensure no duplicated chars in the new game number
        for (int j = 0; j < userGuess.length; j++) {
            for (int k = j + 1; k < userGuess.length; k++) {
                if (k != j && userGuess[k] == userGuess[j]) {
                    throw new InvalidGuessException("Guess must have 4, non-repeating numbers");
                }
            }
        }

        //check to ensure all characters are only numbers
        //check that there are only 4 numeric characters
        if (!guess.matches("[\\d]+[0-9]{3}")) {
            throw new InvalidGuessException("Guess must have 4, non-repeating numbers");
        }
    }

    private int[] convertStringToIntArray(String stringNum) {

        int[] newIntArray = new int[4];

        for (int i = 0; i < stringNum.length(); i++) {
            newIntArray[i] = stringNum.charAt(i);
        }
        return newIntArray;
    }

    private void validateCurrentGame(Game currentGame) throws GameException {

        if (currentGame.isGameComplete() == true) {
            throw new GameException("Game is already completed");
        }
        if (currentGame == null) {
            throw new GameException("No game matches");
        }
    }

    private void performRound(int[] userGuess, Round completedRound, Game currentGame) {

        //convert game into array
        int[] winningNum = convertStringToIntArray(currentGame.getRandomGameNumber());

        if (Arrays.equals(userGuess, winningNum)) {
            currentGame.setGameComplete(true);
            completedRound.setNumOfExactMatches(4);
            completedRound.setNumOfMatches(0);
        } else {
            int numOfExactMatches = 0;
            int numOfMatches = 0;

            for (int i = 0; i < userGuess.length; i++) {
                for (int j = 0; j < winningNum.length; j++) {
                    if (userGuess[i] == winningNum[j]) {
                        if (i == j) {
                            numOfExactMatches++;
                        } else {
                            numOfMatches++;
                        }
                    }
                }
            }
            currentGame.setGameComplete(false);
            completedRound.setNumOfExactMatches(numOfExactMatches);
            completedRound.setNumOfMatches(numOfMatches);
        }

    }

    public List<Game> getAllGames() {

        List<Game> allGames = dao.getAllGames();

        for (Game g : allGames) {
            if (!g.isGameComplete()) {
                g.setRandomGameNumber("");
            }
        }

        return allGames;
    }

    public Game getGameById(int gameId) throws DaoException, GameIdException {

        if (gameId < 1) {
            throw new GameIdException("Error, invalid game id.  Must be greater than 0");
        }

        Game toReturn = dao.getGame(gameId);

        if (!toReturn.isGameComplete()) {
            toReturn.setRandomGameNumber("");
        }
        return toReturn;
    }

    public List<Round> getAllRounds(int gameId) throws DaoException, GameIdException {
        
        if (gameId < 1) {
            throw new GameIdException("Error, invalid game id.  Must be greater than 0");
        }
            
        List<Round> allRounds = dao.getAllRounds(gameId);
        
        return allRounds;
    }
}
