/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.bullsandcows.daos;

import com.dss.bullsandcows.dtos.Game;
import com.dss.bullsandcows.dtos.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Schmi
 */
@Repository
@Profile({"production", "dao-test"})
public class BCDbDao implements BCDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public Game createGame(Game newGame) throws DaoException {

        if (newGame == null) {
            throw new DaoException("Error, null Game");
        }
        if (newGame.getRandomGameNumber() == null) {
            throw new DaoException("Error, null game number");
        }

        template.update("INSERT INTO Game( randomGameNumber, "
                + "gameComplete ) Values (?,?)",
                newGame.getRandomGameNumber(), newGame.isGameComplete());

        int newID = template.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        newGame.setGameId(newID);

        return newGame;
    }

    @Override
    public Game getGame(int gameId) throws DaoException {

        Game matchingGame = null;
        try {
            matchingGame = template.queryForObject("SELECT * "
                    + "FROM Game WHERE gameID = ?", new GameMapper(), gameId);
        } catch (EmptyResultDataAccessException ex) {
            throw new DaoException("No matching game");
        }

        return matchingGame;
    }

    @Override
    public Round saveRound(Round completedRound) throws DaoException {

        if (completedRound == null) {
            throw new DaoException("Error, null Round");
        }

        String sql = "INSERT INTO Round( guess, "
                + "guessTime, numOfMatches, numOfExactMatches, gameID ) "
                + "VALUES (?, ?, ?, ?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        template.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, completedRound.getGuess());
            statement.setString(2, LocalDateTime.now().toString());
            statement.setInt(3, completedRound.getNumOfMatches());
            statement.setInt(4, completedRound.getNumOfExactMatches());
            statement.setInt(5, completedRound.getGameId());
            return statement;
        }, keyHolder);

        completedRound.setRoundId(keyHolder.getKey().intValue());
        completedRound.setGuessTime(LocalDateTime.now());

        return completedRound;
    }

    @Override
    public Game saveGame(Game currentGame) throws DaoException {

        if (currentGame == null) {
            throw new DaoException("Error, null Round");
        }

        template.update("UPDATE Game SET gameComplete = ? WHERE gameID = ?",
                currentGame.isGameComplete(), currentGame.getGameId());

        return currentGame;
    }

    @Override
    public List<Game> getAllGames() {

        List<Game> allGames = template.query("SELECT * FROM Game", new GameMapper());

        return allGames;
    }

    @Override
    public List<Round> getAllRounds(int gameId) throws DaoException {

        if (gameId < 1) {
            throw new DaoException("Game Id can't be less than 1");
        }

        List<Round> allRounds = template.query("SELECT * FROM Round  "
                + "WHERE gameId = ? ORDER BY guessTime", new RoundMapper(), gameId);

        return allRounds;
    }

//            Game matchingGame = null;
//        try {
//            matchingGame = template.queryForObject("SELECT * "
//                    + "FROM Game WHERE gameID = ?", new GameMapper(), gameId);
//        } catch (EmptyResultDataAccessException ex) {
//            throw new DaoException("No matching game");
//        }
    private static class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int i) throws SQLException {
            Game toReturn = new Game();
            toReturn.setGameId(rs.getInt("gameId"));
            toReturn.setRandomGameNumber(rs.getString("randomGameNumber"));
            toReturn.setGameComplete(rs.getBoolean("gameComplete"));

            return toReturn;
        }
    }

    private static class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int i) throws SQLException {
            Round toReturn = new Round();
            toReturn.setRoundId(rs.getInt("roundId"));
            toReturn.setGuess(rs.getString("guess"));
            Date date = rs.getDate("guessTime");
            Timestamp timestamp = new Timestamp(date.getTime());
            toReturn.setGuessTime(LocalDateTime.parse(rs.getString("guessTime"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            toReturn.setNumOfMatches(rs.getInt("numOfMatches"));
            toReturn.setNumOfExactMatches(rs.getInt("numOfExactMatches"));
            toReturn.setGameId(rs.getInt("gameId"));

            return toReturn;
        }
    }

}
