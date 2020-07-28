/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Services;

import com.dss.supers.InMemDaos.InMemLocationDao;
import com.dss.supers.TestAppConfig;
import com.dss.supers.entities.Location;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.NoItemsException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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
@SpringBootTest(classes = TestAppConfig.class)
//@ActiveProfiles("memory")
public class LocationServiceImplTest {

    @Autowired
    LocationServiceImpl service;

    @Autowired
    InMemLocationDao dao;

    public LocationServiceImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

        dao.setUp();

    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllLocations method, of class LocationServiceImpl.
     */
    @Test
    public void testGetAllLocationsGP() throws NoItemsException {

        Location location = new Location(1, "Super Hero Bar", "Supers Favorite Hangout",
                "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"),
                new BigDecimal("120.123456"));
        Location location2 = new Location(2, "Marvel House", "Hangout for Marvel Supers",
                "52nd Street, Minneapolis, MN 55425", new BigDecimal("55.123456"),
                new BigDecimal("125.123456"));
        Location location3 = new Location(3, "Guthrie", "A cool place to watch a show",
                "178 River Road, Minneapolis, MN 55405", new BigDecimal("65.123456"),
                new BigDecimal("130.123456"));

        List<Location> toCheck = service.getAllLocations();

        assertEquals(3, toCheck.size());

        assertTrue(toCheck.contains(location));
        assertTrue(toCheck.contains(location2));
        assertTrue(toCheck.contains(location3));
    }

    @Test
    public void testGetAllLocationsNoItems() {

        dao.remove();

        try {
            service.getAllLocations();
            fail("Should get NoItemsException on test");
        } catch (NoItemsException ex) {
        }
    }

    /**
     * Test of getLocationById method, of class LocationServiceImpl.
     */
    @Test
    public void testGetLocationByIdGP() throws InvalidIdException {

        Location expected = new Location(1, "Super Hero Bar", "Supers Favorite Hangout",
                "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"),
                new BigDecimal("120.123456"));

        Location toCheck = service.getLocationById(1);
        assertEquals(expected, toCheck);
    }

    @Test
    public void testGetLocationByIdInvalidId() {

        try {
            Location toCheck = service.getLocationById(-1);
            fail("Should get InvalidIdException on test");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addLocation method, of class LocationServiceImpl.
     */
    @Test
    public void testAddLocationGP() throws InvalidEntityException {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        Location toCheck = service.addLocation(expected);
        assertEquals(expected, toCheck);
    }

    @Test
    public void testAddLocationNullName() {

        Location expected = new Location(1, null, "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationBlankName() {

        Location expected = new Location(1, "", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationLongName() {

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Location expected = new Location(1, new String(letters), "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationNullDescription() {

        Location expected = new Location(1, "Test bar", null,
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationBlankDescription() {

        Location expected = new Location(1, "Test bar", "",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationLongDescription() {

        char[] letters = new char[76];
        Arrays.fill(letters, 'a');

        Location expected = new Location(1, "Test bar", new String(letters),
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationNullAddress() {

        Location expected = new Location(1, "Test bar", "test hangout",
                null, new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationBlankAddress() {

        Location expected = new Location(1, "Test bar", "test hangout",
                "", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationLongAddress() {

        char[] letters = new char[76];
        Arrays.fill(letters, 'a');

        Location expected = new Location(1, "Test bar", "test hangout",
                new String(letters), new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationNullLatitude() {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", null,
                new BigDecimal("160.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationLowLatitude() {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("-91.123456"),
                new BigDecimal("160.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationHighLatitude() {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("91.123456"),
                new BigDecimal("160.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationNullLongitude() {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("-85.123456"),
                null);

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationLowLongitude() {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("-85.123456"),
                new BigDecimal("-181.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddLocationHighLongitude() {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("181.123456"));

        try {
            Location toCheck = service.addLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    /**
     * Test of deleteLocation method, of class LocationServiceImpl.
     */
    @Test
    public void testDeleteLocationGP() throws InvalidIdException {

        Location expected = new Location(1, "Super Hero Bar", "Supers Favorite Hangout",
                "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"),
                new BigDecimal("120.123456"));

        Location toCheck = service.getLocationById(1);
        assertEquals(expected, toCheck);

        service.deleteLocation(1);

        try {
            service.getLocationById(1);
            fail("Should get InvalidIdException with test");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testDeleteLocationInvalidId() {

        try {
            service.deleteLocation(-1);
            fail("Should get InvalidIdException with test");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of updateLocation method, of class LocationServiceImpl.
     */
    @Test
    public void testUpdateLocationGP() throws InvalidEntityException, InvalidIdException {

        Location original = new Location(1, "Super Hero Bar", "Supers Favorite Hangout",
                "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"),
                new BigDecimal("120.123456"));
        Location updated = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        Location toCheck = service.getLocationById(1);
        assertEquals(original, toCheck);

        service.updateLocation(updated);

        toCheck = service.getLocationById(1);
        assertEquals(updated, toCheck);
        assertNotEquals(original, toCheck);
    }

    @Test
    public void testUpdateLocationInvalidId() throws InvalidEntityException {

        Location updated = new Location(-1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(updated);
            fail("Should get InvalidIdException with test");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testUpdateLocationNullLocation() throws InvalidIdException {

        Location expected = null;

        try {
            service.updateLocation(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationNullName() throws InvalidIdException {

        Location expected = new Location(1, null, "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationEmptyName() throws InvalidIdException {

        Location expected = new Location(1, "", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationBlankName() throws InvalidIdException {

        Location expected = new Location(1, "  ", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationLongName() throws InvalidIdException {

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Location expected = new Location(1, new String(letters), "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationNullDescription() throws InvalidIdException {

        Location expected = new Location(1, "Test bar", null,
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationBlankDescription() throws InvalidIdException {

        Location expected = new Location(1, "Test bar", "  ",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationEmptyDescription() throws InvalidIdException {

        Location expected = new Location(1, "Test bar", "",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationLongDescription() throws InvalidIdException {

        char[] letters = new char[76];
        Arrays.fill(letters, 'a');

        Location expected = new Location(1, "Test bar", new String(letters),
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationNullAddress() throws InvalidIdException {

        Location expected = new Location(1, "Test bar", "test hangout",
                null, new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationEmptyAddress() throws InvalidIdException {

        Location expected = new Location(1, "Test bar", "test hangout",
                "", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationBlankAddress() throws InvalidIdException {

        Location expected = new Location(1, "Test bar", "test hangout",
                "  ", new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationLongAddress() throws InvalidIdException {

        char[] letters = new char[76];
        Arrays.fill(letters, 'a');

        Location expected = new Location(1, "Test bar", "test hangout",
                new String(letters), new BigDecimal("85.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationNullLatitude() throws InvalidIdException {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", null,
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationLowLatitude() throws InvalidIdException {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("-91.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationHighLatitude() throws InvalidIdException {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("91.123456"),
                new BigDecimal("160.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationNullLongitude() throws InvalidIdException {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("-85.123456"),
                null);

        try {
            service.updateLocation(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationLowLongitude() throws InvalidIdException {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("-85.123456"),
                new BigDecimal("-181.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateLocationHighLongitude() throws InvalidIdException {

        Location expected = new Location(1, "Test bar", "test hangout",
                "1234 Washington Street, Shakopee, MN 55379", new BigDecimal("85.123456"),
                new BigDecimal("181.123456"));

        try {
            service.updateLocation(expected);
            fail("Should get a InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

}
