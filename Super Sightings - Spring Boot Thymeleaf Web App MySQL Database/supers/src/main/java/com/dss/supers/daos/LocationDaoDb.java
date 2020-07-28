/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.entities.Location;
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
public class LocationDaoDb implements LocationDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public List<Location> getAllLocations() {

        return template.query("select * from location", new LocationMapper());

    }

    @Override
    public Location getLocationById(int id) throws DaoException {
        try{
        Location toReturn = template.queryForObject("select * from location where id = ?", new LocationMapper(), id);
        
        return toReturn;
        } catch (DataAccessException ex) {
            throw new DaoException("Failed to get location object back");
        }
    }

    @Override
    @Transactional
    public Location addLocation(Location toAdd) {

        template.update("insert into location("
                + "name, "
                + "description, "
                + "address, "
                + "latitude, "
                + "longitude) values (?,?,?,?,?)", 
                toAdd.getName(), toAdd.getDescription(), 
                toAdd.getAddress(), toAdd.getLatitude(), toAdd.getLongitude());
        
        int newId = template.queryForObject("Select LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);
        return toAdd;
    }

    @Override
    @Transactional
    public int deleteLocation(int id) {

        final String DELETE_SIGHTING_BY_LOCATION_ID = "delete s from sighting s inner join location l on s.locationId = l.id where l.id = ?";
        
        final String DELETE_LOCATION_BY_ID = "delete from location where id = ?";
        
        return template.update(DELETE_SIGHTING_BY_LOCATION_ID, id) + template.update(DELETE_LOCATION_BY_ID, id);
    }

    @Override
    @Transactional
    public int updateLocation(Location toEdit) {

        return template.update("update location set name = ?, description = ?,"
                + "address = ?, latitude = ?, longitude = ? where id = ?", 
                toEdit.getName(), toEdit.getDescription(), toEdit.getAddress(), 
                toEdit.getLatitude(), toEdit.getLongitude(), toEdit.getId());
    }

    @Override
    public Location getLocationByName(String name) throws DaoException {

        try {
            return template.queryForObject("select * from location where location.name = ?", new LocationMapper(), name);

        } catch (DataAccessException ex) {
            throw new DaoException("Failed to get location object back");
        }
    }

    public static class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location toReturn = new Location(rs.getInt("id"), rs.getString("name"), 
                    rs.getString("description"), rs.getString("address"), 
                    rs.getBigDecimal("latitude"), rs.getBigDecimal("longitude"));

            return toReturn;
        }
    }
}
