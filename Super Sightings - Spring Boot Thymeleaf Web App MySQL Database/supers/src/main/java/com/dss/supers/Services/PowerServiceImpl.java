/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Services;

import com.dss.supers.daos.PowerDao;
import com.dss.supers.entities.Power;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.NoItemsException;
import com.dss.supers.exceptions.DaoException;
import com.dss.supers.exceptions.InvalidNameException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Schmi
 */
@Service
public class PowerServiceImpl {

    @Autowired
    PowerDao powerDao;

    public List<Power> getAllPowers() throws NoItemsException {

        List<Power> allPowers = powerDao.getAllPowers();

        if (allPowers.isEmpty()) {
            throw new NoItemsException("No powers available");
        }

        return allPowers;

    }

    public Power getPowerById(int id) throws InvalidIdException {

        try {
            return powerDao.getPowerById(id);
        } catch (DaoException ex) {
            throw new InvalidIdException("Cannot get power by id");
        }

    }

    public Power addPower(Power toAdd) throws InvalidEntityException {

        validate(toAdd);

        return powerDao.addPower(toAdd);

    }

    public void deletePower(int id) throws InvalidIdException {

        int response = powerDao.deletePower(id);

        if (response == 0) {
            throw new InvalidIdException("Error, invalid id");
        }

    }

    public void updatePower(Power toEdit) throws InvalidEntityException, InvalidIdException {

        validate(toEdit);

        int response = powerDao.updatePower(toEdit);

        if (response == 0) {
            throw new InvalidIdException("Error, invalid id");
        }

    }

    public Power getBySuperPowerName(String name) throws InvalidNameException, InvalidEntityException {

        if (name == null
                || name.trim().length() == 0
                || name.trim().length() > 50) {
            throw new InvalidEntityException("Error, in proper fields");
        }

        try {
            return powerDao.getPowerByName(name);
        } catch (DaoException ex) {
            throw new InvalidNameException("Cannot get power by name");
        }

    }

    private void validate(Power toAdd) throws InvalidEntityException {

        if (toAdd == null) {
            throw new InvalidEntityException("Null Power");
        }

        if (toAdd.getPower() == null
                || toAdd.getPower().trim().length() == 0
                || toAdd.getPower().trim().length() > 50) {
            throw new InvalidEntityException("Error, in proper fields");
        }
    }
}
