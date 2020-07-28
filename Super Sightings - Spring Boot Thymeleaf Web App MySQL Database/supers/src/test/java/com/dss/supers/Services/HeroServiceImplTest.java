/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Services;

import com.dss.supers.InMemDaos.InMemHeroDao;
import com.dss.supers.TestAppConfig;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Organization;
import com.dss.supers.entities.Power;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.NoItemsException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class HeroServiceImplTest {

    @Autowired
    HeroServiceImpl service;

    @Autowired
    InMemHeroDao dao;

    List<Organization> orgList = new ArrayList<>();
    Organization xmen = new Organization(1, "X-Men",
            "The X-Men fight for peace and equality",
            "1407 Graymalkin Lane, Salem Center, New York 11897",
            "info@xmen.com", null);

    public HeroServiceImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

        orgList.clear();
        orgList.add(xmen);

        dao.setUp();
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllHeroes method, of class HeroServiceImpl.
     */
    @Test
    public void testGetAllHeroesGP() throws NoItemsException {

        Hero expected = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), orgList);
        Hero expected2 = new Hero(2, "Cyclops", "Gifted mutant with leadership qualities", new Power(2, "beam of concussive blast"), orgList);
        Hero expected3 = new Hero(3, "Storm", "Mutant Amazon Woman", new Power(3, "can control the weather"), orgList);

        List<Hero> toCheck = service.getAllHeroes();

        assertEquals(3, toCheck.size());

        assertTrue(toCheck.contains(expected));
        assertTrue(toCheck.contains(expected2));
        assertTrue(toCheck.contains(expected3));
    }

    @Test
    public void testGetAllHeroesNoItems() {

        dao.remove();

        try {
            service.getAllHeroes();
            fail("Should get NoItemsException on test");
        } catch (NoItemsException ex) {
        }
    }

    /**
     * Test of getHeroById method, of class HeroServiceImpl.
     */
    @Test
    public void testGetHeroByIdGP() throws InvalidIdException {

        Hero expected = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), orgList);

        Hero toCheck = service.getHeroById(1);
        assertEquals(expected, toCheck);
    }

    @Test
    public void testGetHeroByIdInvalidId() {

        try {
            service.getHeroById(-1);
            fail("Should get InvalidIdException on test");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addHero method, of class HeroServiceImpl.
     */
    @Test
    public void testAddHeroGP() throws InvalidEntityException, InvalidIdException {

        try {
            service.getHeroById(4);
            fail("Should get InvalidIdException on test");
        } catch (InvalidIdException ex) {
        }

        Hero expected = new Hero(4, "Test Hero", "Test agile", new Power(1, "super healing"), orgList);

        Hero toCheck = service.addHero(expected);
        assertEquals(expected, toCheck);

        toCheck = service.getHeroById(4);
        assertEquals(expected, toCheck);
    }

    @Test
    public void testAddHeroNullName() {

        Hero expected = new Hero(4, null, "Test agile", new Power(1, "super healing"), orgList);

        try {
            Hero toCheck = service.addHero(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddHeroBlankName() {

        Hero expected = new Hero(4, "  ", "Test agile", new Power(1, "super healing"), orgList);

        try {
            Hero toCheck = service.addHero(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddHeroEmptyName() {

        Hero expected = new Hero(4, "", "Test agile", new Power(1, "super healing"), orgList);

        try {
            Hero toCheck = service.addHero(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddHeroLongName() {

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Hero expected = new Hero(4, new String(letters), "Test agile", new Power(1, "super healing"), orgList);

        try {
            Hero toCheck = service.addHero(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddHeroNullDescription() {

        Hero expected = new Hero(4, "Test name", null, new Power(1, "super healing"), orgList);

        try {
            Hero toCheck = service.addHero(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddHeroBlankDescription() {

        Hero expected = new Hero(4, "Test name", "  ", new Power(1, "super healing"), orgList);

        try {
            Hero toCheck = service.addHero(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddHeroEmptyDescription() {

        Hero expected = new Hero(4, "Test name", "", new Power(1, "super healing"), orgList);

        try {
            Hero toCheck = service.addHero(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddHeroLongDescription() {

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Hero expected = new Hero(4, "Test name", new String(letters), new Power(1, "super healing"), orgList);

        try {
            Hero toCheck = service.addHero(expected);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testDeleteHeroGP() throws InvalidIdException {

        Hero expected = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), orgList);

        Hero toCheck = service.getHeroById(1);
        assertEquals(expected, toCheck);

        service.deleteHero(1);

        try {
            service.getHeroById(1);
            fail("Should get InvalidIdException with test");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testDeleteHeroInvalidId() {

        try {
            service.deleteHero(-1);
            fail("Should get InvalidIdException with test");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testUpdateHeroGP() throws InvalidIdException, InvalidEntityException {

        Hero original = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), orgList);
        Hero updated = new Hero(1, "Test Hero", "Test agile", new Power(1, "super healing"), orgList);

        Hero toCheck = service.getHeroById(1);
        assertEquals(original, toCheck);

        service.updateHero(updated);

        toCheck = service.getHeroById(1);
        assertEquals(updated, toCheck);
        assertNotEquals(original, toCheck);
    }

    @Test
    public void testUpdateHeroInvalidId() throws InvalidEntityException {

        Hero updated = new Hero(-1, "Test Hero", "Test agile", new Power(1, "super healing"), orgList);

        try {
            service.updateHero(updated);
            fail("Should get InvalidIdException on test");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testUpdateHeroNullHero() throws InvalidIdException {

        Hero updated = null;

        try {
            service.updateHero(updated);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateHeroNullName() throws InvalidIdException {

        Hero updated = new Hero(1, null, "Test agile", new Power(1, "super healing"), orgList);

        try {
            service.updateHero(updated);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateHeroBlankName() throws InvalidIdException {

        Hero updated = new Hero(1, "", "Test agile", new Power(1, "super healing"), orgList);

        try {
            service.updateHero(updated);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateHeroEmptyName() throws InvalidIdException {

        Hero updated = new Hero(1, "  ", "Test agile", new Power(1, "super healing"), orgList);

        try {
            service.updateHero(updated);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateHeroLongName() throws InvalidIdException {

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Hero updated = new Hero(1, new String(letters), "Test agile", new Power(1, "super healing"), orgList);

        try {
            service.updateHero(updated);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateHeroNullDescription() throws InvalidIdException {

        Hero updated = new Hero(1, "Test Hero", null, new Power(1, "super healing"), orgList);

        try {
            service.updateHero(updated);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateHeroBlankDescription() throws InvalidIdException {

        Hero updated = new Hero(1, "Test Hero", "", new Power(1, "super healing"), orgList);

        try {
            service.updateHero(updated);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateHeroEmptyDescription() throws InvalidIdException {

        Hero updated = new Hero(1, "Test Hero", "  ", new Power(1, "super healing"), orgList);

        try {
            service.updateHero(updated);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateHeroLongDescription() throws InvalidIdException {

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Hero updated = new Hero(1, "Test Hero", new String(letters), new Power(1, "super healing"), orgList);

        try {
            service.updateHero(updated);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }
}
