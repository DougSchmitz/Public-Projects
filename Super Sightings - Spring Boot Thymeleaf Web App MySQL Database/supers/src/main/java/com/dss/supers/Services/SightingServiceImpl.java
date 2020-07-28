/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Services;

import com.dss.supers.daos.SightingDao;
import com.dss.supers.entities.Sighting;
import com.dss.supers.exceptions.DaoException;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.NoItemsException;
import java.time.LocalDate;
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
public class SightingServiceImpl {

    @Autowired
    SightingDao sightingDao;

    public List<Sighting> getAllSightings() throws NoItemsException {

        List<Sighting> allSightings = sightingDao.getAllSightings();

        if (allSightings.isEmpty()) {
            throw new NoItemsException("No sightings available");
        }
        return allSightings;
    }

    public List<Sighting> getTenSightings() throws NoItemsException {

        List<Sighting> tenSightings = sightingDao.getTenSightings();

        if (tenSightings.isEmpty()) {
            throw new NoItemsException("No sightings available");
        }
        return tenSightings;
    }

    public Sighting getSightingById(int id) throws InvalidIdException {

        try {
            return sightingDao.getSightingById(id);
        } catch (DaoException ex) {
            throw new InvalidIdException("Invalid id");
        }

    }

    public Sighting addSighting(Sighting toAdd) throws InvalidEntityException {
        
        validate(toAdd);

        return sightingDao.addSighting(toAdd);
    }

    public void deleteSighting(int id) throws InvalidIdException {

        int response = sightingDao.deleteSighting(id);

        if (response == 0) {
            throw new InvalidIdException("Invalid id");
        }

    }

    public void updateSighting(Sighting toEdit) throws InvalidIdException, InvalidEntityException {
        
        validate(toEdit);

        int response = sightingDao.editSighting(toEdit);

        if (response == 0) {
            throw new InvalidIdException("Invalid id");
        }

    }

    private void validate(Sighting toAdd) throws InvalidEntityException {

        if (toAdd == null) {
            throw new InvalidEntityException("Null Sighting");
        }
        
        if (toAdd.getDate() == null || toAdd.getDate().isAfter(LocalDate.now())) {
            throw new InvalidEntityException("Invalid entity");
        }
    }
}
