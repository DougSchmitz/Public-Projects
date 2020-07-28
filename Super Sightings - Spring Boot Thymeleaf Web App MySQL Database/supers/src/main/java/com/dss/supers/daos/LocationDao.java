/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.entities.Location;
import com.dss.supers.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author Schmi
 */
public interface LocationDao {

    public List<Location> getAllLocations();

    public Location getLocationById(int id) throws DaoException;

    public Location addLocation(Location toAdd);

    public int deleteLocation(int id);

    public int updateLocation(Location toEdit);

    public Location getLocationByName(String name) throws DaoException;
    
}
