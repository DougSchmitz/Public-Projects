/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.bullsandcows.daos;

import com.dss.bullsandcows.dtos.Game;
import com.dss.bullsandcows.dtos.Round;
import java.time.LocalDateTime;
import java.time.Month;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Schmi
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("dao-test")
class BCDaoTests {

    @Autowired
    BCDao testDao;

    @Autowired
    JdbcTemplate template;

    public BCDaoTests() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

        template.update("DELETE FROM Round");
        template.update("DELETE FROM Game");

        template.update("ALTER TABLE Game auto_increment = 1");
        template.update("ALTER TABLE Round auto_increment = 1");

        template.update("INSERT INTO Game ( randomGameNumber, gameComplete) "
                + "values ( '5678', '0' ), ( '9876', '1' );");
        template.update("INSERT INTO Round ( guess, guessTime, "
                + "numOfMatches, numOfExactMatches, gameID ) "
                + "values ( '1234', '2020-06-01 06:20:15', 0, 0, 1 ), "
                + "( '8671', '2020-05-01 05:10:25', 1, 2, 1 ), "
                + "( '6789', '2019-01-15 11:05:50', 4, 0, 2 );");

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCreateGameGP() {

        Game toTest = new Game();
        toTest.setRandomGameNumber("1234");
        toTest.setGameComplete(false);

        try {
            Game newGameToTest = testDao.createGame(toTest);

            assertEquals(3, newGameToTest.getGameId());
            assertEquals("1234", newGameToTest.getRandomGameNumber());
            assertFalse(newGameToTest.isGameComplete());
        } catch (DaoException ex) {
            fail("Should not get a DaoException on GP test");
        }
    }

    @Test
    public void testCreateGameNullGame() {

        Game toTest = null;

        try {
            testDao.createGame(toTest);
            fail("Should get a exception with null Game");
        } catch (DaoException ex) {

        }
    }

    @Test
    public void testCreateGameNullGameNumber() {

        Game toTest = new Game();
        toTest.setRandomGameNumber(null);
        toTest.setGameComplete(true);

        try {
            testDao.createGame(toTest);
            fail("Should get a exception with null Game Number");
        } catch (DaoException ex) {

        }
    }

    @Test
    public void testGetGameGP() {

        try {
            Game matchingGame = testDao.getGame(1);

            assertEquals(1, matchingGame.getGameId());
            assertEquals("5678", matchingGame.getRandomGameNumber());
            assertFalse(matchingGame.isGameComplete());
        } catch (DaoException ex) {
            fail("should not get a DaoException in GP test");
        }
    }

    @Test
    public void testGetGameInvalidGameId() {

        try {
            Game matchingGame = testDao.getGame(6);
            fail("should get a DaoException with no game match with gameId");
        } catch (DaoException ex) {

        }
    }

    @Test
    public void testSaveRoundGP() {

        try {
            Round toSave = new Round();
            toSave.setGuess("9876");
//            toSave.setGuessTime(LocalDateTime.parse("2020-06-01 06:20:15",
//                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            toSave.setNumOfMatches(0);
            toSave.setNumOfExactMatches(4);
            toSave.setGameId(1);

            Round toTest = testDao.saveRound(toSave);

            assertEquals(4, toTest.getRoundId());
            assertEquals("9876", toTest.getGuess());
            assertTrue(LocalDateTime.now().isEqual(toTest.getGuessTime()) || LocalDateTime.now().isAfter(toTest.getGuessTime()));
            assertEquals(0, toTest.getNumOfMatches());
            assertEquals(4, toTest.getNumOfExactMatches());
            assertEquals(1, toTest.getGameId());
        } catch (DaoException ex) {
            fail("Should not get a DaoException in GP Test");
        }
    }

    @Test
    public void testSaveRoundNullRound() {

        try {
            Round toTest = null;
            testDao.saveRound(toTest);
            fail("Should get a DaoException if Round is null");
        } catch (DaoException ex) {

        }
    }

    @Test
    public void testSaveGameGP() {

        try {
            Game toSave = new Game();
            toSave.setGameId(5);
            toSave.setRandomGameNumber("4567");
            toSave.setGameComplete(false);

            Game toTest = testDao.saveGame(toSave);
            assertEquals(5, toTest.getGameId());
            assertEquals("4567", toTest.getRandomGameNumber());
            assertFalse(toTest.isGameComplete());
        } catch (DaoException ex) {
            fail("Should not get a DaoException in GP Test");
        }
    }

    @Test
    public void testSaveGameNullGame() {

        try {
            Game toSave = null;
            testDao.saveGame(toSave);
            fail("Should get a DaoException with null Game");
        } catch (DaoException ex) {

        }
    }

    @Test
    public void testGetAllGamesGP() {

        List<Game> toTest = testDao.getAllGames();

        assertEquals(2, toTest.size());

        assertEquals(1, toTest.get(0).getGameId());
        assertEquals("5678", toTest.get(0).getRandomGameNumber());
        assertFalse(toTest.get(0).isGameComplete());

        assertEquals(2, toTest.get(1).getGameId());
        assertEquals("9876", toTest.get(1).getRandomGameNumber());
        assertTrue(toTest.get(1).isGameComplete());

    }

    @Test
    public void testGetAllRoundsGP() {

        try {
            List<Round> allRoundsToTest = testDao.getAllRounds(1);

            assertEquals(2, allRoundsToTest.size());

            assertEquals(1, allRoundsToTest.get(0).getGameId());
            assertEquals(2, allRoundsToTest.get(0).getRoundId());
            assertEquals("8671", allRoundsToTest.get(0).getGuess());
            assertEquals(LocalDateTime.parse("2020-05-01 05:10:25",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    allRoundsToTest.get(0).getGuessTime());
            assertEquals(1, allRoundsToTest.get(0).getNumOfMatches());
            assertEquals(2, allRoundsToTest.get(0).getNumOfExactMatches());

            assertEquals(1, allRoundsToTest.get(1).getGameId());
            assertEquals(1, allRoundsToTest.get(1).getRoundId());
            assertEquals("1234", allRoundsToTest.get(1).getGuess());
            assertEquals(LocalDateTime.parse("2020-06-01 06:20:15",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    allRoundsToTest.get(1).getGuessTime());
            assertEquals(0, allRoundsToTest.get(1).getNumOfMatches());
            assertEquals(0, allRoundsToTest.get(1).getNumOfExactMatches());
        } catch (DaoException ex) {
            fail("Should not get DaoException with GP Test");
        }
    }

    @Test
    public void testGetAllRoundsInvalidId() {

        try {
            List<Round> allRoundsToTest = testDao.getAllRounds(-1);
            fail("Should get DaoException with invalid id");
            
        } catch (DaoException ex) {

        }
    }

}
