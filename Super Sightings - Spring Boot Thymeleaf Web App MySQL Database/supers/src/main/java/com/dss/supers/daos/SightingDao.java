/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.entities.Sighting;
import com.dss.supers.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author Schmi
 */
public interface SightingDao {

    public List<Sighting> getAllSightings();
    
    public List<Sighting> getTenSightings();
    
    public Sighting getSightingById(int id) throws DaoException;
    
    public Sighting addSighting(Sighting toAdd);
    
    public int deleteSighting(int id);
    
    public int editSighting(Sighting toEdit);
    
    
}
