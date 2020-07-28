/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.entities.Power;
import com.dss.supers.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author Schmi
 */
public interface PowerDao {

    public List<Power> getAllPowers();

    public Power getPowerById(int id) throws DaoException;

    public Power addPower(Power toAdd);

    public int deletePower(int id);

    public int updatePower(Power toEdit);

    public Power getPowerByName(String name) throws DaoException;

    
    
    
}
