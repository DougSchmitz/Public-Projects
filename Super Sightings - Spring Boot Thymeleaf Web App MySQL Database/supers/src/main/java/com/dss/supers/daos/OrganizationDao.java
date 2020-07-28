/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.entities.Organization;
import com.dss.supers.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author Schmi
 */
public interface OrganizationDao {

    public List<Organization> getAllOrgs();

    public Organization getOrgById(int id) throws DaoException;
    
    public Organization addOrg(Organization org);
    
    public int deleteOrg(int id);
    
    public int updateOrg(Organization toEdit);

    public Organization getOrgByName(String name) throws DaoException;
    
}
