/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.bullsandcows.daos;

import com.dss.bullsandcows.dtos.Game;
import com.dss.bullsandcows.dtos.Round;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Schmi
 */
@Repository
@Profile("Service-test")
public class BCInMemDao implements BCDao {

    List<Game> allGames = new ArrayList();
    List<Round> allRounds = new ArrayList();

    public BCInMemDao() {
        Game game1 = new Game();
        game1.setGameId(1);
        game1.setRandomGameNumber("1234");
        game1.setGameComplete(false);
        allGames.add(game1);

        Game game2 = new Game();
        game2.setGameId(2);
        game2.setRandomGameNumber("9876");
        game2.setGameComplete(true);
        allGames.add(game2);

        Round round1G1 = new Round();
        round1G1.setGameId(1);
        round1G1.setGuess("4321");
        round1G1.setGuessTime(LocalDateTime.parse("1920-06-01 06:20:15",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        round1G1.setNumOfExactMatches(0);
        round1G1.setNumOfMatches(4);
        round1G1.setRoundId(1);
        allRounds.add(round1G1);

        Round round2G1 = new Round();
        round2G1.setGameId(1);
        round2G1.setGuess("1289");
        round2G1.setGuessTime(LocalDateTime.parse("1921-06-01 06:20:15",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        round2G1.setNumOfExactMatches(2);
        round2G1.setNumOfMatches(0);
        round2G1.setRoundId(2);
        allRounds.add(round2G1);

        Round round1G2 = new Round();
        round1G2.setGameId(2);
        round1G2.setGuess("9876");
        round1G2.setGuessTime(LocalDateTime.parse("2015-06-01 06:20:15",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        round1G2.setNumOfExactMatches(0);
        round1G2.setNumOfMatches(4);
        round1G2.setRoundId(1);
        allRounds.add(round1G2);

        Round round2G2 = new Round();
        round2G2.setGameId(2);
        round2G2.setGuess("9876");
        round2G2.setGuessTime(LocalDateTime.parse("2017-06-01 06:20:15",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        round2G2.setNumOfExactMatches(4);
        round2G2.setNumOfMatches(0);
        round2G2.setRoundId(2);
        allRounds.add(round2G2);

    }

    @Override
    public Game createGame(Game newGame) throws DaoException {

        if (newGame == null) {
            throw new DaoException("Error, null Game");
        }
        if (newGame.getRandomGameNumber() == null) {
            throw new DaoException("Error, null game number");
        }

        int maxGameId = allGames.stream()
                .mapToInt(o -> o.getGameId())
                .max()
                .orElse(0);

        newGame.setGameId(maxGameId + 1);
        allGames.add(newGame);

        return new Game(newGame);
    }

    @Override
    public Game getGame(int gameId) throws DaoException {

        Game toReturn = allGames.stream().filter(
                o -> o.getGameId() == gameId)
                .findAny()
                .orElse(null);

        if (toReturn != null) {
            toReturn = new Game(toReturn);
        }

        return toReturn;
    }

    @Override
    public Round saveRound(Round completedRound) throws DaoException {

        if (completedRound == null) {
            throw new DaoException("Error, null Round");
        }

        int maxRoundId = allRounds.stream()
                .mapToInt(o -> o.getRoundId())
                .max()
                .orElse(0);

        completedRound.setRoundId(maxRoundId + 1);
        completedRound.setGuessTime(LocalDateTime.now());
        allRounds.add(completedRound);

        return completedRound;
    }

    @Override
    public Game saveGame(Game currentGame) throws DaoException {

        allGames.add(currentGame);

        return new Game(currentGame);
    }

    @Override
    public List<Game> getAllGames() {
        return allGames.stream().map(g -> new Game(g)).collect(Collectors.toList());
    }

    @Override
    public List<Round> getAllRounds(int gameId) {
        return allRounds.stream().filter(r -> r.getGameId() == gameId).collect(Collectors.toList());
    }
}
