/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Services;

import com.dss.supers.InMemDaos.InMemOrganizationDao;
import com.dss.supers.TestAppConfig;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Organization;
import com.dss.supers.entities.Power;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.NoItemsException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author Schmi
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestAppConfig.class)
//@ActiveProfiles("memory")
public class OrganizationServiceImplTest {

    @Autowired
    OrganizationServiceImpl service;

    @Autowired
    InMemOrganizationDao dao;

    public OrganizationServiceImplTest() {
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
     * Test of getAllOrgs method, of class OrganizationServiceImpl.
     */
    @Test
    public void testGetAllOrgsGP() throws NoItemsException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "X-Men", "The X-Men fight for peace and equality", "1407 Graymalkin Lane, Salem Center, New York 11897", "info@xmen.com", heroesList);
        Organization expected2 = new Organization(2, "Avengers", "A collection of earths mighiest heroes", "890 Fifth Avenue, New York City, New York 11145", "info@avengers.com", heroesList);
        Organization expected3 = new Organization(3, "X-Factor", "Super mighty group", "1111 10th Street, Minneapolis, Minnesota 55405", "contactus@xfactor.com", heroesList);

        List<Organization> toCheck = service.getAllOrgs();

        assertEquals(3, toCheck.size());

        assertTrue(toCheck.contains(expected));
        assertTrue(toCheck.contains(expected2));
        assertTrue(toCheck.contains(expected3));
    }

    @Test
    public void testGetAllOrgsNoItems() {

        dao.remove();

        try {
            service.getAllOrgs();
            fail("Should get NoItemsException on test");
        } catch (NoItemsException ex) {
        }
    }

    /**
     * Test of getOrgById method, of class OrganizationServiceImpl.
     */
    @Test
    public void testGetOrgByIdGP() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "X-Men", "The X-Men fight for peace and equality", "1407 Graymalkin Lane, Salem Center, New York 11897", "info@xmen.com", heroesList);

        Organization toCheck = service.getOrgById(1);
        assertEquals(expected, toCheck);
    }

    @Test
    public void testGetOrgByIdInvalidId() {

        try {
            service.getOrgById(-1);
            fail("Should get InvalidIdException on test");
        } catch (InvalidIdException ex) {
        }
    }

    /**
     * Test of addOrganization method, of class OrganizationServiceImpl.
     */
    @Test
    public void testAddOrganizationGP() throws InvalidEntityException, InvalidIdException {

        try {
            service.getOrgById(4);
            fail("Should get InvalidIdException on test");
        } catch (InvalidIdException ex) {
        }

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(4, "Test Group Name", "Test for peace and equality", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        Organization toCheck = service.addOrganization(expected);
        assertEquals(expected, toCheck);

        toCheck = service.getOrgById(4);
        assertEquals(expected, toCheck);
    }

    @Test
    public void testAddOrganizationNullName() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, null, "Test for peace and equality", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationEmptyName() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "", "Test for peace and equality", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationBlankName() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, " ", "Test for peace and equality", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationLongName() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Organization expected = new Organization(1, new String(letters), "Test for peace and equality", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationNullDescription() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "Test name", null, "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationEmptyDescription() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "Test name", "", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            Organization toCheck = service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationBlankDescription() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "Test name", "  ", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationLongDescription() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Organization expected = new Organization(1, "Test name", new String(letters), "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationNullAddress() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "Test name", "Test for peace and equality", null, "test@xmen.com", heroesList);

        try {
            service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationEmptyAddress() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "Test name", "Test for peace and equality", "", "test@xmen.com", heroesList);

        try {
            Organization toCheck = service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationBlankAddress() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "Test name", "Test for peace and equality", "  ", "test@xmen.com", heroesList);

        try {
            service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationLongAddress() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Organization expected = new Organization(1, "Test name", "Test for peace and equality", new String(letters), "test@xmen.com", heroesList);

        try {
            service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationNullEmail() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "Test name", "Test for peace and equality", "1407 Test Lane, Salem Center, New York 11897", null, heroesList);

        try {
            Organization toCheck = service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationEmptyEmail() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "Test name", "Test for peace and equality", "1407 Test Lane, Salem Center, New York 11897", "", heroesList);

        try {
            Organization toCheck = service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationBlankEmail() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "Test name", "Test for peace and equality", "1407 Test Lane, Salem Center, New York 11897", "  ", heroesList);

        try {
            Organization toCheck = service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testAddOrganizationLongEmail() {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Test Super", "Test agile", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Organization expected = new Organization(1, "Test name", "Test for peace and equality", "1407 Test Lane, Salem Center, New York 11897", new String(letters), heroesList);

        try {
            Organization toCheck = service.addOrganization(expected);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testDeleteOrganizationGP() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "X-Men", "The X-Men fight for peace and equality", "1407 Graymalkin Lane, Salem Center, New York 11897", "info@xmen.com", heroesList);

        Organization toCheck = service.getOrgById(1);
        assertEquals(expected, toCheck);

        service.deleteOrganization(1);

        try {
            service.getOrgById(1);
            fail("Should get InvalidIdException with test");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testDeleteOrganizationInvalidId() {

        try {
            service.deleteOrganization(-1);
            fail("Should get InvalidIdException with test");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationGP() throws InvalidIdException, InvalidEntityException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization original = new Organization(1, "X-Men", "The X-Men fight for peace and equality", "1407 Graymalkin Lane, Salem Center, New York 11897", "info@xmen.com", heroesList);
        Organization updated = new Organization(1, "Test", "Test fight for peace ", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        Organization toCheck = service.getOrgById(1);
        assertEquals(original, toCheck);

        service.updateOrganization(updated);

        toCheck = service.getOrgById(1);
        assertEquals(updated, toCheck);
        assertNotEquals(original, toCheck);
    }

    @Test
    public void testUpdateOrganizationInvalidId() throws InvalidEntityException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(-1, "Test", "Test fight for peace ", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidIdException with test");
        } catch (InvalidIdException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationNullOrg() throws InvalidIdException {

        Organization updated = null;

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException on test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationNullName() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(1, null, "Test fight for peace ", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationEmptyName() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(1, "", "Test fight for peace ", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationBlankName() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(1, "  ", "Test fight for peace ", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationLongName() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Organization updated = new Organization(1, new String(letters), "Test fight for peace ", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationNullDescription() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(1, "Test", null, "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationEmptyDescription() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(1, "Test", "", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationBlankDescription() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(1, "Test", "  ", "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationLongDescription() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Organization updated = new Organization(1, "Test", new String(letters), "1407 Test Lane, Salem Center, New York 11897", "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationNullAddress() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(1, "Test", "Test fight for peace", null, "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationEmptyAddress() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(1, "Test", "Test fight for peace", "", "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationBlankAddress() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(1, "Test", "Test fight for peace", "  ", "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationLongAddress() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Organization updated = new Organization(1, "Test", "Test fight for peace", new String(letters), "test@xmen.com", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationNullEmail() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(1, "Test", "Test fight for peace", "1407 Test Lane, Salem Center, New York 11897", null, heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationEmptyEmail() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(1, "Test", "Test fight for peace", "1407 Test Lane, Salem Center, New York 11897", "", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationBlankEmail() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization updated = new Organization(1, "Test", "Test fight for peace", "1407 Test Lane, Salem Center, New York 11897", "  ", heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }

    @Test
    public void testUpdateOrganizationLongEmail() throws InvalidIdException {

        List<Hero> heroesList = new ArrayList<>();
        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        char[] letters = new char[51];
        Arrays.fill(letters, 'a');

        Organization updated = new Organization(1, "Test", "Test fight for peace", "1407 Test Lane, Salem Center, New York 11897", new String(letters), heroesList);

        try {
            service.updateOrganization(updated);
            fail("Should get InvalidEntityException with test");
        } catch (InvalidEntityException ex) {
        }
    }
}
