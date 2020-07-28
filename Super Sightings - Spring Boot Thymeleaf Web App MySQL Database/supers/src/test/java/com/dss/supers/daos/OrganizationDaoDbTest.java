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
public class OrganizationDaoDbTest {

    @Autowired
    OrganizationDao dao;

    @Autowired
    JdbcTemplate template;

    Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
    List<Hero> heroesList = new ArrayList<>();

    public OrganizationDaoDbTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

        heroesList.clear();
        heroesList.add(wolverine);

        template.update("delete from sighting");
        template.update("delete from hero_organization");
        template.update("delete from hero");
        template.update("delete from power");
        template.update("delete from organization");

        template.update("alter table hero auto_increment = 1");
        template.update("alter table power auto_increment = 1");
        template.update("alter table organization auto_increment = 1");

        template.update("insert into organization ( name, description, address, email ) values "
                + "( 'X-Men', 'The X-Men fight for peace and equality', '1407 Graymalkin Lane, Salem Center, New York 11897', 'info@xmen.com' ), "
                + "( 'Avengers', 'A collection of earths mighiest heroes', '890 Fifth Avenue, New York City, New York 11145', 'info@avengers.com' ),"
                + "( 'X-Factor', 'Super mighty group', '1111 10th Street, Minneapolis, Minnesota 55405', 'contactus@xfactor.com' )");
        template.update("insert into power (power) values "
                + "('super healing')");
        template.update("insert into hero ( name, description, powerId) values"
                + "('Wolverine', 'Super agile superhero', 1)");
        template.update("insert into hero_organization ( heroId, organizationId ) values"
                + "( 1,1 ), ( 1,2 ), ( 1,3 )");

    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getAllOrgs method, of class OrganizationDaoDb.
     */
    @Test
    public void testGetAllOrgsGP() {

        Organization expected = new Organization(1, "X-Men", "The X-Men fight for peace and equality", "1407 Graymalkin Lane, Salem Center, New York 11897", "info@xmen.com", heroesList);
        Organization expected2 = new Organization(2, "Avengers", "A collection of earths mighiest heroes", "890 Fifth Avenue, New York City, New York 11145", "info@avengers.com", heroesList);
        Organization expected3 = new Organization(3, "X-Factor", "Super mighty group", "1111 10th Street, Minneapolis, Minnesota 55405", "contactus@xfactor.com", heroesList);

        List<Organization> toCheck = dao.getAllOrgs();

        assertEquals(3, toCheck.size());

        assertTrue(toCheck.contains(expected));
        assertTrue(toCheck.contains(expected2));
        assertTrue(toCheck.contains(expected3));

    }

    @Test
    public void testGetOrgByIdGP() throws DaoException {

        Organization expected = new Organization(1, "X-Men", "The X-Men fight for peace and equality", "1407 Graymalkin Lane, Salem Center, New York 11897", "info@xmen.com", heroesList);
        Organization toCheck = dao.getOrgById(1);
        assertEquals(expected, toCheck);
    }

    @Test
    public void testGetOrgByIdInvalidId() {

        try {
            dao.getOrgById(-1);
            fail("Should get DaoException on test");
        } catch (DaoException ex) {
        }
    }

    @Test
    public void testAddOrgGP() throws DaoException {

        try {
            dao.getOrgById(4);
            fail("Should get DaoException on test");
        } catch (DaoException ex) {
        }

        Organization expected = new Organization(4, "Test-Men", "Test fight for equality", "Test Address", "Test@xmen.com", heroesList);

        Organization toCheck = dao.addOrg(expected);
        assertEquals(expected, toCheck);

        toCheck = dao.getOrgById(4);
        assertEquals(expected, toCheck);
    }

    @Test
    public void testDeleteOrgGP() throws DaoException {

        Organization expected = new Organization(1, "X-Men", "The X-Men fight for peace and equality", "1407 Graymalkin Lane, Salem Center, New York 11897", "info@xmen.com", heroesList);
        Organization toCheck = dao.getOrgById(1);
        assertEquals(expected, toCheck);

        int qtySuccessfulUpdates = dao.deleteOrg(1);
        assertNotEquals(0, qtySuccessfulUpdates);

        try {
            dao.getOrgById(1);
            fail("Should get DaoException when no object is available");
        } catch (DaoException ex) {
        }
    }

    @Test
    public void testDeleteOrgInvalidId() throws DaoException {

        int qtySuccessfulUpdates = dao.deleteOrg(-1);
        assertEquals(0, qtySuccessfulUpdates);
    }

    @Test
    public void testUpdateOrgGP() throws DaoException {

        Organization original = new Organization(1, "X-Men", "The X-Men fight for peace and equality", "1407 Graymalkin Lane, Salem Center, New York 11897", "info@xmen.com", heroesList);
        Organization expected = new Organization(1, "Test", "Test for peace and equality", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        Organization toCheck = dao.getOrgById(1);
        assertEquals(original, toCheck);

        int qtySuccesfulUpdates = dao.updateOrg(expected);
        assertNotEquals(0, qtySuccesfulUpdates);

        toCheck = dao.getOrgById(1);
        assertEquals(expected, toCheck);
        assertNotEquals(original, toCheck);
    }

    @Test
    public void testUpdateOrgInvalidId() throws DaoException {

        Organization expected = new Organization(-1, "Test", "Test for peace and equality", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        int qtySuccesfulUpdates = dao.updateOrg(expected);
        assertEquals(0, qtySuccesfulUpdates);
    }
}
