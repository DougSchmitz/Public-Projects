/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.TestAppConfig;
import com.dss.supers.entities.Power;
import com.dss.supers.exceptions.DaoException;
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
public class PowerDaoDbTest {

    @Autowired
    PowerDao dao;

    @Autowired
    JdbcTemplate template;

    public PowerDaoDbTest() {
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
        template.update("delete from hero_organization");
        template.update("delete from hero");
        template.update("delete from power");
        template.update("alter table power auto_increment = 1");
        template.update("insert into power (power) values "
                + "('super strength'), "
                + "('laser beams'), "
                + "('rocklike skin')");
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllPowers method, of class PowerDaoDb.
     */
    @Test
    public void testGetAllPowersGP() {

        Power expected = new Power(1, "super strength");
        Power expected2 = new Power(2, "laser beams");
        Power expected3 = new Power(3, "rocklike skin");

        List<Power> toCheck = dao.getAllPowers();

        assertEquals(3, toCheck.size());

        assertTrue(toCheck.contains(expected));
        assertTrue(toCheck.contains(expected2));
        assertTrue(toCheck.contains(expected3));

    }

    /**
     * Test of getPowerById method, of class PowerDaoDb.
     */
    @Test
    public void testGetPowerByIdGP() throws DaoException {

        Power expected = new Power(1, "super strength");

        Power toCheck = dao.getPowerById(1);

        assertEquals(expected, toCheck);

    }

    @Test
    public void testGetPowerByIdInvalidId() {

        try {
            dao.getPowerById(-1);
            fail("Should get DaoException with test");
        } catch (DaoException ex) {
        }
    }

    /**
     * Test of addPower method, of class PowerDaoDb.
     */
    @Test
    public void testAddPowerGP() throws DaoException {

        try {
            dao.getPowerById(4);
            fail("Should get DaoException with test");
        } catch (DaoException ex) {
        }

        Power toAdd = new Power(4, "fast fists");
        
        Power toCheck = dao.addPower(toAdd);
        assertEquals(toAdd, toCheck);
        
        toCheck = dao.getPowerById(4);
        assertEquals(toAdd, toCheck);
    }

    /**
     * Test of deletePower method, of class PowerDaoDb.
     */
    @Test
    public void testDeletePowerGP() throws DaoException {

        Power expected = new Power(1, "super strength");
        Power toCheck = dao.getPowerById(1);
        assertEquals(expected, toCheck);

        int qtySuccessfulUpdates = dao.deletePower(1);
        assertEquals(1, qtySuccessfulUpdates);

        try {
            dao.getPowerById(1);
            fail("should get DaoException when no object by ID is available");
        } catch (DaoException ex) {
        }

    }

    @Test
    public void testDeletePowerInvalidId() {

        int qtySuccessfulUpdates = dao.deletePower(-1);
        assertEquals(0, qtySuccessfulUpdates);

    }

    /**
     * Test of updatePower method, of class PowerDaoDb.
     */
    @Test
    public void testUpdatePowerGP() throws DaoException {

        Power original = new Power(1, "super strength");
        Power updated = new Power(1, "ninja skills");

        Power toCheck = dao.getPowerById(1);
        assertEquals(original, toCheck);

        int qtySuccesfulUpdates = dao.updatePower(updated);
        assertEquals(1, qtySuccesfulUpdates);

        toCheck = dao.getPowerById(1);
        assertEquals(updated, toCheck);
        assertNotEquals(original, toCheck);
    }

    @Test
    public void testUpdatePowerInvalidId() {

        Power toUpdate = new Power(-1, "super strength");
        int qtySuccessfulUpdates = dao.updatePower(toUpdate);
        assertEquals(0, qtySuccessfulUpdates);

    }

}
