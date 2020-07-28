/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.TestAppConfig;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Organization;
import com.dss.supers.entities.Power;
import com.dss.supers.exceptions.DaoException;
import java.util.ArrayList;
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
@SpringBootTest(classes = TestAppConfig.class)
@ActiveProfiles("database")
public class HeroDaoDbTest {

    @Autowired
    HeroDao dao;

    @Autowired
    JdbcTemplate template;

    Organization xmen = new Organization(1, "X-Men",
            "The X-Men fight for peace and equality",
            "1407 Graymalkin Lane, Salem Center, New York 11897",
            "info@xmen.com", null);

    List<Organization> orgList = new ArrayList<>();

    public HeroDaoDbTest() {
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

        template.update("delete from sighting");
        template.update("delete from hero_organization");
        template.update("delete from hero");
        template.update("delete from power");
        template.update("delete from organization");

        template.update("alter table hero auto_increment = 1");
        template.update("alter table power auto_increment = 1");
        template.update("alter table organization auto_increment = 1");

        template.update("insert into organization ( name, description, address, email ) values "
                + "( 'X-Men', 'The X-Men fight for peace and equality', '1407 Graymalkin Lane, Salem Center, New York 11897', 'info@xmen.com' )");
        template.update("insert into power (power) values "
                + "('super healing'), "
                + "('beam of concussive blast'), "
                + "('can control the weather')");
        template.update("insert into hero ( name, description, powerId) values"
                + "('Wolverine', 'Super agile superhero', 1),"
                + "('Cyclops', 'Gifted mutant with leadership qualities', 2),"
                + "('Storm', 'Mutant Amazon Woman', 3)");
        template.update("insert into hero_organization ( heroId, organizationId ) values"
                + "( 1,1 ), ( 2,1 ), ( 3,1 )");
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllHeroes method, of class HeroDaoDb.
     */
    @Test
    public void testGetAllHeroesGP() {

        Hero expected = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), orgList);
        Hero expected2 = new Hero(2, "Cyclops", "Gifted mutant with leadership qualities", new Power(2, "beam of concussive blast"), orgList);
        Hero expected3 = new Hero(3, "Storm", "Mutant Amazon Woman", new Power(3, "can control the weather"), orgList);

        List<Hero> toCheck = dao.getAllHeroes();
        assertEquals(3, toCheck.size());

        assertTrue(toCheck.contains(expected));
        assertTrue(toCheck.contains(expected2));
        assertTrue(toCheck.contains(expected3));
    }

    /**
     * Test of getHeroById method, of class HeroDaoDb.
     */
    @Test
    public void testGetHeroByIdGP() throws DaoException {

        Hero expected = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), orgList);

        Hero toCheck = dao.getHeroById(1);
        assertEquals(expected, toCheck);
    }

    @Test
    public void testGetHeroByIdInvalidId() {

        try {
            Hero toCheck = dao.getHeroById(-1);
            fail("Should get DaoException with test");
        } catch (DaoException ex) {
        }
    }

    /**
     * Test of addHero method, of class HeroDaoDb.
     */
    @Test
    public void testAddHeroGP() throws DaoException {

        try {
            Hero toCheck = dao.getHeroById(4);
            fail("Should get DaoException with test");
        } catch (DaoException ex) {
        }

        Hero expected = new Hero(4, "Test", "Test agile superhero", new Power(1, "super healing"), orgList);

        Hero toCheck = dao.addHero(expected);
        assertEquals(expected, toCheck);

        toCheck = dao.getHeroById(4);
        assertEquals(expected, toCheck);
    }

    /**
     * Test of deleteHero method, of class HeroDaoDb.
     */
    @Test
    public void testDeleteHeroGP() throws DaoException {

        Hero expected = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), orgList);

        Hero toCheck = dao.getHeroById(1);
        assertEquals(expected, toCheck);

        int qtySuccessfulUpdates = dao.deleteHero(1);
        assertNotEquals(0, qtySuccessfulUpdates);

        try {
            dao.getHeroById(1);
            fail("Should get DaoException when no object is available");
        } catch (DaoException ex) {
        }
    }

    @Test
    public void testDeleteHeroInvalidId() {

        int qtySuccessfulUpdates = dao.deleteHero(-1);
        assertEquals(0, qtySuccessfulUpdates);
    }

    /**
     * Test of updateHero method, of class HeroDaoDb.
     */
    @Test
    public void testUpdateHeroGP() throws DaoException {

        Hero original = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), orgList);
        Hero expected = new Hero(1, "Test", "Test agile", new Power(2, "beam of concussive blast"), orgList);

        Hero toCheck = dao.getHeroById(1);
        assertEquals(original, toCheck);

        int qtySuccesfulUpdates = dao.updateHero(expected);
        assertNotEquals(0, qtySuccesfulUpdates);

        toCheck = dao.getHeroById(1);
        assertEquals(expected, toCheck);
        assertNotEquals(original, toCheck);
    }

    @Test
    public void testUpdateHeroInvalidId() {

        Hero expected = new Hero(-1, "Test", "Test agile", new Power(2, "beam of concussive blast"), orgList);

        int qtySuccesfulUpdates = dao.updateHero(expected);
        assertEquals(0, qtySuccesfulUpdates);
    }
}
