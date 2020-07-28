/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.bullsandcows.Controllers;

import com.dss.bullsandcows.daos.DaoException;
import com.dss.bullsandcows.dtos.Game;
import com.dss.bullsandcows.dtos.Round;
import com.dss.bullsandcows.services.BCService;
import com.dss.bullsandcows.services.GameException;
import com.dss.bullsandcows.services.GameIdException;
import com.dss.bullsandcows.services.InvalidGuessException;
import static java.nio.file.Files.list;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Schmi
 */
@RestController
@RequestMapping("/api")
public class BCController {

    @Autowired
    BCService service;

    @PostMapping("/begin")
    public ResponseEntity<String> startGame() throws DaoException {
        Game newGame = service.startGame();
        return new ResponseEntity<>("Here is your new Game ID#: " + newGame.getGameId(), HttpStatus.CREATED);
    }

    @PostMapping("/guess")
    public ResponseEntity<String> userGuess(@RequestBody Round guess) throws
            DaoException, InvalidGuessException, GameException, GameIdException {
        Round roundResult = service.userGuess(guess.getGuess(), guess.getGameId());
        String gameStatus = service.getGameStatus(guess.getGameId());

        return new ResponseEntity<>(
                " Round Id: " + roundResult.getRoundId() + "\n"
                + " Your guess: " + roundResult.getGuess() + "\n"
                + " DateTime: " + roundResult.getGuessTime() + "\n"
                + " Number of Matches: " + roundResult.getNumOfMatches() + "\n"
                + " Number of Exact Matches: " + roundResult.getNumOfExactMatches() + "\n"
                + " Game Id: " + guess.getGameId() + "\n" + " " + gameStatus,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/game")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> allGames = service.getAllGames();

        return new ResponseEntity<>(allGames, HttpStatus.CREATED);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> getGameById(@PathVariable int gameId)
            throws DaoException, GameIdException {
        Game toReturn = service.getGameById(gameId);

        return new ResponseEntity<>(toReturn, HttpStatus.CREATED);
    }

    @GetMapping("/rounds/{gameId}")
    public ResponseEntity<List<Round>> getAllRounds(@PathVariable int gameId) 
            throws DaoException, GameIdException {
        List<Round> allRounds = service.getAllRounds(gameId);

        return new ResponseEntity<>(allRounds, HttpStatus.CREATED);
    }
}
