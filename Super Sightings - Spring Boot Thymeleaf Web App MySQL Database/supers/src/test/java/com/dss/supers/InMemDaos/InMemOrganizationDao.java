/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.InMemDaos;

import com.dss.supers.daos.OrganizationDao;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Organization;
import com.dss.supers.entities.Power;
import com.dss.supers.exceptions.DaoException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Schmi
 */
@Repository
@Profile("memory")
public class InMemOrganizationDao implements OrganizationDao {

    List<Organization> allOrgs = new ArrayList();
    List<Hero> heroesList = new ArrayList<>();

    public void setUp() {

        allOrgs.clear();
        heroesList.clear();

        Hero wolverine = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), null);
        heroesList.add(wolverine);

        Organization expected = new Organization(1, "X-Men", "The X-Men fight for peace and equality", "1407 Graymalkin Lane, Salem Center, New York 11897", "info@xmen.com", heroesList);
        Organization expected2 = new Organization(2, "Avengers", "A collection of earths mighiest heroes", "890 Fifth Avenue, New York City, New York 11145", "info@avengers.com", heroesList);
        Organization expected3 = new Organization(3, "X-Factor", "Super mighty group", "1111 10th Street, Minneapolis, Minnesota 55405", "contactus@xfactor.com", heroesList);
        allOrgs.add(expected);
        allOrgs.add(expected2);
        allOrgs.add(expected3);

    }

    public void remove() {
        allOrgs.clear();
    }

    @Override
    public List<Organization> getAllOrgs() {

        return allOrgs;
    }

    @Override
    public Organization getOrgById(int id) throws DaoException {

        Organization toReturn = allOrgs.stream().filter(o -> o.getId() == id).findAny().orElse(null);
        if (toReturn == null) {
            throw new DaoException("No match on id");
        }
        return toReturn;
    }

    @Override
    public Organization addOrg(Organization toAdd) {

        int id = allOrgs.stream().mapToInt(o -> o.getId()).max().orElse(0) + 1;
        
        toAdd.setId(id);
        allOrgs.add(toAdd);
        
        return toAdd;
    }

    @Override
    public int deleteOrg(int id) {

        int deletion = 0;
        
        for ( Organization o : allOrgs) {
            if (o.getId() == id) {
                allOrgs.remove(o);
                deletion = 1;
                break;
            }
        }
        return deletion;
    }

    @Override
    public int updateOrg(Organization toEdit) {

        int updated = 0;
        
        for (int i = 0; i < allOrgs.size(); i++) {
            if (allOrgs.get(i).getId() == toEdit.getId()) {
                allOrgs.set(i, toEdit);
                updated = 1;
                break;
            }
        }
        return updated;
    }

    @Override
    public Organization getOrgByName(String name) throws DaoException {
        return allOrgs.stream().filter(o -> o.getName().equals(name)).findFirst().orElseThrow(() -> new DaoException("No match on organization name"));
    }
}
