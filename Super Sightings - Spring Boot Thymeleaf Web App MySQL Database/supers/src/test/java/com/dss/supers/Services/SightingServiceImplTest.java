/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Services;

import com.dss.supers.InMemDaos.InMemSightingDao;
import com.dss.supers.TestAppConfig;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Location;
import com.dss.supers.entities.Sighting;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.NoItemsException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
public class SightingServiceImplTest {

    @Autowired
    SightingServiceImpl service;

    @Autowired
    InMemSightingDao dao;

    Hero hero2 = new Hero(2, "Storm", "Mutant Amazon Woman", null, null);
    Location loc1 = new Location(1, "Super Hero Bar", "Supers Favorite Hangout", "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"), new BigDecimal("120.123456"));
    Hero hero1 = new Hero(1, "Cyclops", "Gifted mutant with leadership qualities", null, null);

    public SightingServiceImplTest() {
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
     * Test of getAllSightings method, of class SightingServiceImpl.
     */
    @Test
    public void testGetAllSightingsGP() throws NoItemsException {

        Sighting expected = new Sighting(1, LocalDate.parse("2010-01-01"), loc1, hero1);
        Sighting expected2 = new Sighting(2, LocalDate.parse("2015-02-01"), loc1, hero2);
        Sighting expected3 = new Sighting(3, LocalDate.parse("2015-02-01"), loc1, hero1);

        List<Sighting> toCheck = service.getAllSightings();

        assertEquals(3, toCheck.size());

        assertTrue(toCheck.contains(expected));
        assertTrue(toCheck.contains(expected2));
        assertTrue(toCheck.contains(expected3));
    }

    @Test
    public void testGetAllSightingsNoItems() {

        dao.remove();

        try {
            List<Sighting> toCheck = service.getAllSightings();
            fail("Should get NoItemsException on test");
        } catch (NoItemsException ex) {
        }
    }

    /**
     * Test of getSightingById method, of class SightingServiceImpl.
     */
    @Test
    public void testGetSightingByIdGP() throws InvalidIdException {
        Sighting expected = new Sighting(1, LocalDate.parse("2010-01-01"), loc1, hero1);

        Sighting toCheck = service.getSightingById(1);
        assertEquals(expected, toCheck);
    }

    @Test
    public void testGetSightingByIdInvalidId() {
        Sighting expected = new Sighting(-1, LocalDate.parse("2010-01-01"), loc1, hero1);

        try {
            service.getSightingById(-1);
            fail("Should get InvalidIdException on test");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addSighting method, of class SightingServiceImpl.
     */
    @Test
    public void testAddSightingGP() throws InvalidIdException, InvalidEntityException {

        try {
            service.getSightingById(4);
            fail("Should get InvalidException on test");
        } catch (InvalidIdException ex) {
        }

        Sighting expected = new Sighting(4, LocalDate.parse("2010-01-01"), loc1, hero1);

        Sighting toCheck = service.addSighting(expected);
        assertEquals(expected, toCheck);

        toCheck = service.getSightingById(4);
        assertEquals(expected, toCheck);
    }

    @Test
    public void testAddSightingNullSighting() {

        Sighting expected = null;

        try {
            service.addSighting(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddSightingNullDate() {

        Sighting expected = new Sighting(4, null, loc1, hero1);

        try {
            service.addSighting(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }

    }

    @Test
    public void testAddSightingFutureDate() {

        Sighting expected = new Sighting(4, LocalDate.parse("2021-01-01"), loc1, hero1);

        try {
            service.addSighting(expected);
            fail("Should get InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }

    }

    /**
     * Test of deleteSighting method, of class SightingServiceImpl.
     */
    @Test
    public void testDeleteSightingGP() throws InvalidIdException {

        Sighting expected = new Sighting(1, LocalDate.parse("2010-01-01"), loc1, hero1);

        Sighting toCheck = service.getSightingById(1);
        assertEquals(expected, toCheck);

        service.deleteSighting(1);

        try {
            service.deleteSighting(1);
            fail("Should get InvalidIdException with test");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of updateSighting method, of class SightingServiceImpl.
     */
    @Test
    public void testUpdateSightingGP() throws InvalidIdException, InvalidEntityException {
        Sighting original = new Sighting(1, LocalDate.parse("2010-01-01"), loc1, hero1);
        Sighting updated = new Sighting(1, LocalDate.parse("2015-01-01"), loc1, hero1);
        
        Sighting toCheck = service.getSightingById(1);
        assertEquals(original, toCheck);
        
        service.updateSighting(updated);
        
        toCheck = service.getSightingById(1);
        assertEquals(updated, toCheck);
        assertNotEquals(original, toCheck);
    }

}
