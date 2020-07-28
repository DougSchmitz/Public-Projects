/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.InMemDaos;

import com.dss.supers.daos.LocationDao;
import com.dss.supers.entities.Location;
import com.dss.supers.exceptions.DaoException;
import java.math.BigDecimal;
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
public class InMemLocationDao implements LocationDao {

    List<Location> allLocations = new ArrayList<>();

    public void setUp() {

        allLocations.clear();
        Location location = new Location(1, "Super Hero Bar", "Supers Favorite Hangout",
                "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"),
                new BigDecimal("120.123456"));
        Location location2 = new Location(2, "Marvel House", "Hangout for Marvel Supers",
                "52nd Street, Minneapolis, MN 55425", new BigDecimal("55.123456"),
                new BigDecimal("125.123456"));
        Location location3 = new Location(3, "Guthrie", "A cool place to watch a show",
                "178 River Road, Minneapolis, MN 55405", new BigDecimal("65.123456"),
                new BigDecimal("130.123456"));
        allLocations.add(location);
        allLocations.add(location2);
        allLocations.add(location3);
    }

    public void remove() {
        allLocations.clear();
    }

    @Override
    public List<Location> getAllLocations() {
        return allLocations;
    }

    @Override
    public Location getLocationById(int id) throws DaoException {
        Location toReturn = allLocations.stream().filter(l -> l.getId() == id).findAny().orElse(null);
        if (toReturn == null) {
            throw new DaoException("No match on id");
        }
        return toReturn;
    }

    @Override
    public Location addLocation(Location toAdd) {

        int id = allLocations.stream().mapToInt(l -> l.getId()).max().orElse(0) + 1;
       
        toAdd.setId(id);
        allLocations.add(toAdd);

        return toAdd;
    }

    @Override
    public int deleteLocation(int id) {

        int deletion = 0;

        for (Location l : allLocations) {
            if (l.getId() == id) {
                allLocations.remove(l);
                deletion = 1;
                break;
            }
        }
        return deletion;
    }

    @Override
    public int updateLocation(Location toEdit) {

        int updated = 0;

        for (int i = 0; i < allLocations.size(); i++) {
            if (allLocations.get(i).getId() == toEdit.getId()) {
                allLocations.set(i, toEdit);
                updated = 1;
                break;
            }
        }
        return updated;
    }

    @Override
    public Location getLocationByName(String name) throws DaoException {
        return allLocations.stream().filter(l -> l.getName().equals(name)).findFirst().orElseThrow(() -> new DaoException("No match on location name"));
    }
}
