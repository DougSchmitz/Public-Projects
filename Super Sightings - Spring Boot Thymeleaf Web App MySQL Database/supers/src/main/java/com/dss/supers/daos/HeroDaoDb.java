/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.daos.OrganizationDaoDb.OrganizationMapper;
import com.dss.supers.daos.PowerDaoDb.PowerMapper;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Organization;
import com.dss.supers.entities.Power;
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
public class HeroDaoDb implements HeroDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public List<Hero> getAllHeroes() {

        List<Hero> allHeroes = template.query("select * from hero", new HeroMapper());

        //get power for heroes
        associatePowerWithHero(allHeroes);

        //get all orgs for hero
        associateOrgsWithHero(allHeroes);

        return allHeroes;
    }

    private void associatePowerWithHero(List<Hero> allHeroes) {

        for (Hero hero : allHeroes) {
            hero.setSuperpower(getSuperPowerById(hero.getId()));
        }
    }

    private void associateOrgsWithHero(List<Hero> allHeroes) {

        for (Hero hero : allHeroes) {
            //get orgs for each hero and associate their powers
            hero.setAffiliatedOrganizaitons(getOrgsById(hero.getId()));
        }
    }

    private Power getSuperPowerById(int id) {

        final String SELECT_POWER_BY_SUPER = "select * from power inner join hero on power.id = hero.powerid  where hero.id = ?";
        return template.queryForObject(SELECT_POWER_BY_SUPER, new PowerMapper(), id);
    }

    private List<Organization> getOrgsById(int id) {

        final String SELECT_ORGS_BY_HERO = "select * from organization "
                + "inner join hero_organization ho on organization.id = ho.organizationId "
                + "where ho.heroId = ?";
        return template.query(SELECT_ORGS_BY_HERO, new OrganizationMapper(), id);
    }

    @Override
    public Hero getHeroById(int id) throws DaoException {

        try {
            Hero hero = template.queryForObject("select * from hero where id = ?", new HeroMapper(), id);
            //get and set power for hero
            hero.setSuperpower(getSuperPowerById(id));
            //get and set orgs for hero
            hero.setAffiliatedOrganizaitons(getOrgsById(id));

            return hero;
        } catch (DataAccessException ex) {
            throw new DaoException("No hero available by id");
        }
    }

    @Override
    @Transactional
    public Hero addHero(Hero toAdd) {

        template.update("insert into hero (name, description, powerId) "
                + "values (?,?,?)", toAdd.getName(), toAdd.getDescription(),
                toAdd.getSuperpower().getId());

        int newId = template.queryForObject("Select LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);

        for (Organization org : toAdd.getAffiliatedOrganizaitons()) {
            template.update("insert into hero_organization (heroId, organizationId) values (?,?)", newId, org.getId());
        }
        return toAdd;
    }

    @Override
    @Transactional
    public int deleteHero(int id) {

        final String DELETE_SIGHTING_BY_HERO_ID = "delete s from sighting s inner join hero h on s.heroId = h.id where h.id = ?";
        int sightingDeleted = template.update(DELETE_SIGHTING_BY_HERO_ID, id);

        final String DELETE_HERO_ORGANIZATION_BY_HERO_ID = "delete ho from hero_organization ho inner join hero h on ho.heroId = h.id where h.id = ?";
        int hero_orgDeleted = template.update(DELETE_HERO_ORGANIZATION_BY_HERO_ID, id);

        final String DELETE_HERO_BY_ID = "delete from hero where id = ?";
        int hero = template.update(DELETE_HERO_BY_ID, id);

        int toReturn = sightingDeleted + hero_orgDeleted + hero;

        return toReturn;
    }

    @Override
    @Transactional
    public int updateHero(Hero toEdit) {

        final String DELETE_HERO_ORGANIZATION_BY_HERO_ID = "delete from hero_organization ho where ho.heroId = ?";
        final String UPDATE_HERO = "update hero set name = ?, description = ?, powerId = ? where id = ?";
        final String INSERT_HERO_ORG = "insert into hero_organization (heroId, organizationId) values (?,?)";

        int heroUpdate = template.update(UPDATE_HERO, toEdit.getName(), toEdit.getDescription(), toEdit.getSuperpower().getId(), toEdit.getId());

        int deleteHeroOrganization = template.update(DELETE_HERO_ORGANIZATION_BY_HERO_ID, toEdit.getId());

        int hero_organizationInserts = 0;
        if (heroUpdate != 0) {
            for (Organization org : toEdit.getAffiliatedOrganizaitons()) {
                hero_organizationInserts += template.update(INSERT_HERO_ORG, toEdit.getId(), org.getId());
            }
        }
        return heroUpdate + deleteHeroOrganization + hero_organizationInserts;
    }

    @Override
    public Hero getHeroByName(String name) throws DaoException {

        try {
            return template.queryForObject("select * from hero where hero.name = ?", new HeroMapper(), name);

        } catch (DataAccessException ex) {
            throw new DaoException("Failed to get power object back");
        }
    }

    public static class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int i) throws SQLException {
            return new Hero(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
        }
    }
}
