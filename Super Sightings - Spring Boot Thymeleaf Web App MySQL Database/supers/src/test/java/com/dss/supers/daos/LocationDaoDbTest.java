/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.TestAppConfig;
import com.dss.supers.entities.Location;
import com.dss.supers.exceptions.DaoException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
@SpringBootTest(classes = TestAppConfig.class)
@ActiveProfiles("database")
public class LocationDaoDbTest {

    @Autowired
    LocationDaoDb dao;

    @Autowired
    JdbcTemplate template;

    public LocationDaoDbTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

        template.update("delete from sighting");
        template.update("delete from location");
        template.update("alter table location auto_increment = 1");
        template.update("insert into location (name, description, address, latitude, longitude) values "
                + "('Super Hero Bar', 'Supers Favorite Hangout', '121 Lake Street, Minneapolis, MN 55415', '45.123456', '120.123456'), "
                + "('Marvel House', 'Hangout for Marvel Supers', '52nd Street, Minneapolis, MN 55425', '55.123456', '125.123456'), "
                + "('Guthrie', 'A cool place to watch a show', '178 River Road, Minneapolis, MN 55405', '65.123456', '130.123456')"
        );
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDb.
     */
    @Test
    public void testGetAllLocationsGP() {

        Location expected = new Location(1, "Super Hero Bar", "Supers Favorite Hangout",
                "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"),
                new BigDecimal("120.123456"));
        Location expected2 = new Location(2, "Marvel House", "Hangout for Marvel Supers",
                "52nd Street, Minneapolis, MN 55425", new BigDecimal("55.123456"),
                new BigDecimal("125.123456"));
        Location expected3 = new Location(3, "Guthrie", "A cool place to watch a show",
                "178 River Road, Minneapolis, MN 55405", new BigDecimal("65.123456"),
                new BigDecimal("130.123456"));

        List<Location> toCheck = dao.getAllLocations();

        assertEquals(3, toCheck.size());

        assertTrue(toCheck.contains(expected));
        assertTrue(toCheck.contains(expected2));
        assertTrue(toCheck.contains(expected3));
    }

    @Test
    public void getLocationByIdGP() throws DaoException {

        Location expected = new Location(1, "Super Hero Bar", "Supers Favorite Hangout",
                "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"),
                new BigDecimal("120.123456"));
        Location toCheck = dao.getLocationById(1);
        assertEquals(expected, toCheck);
    }

    @Test
    public void getLocationByIdInvalidId() {

        try {
            dao.getLocationById(-1);
            fail("Should get DaoException with invalid test");
        } catch (DaoException ex) {
        }
    }

    @Test
    public void addLocationGP() throws DaoException {

        try {
            dao.getLocationById(4);
            fail("Should get DaoException with invalid test");
        } catch (DaoException ex) {
        }

        Location expected = new Location(4, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        Location toCheck = dao.addLocation(expected);
        assertEquals(expected, toCheck);

        toCheck = dao.getLocationById(4);
        assertEquals(expected, toCheck);
    }

    @Test
    public void deleteLocationGP() throws DaoException {

        Location expected = new Location(1, "Super Hero Bar", "Supers Favorite Hangout",
                "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"),
                new BigDecimal("120.123456"));
        Location toCheck = dao.getLocationById(1);
        assertEquals(expected, toCheck);

        int qtySuccessfulUpdates = dao.deleteLocation(1);
        assertEquals(1, qtySuccessfulUpdates);

        try {
            dao.getLocationById(1);
            fail("should get DaoException when no object is available");
        } catch (DaoException ex) {
        }
    }

    @Test
    public void deleteLocationInvalidId() {

        int qtySuccessfulUpdates = dao.deleteLocation(-1);
        assertEquals(0, qtySuccessfulUpdates);
    }

    @Test
    public void updateLocationGP() throws DaoException {

        Location original = new Location(1, "Super Hero Bar", "Supers Favorite Hangout",
                "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"),
                new BigDecimal("120.123456"));
        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        Location toCheck = dao.getLocationById(1);
        assertEquals(original, toCheck);

        int qtySuccesfulUpdates = dao.updateLocation(expected);
        assertEquals(1, qtySuccesfulUpdates);

        toCheck = dao.getLocationById(1);
        assertEquals(expected, toCheck);
        assertNotEquals(original, toCheck);
    }

    @Test
    public void updateLocationInvalidId() {

        Location expected = new Location(-1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        int qtySuccesfulUpdates = dao.updateLocation(expected);
        assertEquals(0, qtySuccesfulUpdates);
    }
}
