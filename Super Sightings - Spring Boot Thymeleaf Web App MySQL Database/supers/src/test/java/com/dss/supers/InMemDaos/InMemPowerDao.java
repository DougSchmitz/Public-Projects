/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.InMemDaos;

import com.dss.supers.daos.*;
import com.dss.supers.entities.Power;
import com.dss.supers.exceptions.DaoException;
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
public class InMemPowerDao implements PowerDao {

    List<Power> allPowers = new ArrayList<>();
    
    public void setUp() {
        
        allPowers.clear();
        Power power = new Power(1, "metal claws");
        Power power2 = new Power(2, "super jump");
        Power power3 = new Power(3, "squishy limbs");
        allPowers.add(power);
        allPowers.add(power2);
        allPowers.add(power3);
    }
    
    public void remove() {
        allPowers.clear();
    }
   

    @Override
    public List<Power> getAllPowers(){
        return allPowers;
    }

    @Override
    public Power getPowerById(int id) throws DaoException {

        Power toReturn = allPowers.stream().filter(p -> p.getId() == id).findAny().orElse(null);
        
        if (toReturn == null) {
            throw new DaoException("No match on id");
        }
        
        return toReturn;
    }

    @Override
    public Power addPower(Power toAdd) {

        int id = allPowers.stream().mapToInt(p -> p.getId()).max().orElse(0)+1;
        toAdd.setId(id);
        
        allPowers.add(toAdd);
        
        return toAdd;
    }

    @Override
    public int deletePower(int id) {

        int deletion = 0;
        
        for (Power p : allPowers) {
            if (p.getId() == id) {
                allPowers.remove(p);
                deletion = 1;
                break;
            }
        }
        return deletion;
    }

    @Override
    public int updatePower(Power toEdit) {
        
        int updated = 0;
        
        for (int i = 0; i < allPowers.size(); i++) {
            if ( allPowers.get(i).getId() == toEdit.getId() ) {
                allPowers.set(i, toEdit);
                updated = 1;
                break;
            }
        }

        return updated;
    }

    @Override
    public Power getPowerByName(String name) throws DaoException {
        return allPowers.stream().filter(p -> p.getPower().equals(name)).findFirst().orElseThrow(() -> new DaoException("No match on power name"));
    }
}
