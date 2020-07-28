/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.daos.HeroDaoDb.HeroMapper;
import com.dss.supers.daos.LocationDaoDb.LocationMapper;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Location;
import com.dss.supers.entities.Sighting;
import com.dss.supers.exceptions.DaoException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Schmi
 */
@Repository
@Profile("database")
public class SightingDaoDb implements SightingDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public List<Sighting> getAllSightings() {

        List<Sighting> allSightings = template.query("select * from sighting order by date desc", new SightingMapper());

        //get location for sightings
        associateLocationWithSighting(allSightings);

        //get hero for sightings
        associateHeroWithSighting(allSightings);

        return allSightings;
    }

    private void associateLocationWithSighting(List<Sighting> allSightings) {

        for (Sighting sighting : allSightings) {
            sighting.setLocation(getLocationById(sighting.getId()));
        }
    }

    private Location getLocationById(int id) {
        final String SELECT_LOCATION_BY_SIGHTING = "select * from location inner join sighting on location.id = sighting.locationId where sighting.id = ?";
        return template.queryForObject(SELECT_LOCATION_BY_SIGHTING, new LocationMapper(), id);
    }

    private void associateHeroWithSighting(List<Sighting> allSightings) {

        for (Sighting sighting : allSightings) {
            sighting.setHero(getHeroById(sighting.getId()));
        }
    }

    @Override
    public List<Sighting> getTenSightings() {

        List<Sighting> tenSightings = template.query("select * from sighting order by date desc limit 10", new SightingMapper());

        //get location for sightings
        associateLocationWithSighting(tenSightings);

        //get hero for sightings
        associateHeroWithSighting(tenSightings);

        return tenSightings;
    }

    private Hero getHeroById(int id) {
        final String SELECT_HERO_BY_SIGHTING = "select * from hero inner join sighting on hero.id = sighting.heroid where sighting.id = ?";
        return template.queryForObject(SELECT_HERO_BY_SIGHTING, new HeroMapper(), id);
    }

    @Override
    public Sighting getSightingById(int id) throws DaoException {

        try {
            Sighting sighting = template.queryForObject("select * from sighting where id = ?", new SightingMapper(), id);

            //get and set location
            sighting.setLocation(getLocationById(id));

            //get and set hero
            sighting.setHero(getHeroById(id));

            return sighting;
        } catch (DataAccessException ex) {
            throw new DaoException("No sighting available by id");
        }
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting toAdd) {

        template.update("insert into sighting (date, heroId, locationId) values (?,?,?)",
                Date.valueOf(toAdd.getDate()), toAdd.getHero().getId(), toAdd.getLocation().getId());

        int newId = template.queryForObject("Select LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);

        return toAdd;
    }

    @Override
    public int deleteSighting(int id) {

        final String DELETE_SIGHTING_BY_ID = "delete from sighting where sighting.id = ?";
        int sightingDeleted = template.update(DELETE_SIGHTING_BY_ID, id);

        return sightingDeleted;
    }

    @Override
    @Transactional
    public int editSighting(Sighting toEdit) {

        final String UPDATE_SIGHTING = "update sighting set date = ?, heroId = ?, locationId = ? where id = ?";
        int heroUpdate = template.update(UPDATE_SIGHTING, Date.valueOf(toEdit.getDate()), toEdit.getHero().getId(), toEdit.getLocation().getId(), toEdit.getId());

        return heroUpdate;
    }

    public static class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            return new Sighting(rs.getInt("id"), rs.getDate("date").toLocalDate());
        }
    }
}
