/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Services;

import com.dss.supers.daos.OrganizationDao;
import com.dss.supers.entities.Organization;
import com.dss.supers.exceptions.DaoException;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.InvalidNameException;
import com.dss.supers.exceptions.NoItemsException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Schmi
 */
@Service
public class OrganizationServiceImpl {
    
    @Autowired
    OrganizationDao orgDao;

    public List<Organization> getAllOrgs() throws NoItemsException {
        
        List<Organization> allOrgs = orgDao.getAllOrgs();
        
        if (allOrgs.isEmpty()) {
            throw new NoItemsException("No orgs available");
        }
        return allOrgs;
    }

    public Organization getOrgById(int id) throws InvalidIdException {

        try {
            return orgDao.getOrgById(id);
        } catch (DaoException ex) {
            throw new InvalidIdException("Invalid id");
        }
    }

    public Organization addOrganization(Organization toAdd) throws InvalidEntityException {

        validate(toAdd);
        
        return orgDao.addOrg(toAdd);
    }
    
    public void deleteOrganization(int id) throws InvalidIdException {
        
        int response = orgDao.deleteOrg(id);
        
        if (response == 0) {
            throw new InvalidIdException("Invalid id");
        }
    }
    
    public void updateOrganization(Organization toEdit) throws InvalidEntityException, InvalidIdException {
        
        validate(toEdit);
        
        int response = orgDao.updateOrg(toEdit);
        
        if (response == 0) {
            throw new InvalidIdException("Invalid id");
        }
        
    }
    
        public Organization getByOrgName(String name) throws InvalidNameException, InvalidEntityException {

        if (name == null 
                || name.trim().length() == 0
                || name.trim().length() > 50) {
            throw new InvalidEntityException("Error, in proper fields");
        }

        try {
            return orgDao.getOrgByName(name);
        } catch (DaoException ex) {
            throw new InvalidNameException("Cannot get power by name");
        }

    }

    private void validate(Organization toAdd) throws InvalidEntityException {

        if (toAdd == null) {
            throw new InvalidEntityException("Null organization");
        }
        
        if (toAdd.getName() == null 
                || toAdd.getName().trim().length() == 0
                || toAdd.getName().trim().length() > 50
                || toAdd.getDescription() == null
                || toAdd.getDescription().trim().length() == 0
                || toAdd.getDescription().trim().length() > 50
                || toAdd.getAddress() == null
                || toAdd.getAddress().trim().length() == 0
                || toAdd.getAddress().trim().length() > 50
                || toAdd.getEmail() ==  null
                || toAdd.getEmail().trim().length() == 0
                || toAdd.getEmail().trim().length() > 50){
            throw new InvalidEntityException("Invalid Entity");
        }
    }
    
}
