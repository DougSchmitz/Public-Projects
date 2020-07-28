/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Services;

import com.dss.supers.daos.LocationDao;
import com.dss.supers.entities.Location;
import com.dss.supers.exceptions.DaoException;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.InvalidNameException;
import com.dss.supers.exceptions.NoItemsException;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Schmi
 */
@Service
public class LocationServiceImpl {

    @Autowired
    LocationDao locationDao;

    public List<Location> getAllLocations() throws NoItemsException {

        List<Location> allLocations = locationDao.getAllLocations();

        if (allLocations.isEmpty()) {
            throw new NoItemsException("No locations available");
        }

        return allLocations;
    }

    public Location getLocationById(int id) throws InvalidIdException {

        try {
            return locationDao.getLocationById(id);
        } catch (DaoException ex) {
            throw new InvalidIdException("Invalid id");
        }
    }

    public Location addLocation(Location toAdd) throws InvalidEntityException {

        validate(toAdd);

        return locationDao.addLocation(toAdd);

    }

    public void deleteLocation(int id) throws InvalidIdException {

        int response = locationDao.deleteLocation(id);

        if (response == 0) {
            throw new InvalidIdException("Invalid id");
        }

    }

    public void updateLocation(Location toEdit) throws InvalidEntityException, InvalidIdException {

        validate(toEdit);
        
        int response = locationDao.updateLocation(toEdit);
        
        if (response == 0) {
            throw new InvalidIdException("Invalid id");
        }
        
    }
    
        public Location getByLocationName(String name) throws InvalidNameException, InvalidEntityException {

        if (name == null
                || name.trim().length() == 0
                || name.trim().length() > 50) {
            throw new InvalidEntityException("Error, in proper fields");
        }

        try {
            return locationDao.getLocationByName(name);
        } catch (DaoException ex) {
            throw new InvalidNameException("Cannot get location by name");
        }

    }

    private void validate(Location toAdd) throws InvalidEntityException {
        
        if (toAdd == null) {
            throw new InvalidEntityException("Null Locations");
        }

        if (toAdd.getName() == null
                || toAdd.getName().trim().length() == 0
                || toAdd.getName().trim().length() > 50
                || toAdd.getDescription() == null
                || toAdd.getDescription().trim().length() == 0
                || toAdd.getDescription().trim().length() > 75
                || toAdd.getAddress() == null
                || toAdd.getAddress().trim().length() == 0
                || toAdd.getAddress().trim().length() > 75
                || toAdd.getLatitude() == null
                || toAdd.getLatitude().compareTo(new BigDecimal("-90.000001")) == -1
                || toAdd.getLatitude().compareTo(new BigDecimal("90.000001")) == 1
                || toAdd.getLongitude() == null
                || toAdd.getLongitude().compareTo(new BigDecimal("-180.000001")) == -1
                || toAdd.getLongitude().compareTo(new BigDecimal("180.000001")) == 1) {
            throw new InvalidEntityException("Invalid Entity");
        }
    }

}
