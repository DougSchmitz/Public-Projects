/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.bullsandcows.services;

import com.dss.bullsandcows.daos.DaoException;
import com.dss.bullsandcows.dtos.Game;
import com.dss.bullsandcows.dtos.Round;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Schmi
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BCServiceTest {

    @Autowired
    BCService toTest;

    public BCServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testStartGameGP() {

        try {

            Game newGameToTest = toTest.startGame();

            //check for valid gameId and game not complete
            assertEquals(3, newGameToTest.getGameId());
            assertFalse(newGameToTest.isGameComplete());

        } catch (DaoException ex) {
            fail("Should not hit DaoException during GP test.");
        }
    }

    @Test
    public void testUserGuessGP() {

        try {

            LocalDateTime time = LocalDateTime.now();
            Round toCheck = toTest.userGuess("1254", 1);

            assertEquals(1, toCheck.getGameId());
            assertEquals("1254", toCheck.getGuess());
            assertTrue(time.isBefore(toCheck.getGuessTime()) || time.isEqual(toCheck.getGuessTime()));
            assertEquals(3, toCheck.getNumOfExactMatches());
            assertEquals(0, toCheck.getNumOfMatches());
            assertEquals(3, toCheck.getRoundId());

        } catch (DaoException ex) {
            fail("Should not hit DaoException during GP test.");
        } catch (InvalidGuessException ex) {
            fail("Should not hit InvalidGuessException during GP test.");
        } catch (GameException ex) {
            fail("Should not hit GameException during GP test.");
        } catch (GameIdException ex) {
            fail("Should not hit GameIdException during GP test.");
        }
    }

    @Test
    public void testUserGuessInvalidGuess() {

        try {
            String guess = "5566";
            int gameId = 1;

            LocalDateTime time = LocalDateTime.now();
            Round toCheck = toTest.userGuess(guess, gameId);
            fail("Should hit InvalidGuessException during invalid guess test.");

        } catch (DaoException ex) {
            fail("Should not hit DaoException during invalid guess test.");
        } catch (InvalidGuessException ex) {

        } catch (GameException ex) {
            fail("Should not hit GameException during invalid guess test.");
        } catch (GameIdException ex) {
            fail("Should not hit GameIdException during invalid guess test.");
        }
    }

    @Test
    public void testUserGuessNullGuess() {

        try {
            String guess = "null";
            int gameId = 1;

            LocalDateTime time = LocalDateTime.now();
            Round toCheck = toTest.userGuess(guess, gameId);
            fail("Should hit InvalidGuessException during null guess test.");

        } catch (DaoException ex) {
            fail("Should not hit DaoException null guess test.");
        } catch (InvalidGuessException ex) {

        } catch (GameException ex) {
            fail("Should not hit GameException null guess test.");
        } catch (GameIdException ex) {
            fail("Should not hit GameIdException null guess test.");
        }
    }

    @Test
    public void testUserGuessInvalidId() {

        try {
            String guess = "1534";
            int gameId = -1;

            LocalDateTime time = LocalDateTime.now();
            Round toCheck = toTest.userGuess(guess, gameId);
            fail("Should hit GameIdException during invalid id test. ");

        } catch (DaoException ex) {
            fail("Should not hit DaoException during invalid id test.");
        } catch (InvalidGuessException ex) {
            fail("Should hit InvalidGuessException during invalid id test.");
        } catch (GameException ex) {
            fail("Should not hit GameException during invalid id test.");
        } catch (GameIdException ex) {

        }
    }

    @Test
    public void testGetAllGamesGP() {

        List<Game> allGames = toTest.getAllGames();

        assertEquals(2, allGames.size());

        assertEquals(1, allGames.get(0).getGameId());
        assertEquals("", allGames.get(0).getRandomGameNumber());
        assertFalse(allGames.get(0).isGameComplete());

        assertEquals(2, allGames.get(1).getGameId());
        assertEquals("9876", allGames.get(1).getRandomGameNumber());
        assertTrue(allGames.get(1).isGameComplete());
    }

    @Test
    public void testGetGameByIdGP() {
        try {

            Game tocheck = toTest.getGameById(1);
            assertEquals(1, tocheck.getGameId());
            assertEquals("", tocheck.getRandomGameNumber());
            assertFalse(tocheck.isGameComplete());

            Game tocheck2 = toTest.getGameById(2);
            assertEquals(2, tocheck2.getGameId());
            assertEquals("9876", tocheck2.getRandomGameNumber());
            assertTrue(tocheck2.isGameComplete());

        } catch (DaoException ex) {
            fail("Should not get DaoException on GP Test");
        } catch (GameIdException ex) {
            fail("Should not get GameIdException on GP Test");
        }
    }

    @Test
    public void testGetGameByIdInvalidID() {
        try {

            Game tocheck = toTest.getGameById(-1);
            fail("Should get GameIdException with invalid game id");

        } catch (DaoException ex) {
            fail("Should not get DaoException on GP Test");
        } catch (GameIdException ex) {

        }
    }

    @Test
    public void testGetAllRoundsGP() {

        try {
            List<Round> allRounds = toTest.getAllRounds(1);

            assertEquals(2, allRounds.size());

            assertEquals(1, allRounds.get(1).getGameId());
            assertEquals(2, allRounds.get(1).getRoundId());
            assertEquals("1289", allRounds.get(1).getGuess());
            assertEquals(LocalDateTime.parse("1921-06-01 06:20:15",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    allRounds.get(1).getGuessTime());
            assertEquals(0, allRounds.get(1).getNumOfMatches());
            assertEquals(2, allRounds.get(1).getNumOfExactMatches());

            assertEquals(1, allRounds.get(0).getGameId());
            assertEquals(1, allRounds.get(0).getRoundId());
            assertEquals("4321", allRounds.get(0).getGuess());
            assertEquals(LocalDateTime.parse("1920-06-01 06:20:15",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    allRounds.get(0).getGuessTime());
            assertEquals(4, allRounds.get(0).getNumOfMatches());
            assertEquals(0, allRounds.get(0).getNumOfExactMatches());
        } catch (DaoException ex) {
            fail("Should not get DaoException in GP Test");
        } catch (GameIdException ex) {
            fail("Should not get GameIdException with GP Test");
        }
    }

    @Test
    public void testGetAllRoundsInvalidId() {

        try {
            List<Round> allRounds = toTest.getAllRounds(-1);
            fail("Should get GameIdException with Invalid Id");
        } catch (DaoException ex) {
            fail("Should not get DaoException in GP Test");
        } catch (GameIdException ex) {

        }
    }

}
