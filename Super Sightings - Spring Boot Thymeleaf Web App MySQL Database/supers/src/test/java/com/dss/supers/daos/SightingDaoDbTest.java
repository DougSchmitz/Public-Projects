/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.TestAppConfig;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Location;
import com.dss.supers.entities.Organization;
import com.dss.supers.entities.Power;
import com.dss.supers.entities.Sighting;
import com.dss.supers.exceptions.DaoException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class SightingDaoDbTest {

    @Autowired
    SightingDao dao;

    @Autowired
    JdbcTemplate template;

    Location loc1 = new Location(1, "Super Hero Bar", "Supers Favorite Hangout", "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"), new BigDecimal("120.123456"));
    Hero hero1 = new Hero(1, "Cyclops", "Gifted mutant with leadership qualities", null, null);
    Sighting expected = new Sighting(1, LocalDate.parse("2010-01-01"), loc1, hero1);

    public SightingDaoDbTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

        template.update("delete from hero_organization");
        template.update("delete from sighting");
        template.update("delete from location");
        template.update("delete from hero");

        template.update("alter table location auto_increment = 1");
        template.update("alter table hero auto_increment = 1");
        template.update("alter table sighting auto_increment = 1");

        template.update("insert into location (name, description, address, latitude, longitude) values ('Super Hero Bar', 'Supers Favorite Hangout', '121 Lake Street, Minneapolis, MN 55415', '45.123456', '120.123456')");
        template.update("insert into hero ( name, description, powerId) values ('Cyclops', 'Gifted mutant with leadership qualities', 2), ('Storm', 'Mutant Amazon Woman', 3)");
        template.update("insert into sighting ( date, heroId, locationId) values ('2010-01-01', 1, 1), ('2015-02-01', 2, 1), ('2015-02-01', 1, 1)");
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDb.
     */
    @Test
    public void testGetAllSightingsGP() {

        Hero hero2 = new Hero(2, "Storm", "Mutant Amazon Woman", null, null);

        Sighting expected2 = new Sighting(2, LocalDate.parse("2015-02-01"), loc1, hero2);
        Sighting expected3 = new Sighting(3, LocalDate.parse("2015-02-01"), loc1, hero1);

        List<Sighting> toCheck = dao.getAllSightings();
        assertEquals(3, toCheck.size());

        assertTrue(toCheck.contains(expected));
        assertTrue(toCheck.contains(expected2));
        assertTrue(toCheck.contains(expected3));
    }

    /**
     * Test of getSightingById method, of class SightingDaoDb.
     */
    @Test
    public void testGetSightingByIdGP() throws DaoException {

        Sighting toCheck = dao.getSightingById(1);
        assertEquals(expected, toCheck);

    }

    @Test
    public void testGetSightingByIdInvalidId() {

        Sighting toCheck;
        try {
            toCheck = dao.getSightingById(-1);
            fail("Should get DaoException with test");
        } catch (DaoException ex) {
        }
    }

    /**
     * Test of addSighting method, of class SightingDaoDb.
     */
    @Test
    public void testAddSightingGP() throws DaoException {

        try {
            Sighting toCheck = dao.getSightingById(4);
            fail("Should get DaoException with test");
        } catch (DaoException ex) {
        }

        Location loc1 = new Location(1, "Super Hero Bar", "Supers Favorite Hangout", "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"), new BigDecimal("120.123456"));
        Hero hero1 = new Hero(1, "Cyclops", "Gifted mutant with leadership qualities", null, null);

        Sighting expected = new Sighting(4, LocalDate.parse("2000-01-01"), loc1, hero1);

        Sighting toCheck = dao.addSighting(expected);
        assertEquals(expected, toCheck);

        toCheck = dao.getSightingById(4);
        assertEquals(expected, toCheck);
    }

    /**
     * Test of deleteSighting method, of class SightingDaoDb.
     */
    @Test
    public void testDeleteSightingGP() throws DaoException {
        Location loc1 = new Location(1, "Super Hero Bar", "Supers Favorite Hangout", "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"), new BigDecimal("120.123456"));
        Hero hero1 = new Hero(1, "Cyclops", "Gifted mutant with leadership qualities", null, null);
        Sighting expected = new Sighting(1, LocalDate.parse("2010-01-01"), loc1, hero1);

        Sighting toCheck = dao.getSightingById(1);
        assertEquals(expected, toCheck);

        int qtySuccessfulUpdates = dao.deleteSighting(1);
        assertNotEquals(0, qtySuccessfulUpdates);

        try {
            dao.getSightingById(1);
            fail("Should get DaoException when no sighting is available");
        } catch (DaoException ex) {
        }

    }

    @Test
    public void testDeleteSightingInvalidId() {

        int qtySuccessfulUpdates = dao.deleteSighting(-1);
        assertEquals(0, qtySuccessfulUpdates);
    }

    /**
     * Test of editSighting method, of class SightingDaoDb.
     */
    @Test
    public void testEditSightingGP() throws DaoException {

        Location loc1 = new Location(1, "Super Hero Bar", "Supers Favorite Hangout", "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"), new BigDecimal("120.123456"));
        Hero hero1 = new Hero(1, "Cyclops", "Gifted mutant with leadership qualities", null, null);
        Sighting original = new Sighting(1, LocalDate.parse("2010-01-01"), loc1, hero1);
        Sighting expected = new Sighting(1, LocalDate.parse("2000-01-01"), loc1, hero1);

        Sighting toCheck = dao.getSightingById(1);
        assertEquals(original, toCheck);

        int qtySuccesfulUpdates = dao.editSighting(expected);
        assertNotEquals(0, qtySuccesfulUpdates);

        toCheck = dao.getSightingById(1);
        assertEquals(expected, toCheck);
        assertNotEquals(original, toCheck);
    }

    @Test
    public void testEditSightingInvalidId() throws DaoException {

        Location loc1 = new Location(1, "Super Hero Bar", "Supers Favorite Hangout", "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"), new BigDecimal("120.123456"));
        Hero hero1 = new Hero(1, "Cyclops", "Gifted mutant with leadership qualities", null, null);
        Sighting expected = new Sighting(-1, LocalDate.parse("2000-01-01"), loc1, hero1);

        int qtySuccesfulUpdates = dao.editSighting(expected);
        assertEquals(0, qtySuccesfulUpdates);
    }

    @Test
    public void testGetTenSightingsGP() {
        Hero hero2 = new Hero(2, "Storm", "Mutant Amazon Woman", null, null);

        Sighting expected2 = new Sighting(2, LocalDate.parse("2015-02-01"), loc1, hero2);
        Sighting expected3 = new Sighting(3, LocalDate.parse("2015-02-01"), loc1, hero1);

        List<Sighting> toCheck = dao.getAllSightings();
        assertEquals(3, toCheck.size());

        assertTrue(toCheck.contains(expected));
        assertTrue(toCheck.contains(expected2));
        assertTrue(toCheck.contains(expected3));
    }

}
