/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.bullsandcows.daos;

import com.dss.bullsandcows.dtos.Game;
import com.dss.bullsandcows.dtos.Round;
import java.util.List;

/**
 *
 * @author Schmi
 */
public interface BCDao {

    public Game createGame(Game newGame) throws DaoException;

    public Game getGame(int gameId) throws DaoException ;

    public Round saveRound(Round completedRound)throws DaoException;

    public Game saveGame(Game currentGame)throws DaoException;

    public List<Game> getAllGames();

    public List<Round> getAllRounds(int gameId)throws DaoException;

}
