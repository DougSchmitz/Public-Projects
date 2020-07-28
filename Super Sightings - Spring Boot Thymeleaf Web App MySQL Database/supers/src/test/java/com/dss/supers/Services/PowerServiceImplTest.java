/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Services;

import com.dss.supers.InMemDaos.InMemPowerDao;
import com.dss.supers.TestAppConfig;
import com.dss.supers.entities.Power;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.NoItemsException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Schmi
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
//@ActiveProfiles("memory")
public class PowerServiceImplTest {

    @Autowired
    PowerServiceImpl service;

    @Autowired
    InMemPowerDao dao;

    public PowerServiceImplTest() {
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
     * Test of getAllPowers method, of class PowerServiceImpl.
     */
    @Test
    public void testGetAllPowersGP() throws NoItemsException {

        Power expected = new Power(1, "metal claws");
        Power expected2 = new Power(2, "super jump");
        Power expected3 = new Power(3, "squishy limbs");

        List<Power> toCheck = service.getAllPowers();

        assertEquals(3, toCheck.size());

        assertTrue(toCheck.contains(expected));
        assertTrue(toCheck.contains(expected2));
        assertTrue(toCheck.contains(expected3));

    }

    @Test
    public void testGetAllPowersNoItems() {

        dao.remove();

        try {
            service.getAllPowers();
            fail("Should get NoItemsException on test");
        } catch (NoItemsException ex) {

        }

    }

    /**
     * Test of getPowerById method, of class PowerServiceImpl.
     */
    @Test
    public void testGetPowerByIdGP() throws InvalidIdException {

        Power expected = new Power(1, "metal claws");

        Power toCheck = service.getPowerById(1);

        assertEquals(expected, toCheck);

    }

    @Test
    public void testGetPowerByIdInvalidId() {

        try {
            Power toCheck = service.getPowerById(-1);
            fail("Should get InvalidIdException with test");
        } catch (InvalidIdException ex) {

        }

    }

    /**
     * Test of addPower method, of class PowerServiceImpl.
     */
    @Test
    public void testAddPowerGP() throws InvalidEntityException {

        Power toAdd = new Power(4, "fast fists");

        Power toCheck = service.addPower(toAdd);
        assertEquals(toAdd, toCheck);
    }

    @Test
    public void testAddPowerNullPower() {

        Power toAdd = new Power(4, null);

        try {
            service.addPower(toAdd);
            fail("Should get InvalidEntityException with null power");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testAddPowerBlankPower() {

        Power toAdd = new Power(4, " ");

        try {
            service.addPower(toAdd);
            fail("Should get InvalidEntityException with null power");
        } catch (InvalidEntityException ex) {

        }
    }

    @Test
    public void testAddPowerTooManyChars() {

        Power toAdd = new Power(4, "aaaaabbbbbaaaaabbbbbaaaaabbbbbaaaaabbbbbaaaaabbbbbaaaaabbbbb");

        try {
            service.addPower(toAdd);
            fail("Should get InvalidEntityException with null power");
        } catch (InvalidEntityException ex) {

        }
    }

    /**
     * Test of deletePower method, of class PowerServiceImpl.
     */
    @Test
    public void testDeletePowerGP() throws InvalidIdException {

        Power expected = new Power(1, "metal claws");

        Power toCheck = service.getPowerById(1);
        assertEquals(expected, toCheck);

        service.deletePower(1);

        try {
            service.getPowerById(1);
            fail("should get InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testDeletePowerInvalidId() {

        try {
            service.deletePower(-1);
            fail("should get InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of updatePower method, of class PowerServiceImpl.
     */
    @Test
    public void testUpdatePowerGP() throws InvalidIdException, InvalidEntityException {

        Power original = new Power(1, "metal claws");
        Power updated = new Power(1, "ninja skills");

        Power toCheck = service.getPowerById(1);
        assertEquals(original, toCheck);

        service.updatePower(updated);

        toCheck = service.getPowerById(1);
        assertEquals(updated, toCheck);
        assertNotEquals(original, toCheck);
    }

    @Test
    public void testUpdatePowerInvalidId() throws InvalidEntityException {

        Power updated = new Power(-1, "metal claws");

        try {
            service.updatePower(updated);
            fail("InvalidIdException");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testUpdatePowerNullPower() throws InvalidIdException {

        Power updated = null;

        try {
            service.updatePower(updated);
            fail("InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdatePowerBlankPower() throws InvalidIdException {

        Power updated = new Power(1, "");

        try {
            service.updatePower(updated);
            fail("InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdatePowerEmptyPower() throws InvalidIdException {

        Power updated = new Power(1, "  ");

        try {
            service.updatePower(updated);
            fail("InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdatePowerTooManyChars() throws InvalidIdException {

        Power updated = new Power(1, "aaaaabbbbbaaaaabbbbbaaaaabbbbbaaaaabbbbbaaaaabbbbbaaaaabbbbb");

        try {
            service.updatePower(updated);
            fail("InvalidEntityException");
        } catch (InvalidEntityException ex) {
        }
    }

}
