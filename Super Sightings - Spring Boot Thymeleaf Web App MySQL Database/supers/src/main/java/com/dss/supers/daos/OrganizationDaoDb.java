/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.daos.HeroDaoDb.HeroMapper;
import com.dss.supers.daos.PowerDaoDb.PowerMapper;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Organization;
import com.dss.supers.exceptions.DaoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Schmi
 */
@Repository
@Profile("database")
public class OrganizationDaoDb implements OrganizationDao {

    @Autowired
    JdbcTemplate template;

    public List<Organization> getAllOrgs() {

        List<Organization> allOrgs = template.query("select * from organization", new OrganizationMapper());

        //get all heroes and their power for all orgs
        associateOrgsPower(allOrgs);

        return allOrgs;
    }

    private void associateOrgsPower(List<Organization> allOrgs) {
        for (Organization org : allOrgs) {
            //get heroes for each org and associate their powers
            org.setHeroes(getHeroesForOrg(org.getId()));
        }
    }

    private List<Hero> getHeroesForOrg(int id) {

        final String SELECT_HEROES_BY_ORG = "select * from hero "
                + "inner join hero_organization ho on hero.id = ho.heroId "
                + "where ho.organizationId = ?";

        List<Hero> heroesByOrg = template.query(SELECT_HEROES_BY_ORG, new HeroMapper(), id);

        //get powers for each hero
        associatePowerWithHero(heroesByOrg);

        return heroesByOrg;
    }

    private void associatePowerWithHero(List<Hero> heroesByOrg) {

        final String SELECT_POWER_BY_SUPER = "select * from power inner join hero on power.id = hero.powerid  where hero.id = ?";

        for (Hero hero : heroesByOrg) {
            hero.setSuperpower(template.queryForObject(SELECT_POWER_BY_SUPER, new PowerMapper(), hero.getId()));
        }
    }

    @Override
    public Organization getOrgById(int id) throws DaoException {

        try {
            Organization org = template.queryForObject("select * from organization where id = ?", new OrganizationMapper(), id);
            //getHeroesForOrg will also associate the powers with the hero
            org.setHeroes(getHeroesForOrg(id));

            return org;
        } catch (DataAccessException ex) {
            throw new DaoException("Failed to get organization object back");
        }
    }

    @Override
    @Transactional
    public Organization addOrg(Organization toAdd) {

        template.update("insert into organization (name, description, "
                + "address, email) values (?,?,?,?)",
                toAdd.getName(), toAdd.getDescription(), toAdd.getAddress(), toAdd.getEmail());

        int newId = template.queryForObject("Select LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);

        for (Hero hero : toAdd.getHeroes()) {
            template.update("insert into hero_organization (heroId, organizationId) values (?,?)", hero.getId(), newId);
        }

        return toAdd;
    }

    @Override
    @Transactional
    public int deleteOrg(int id) {

        final String DELETE_HERO_ORGANIZATION_BY_ORG_ID = "delete ho from hero_organization ho inner join organization o on ho.organizationId = o.id where o.id = ?";

        final String DELETE_ORGANIZATION_BY_ID = "delete from organization where id = ?";

        return template.update(DELETE_HERO_ORGANIZATION_BY_ORG_ID, id) + template.update(DELETE_ORGANIZATION_BY_ID, id);
    }

    @Override
    @Transactional
    public int updateOrg(Organization toEdit) {

        final String DELETE_HERO_ORGANIZATION_BY_ORG_ID = "delete from hero_organization ho where ho.organizationId = ?";
        final String UPDATE_ORG = "update organization set name = ?, description = ?, address = ?, email = ? where id = ?";
        final String INSERT_HERO_ORG = "insert into hero_organization (heroId, organizationId) values (?,?)";

        int orgUpdate = template.update(UPDATE_ORG, toEdit.getName(), toEdit.getDescription(), toEdit.getAddress(), toEdit.getEmail(), toEdit.getId());

        int deleteHeroOrg = template.update(DELETE_HERO_ORGANIZATION_BY_ORG_ID, toEdit.getId());

        int hero_orgInserts = 0;
        if (orgUpdate != 0) {
            for (Hero hero : toEdit.getHeroes()) {
                hero_orgInserts += template.update(INSERT_HERO_ORG, hero.getId(), toEdit.getId());
            }
        }
        return orgUpdate + deleteHeroOrg + hero_orgInserts;
    }

    @Override
    public Organization getOrgByName(String name) throws DaoException {
        try {
            return template.queryForObject("select * from organization where organization.name = ?", new OrganizationMapper(), name);

        } catch (DataAccessException ex) {
            throw new DaoException("Failed to get organization object back");
        }
    }

    public static class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            return new Organization(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getString("address"), rs.getString("email"));
        }
    }

}
