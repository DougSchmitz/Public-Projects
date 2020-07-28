/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.entities.Hero;
import com.dss.supers.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author Schmi
 */
public interface HeroDao {

    public List<Hero> getAllHeroes();

    public Hero getHeroById(int id) throws DaoException;
    
    public Hero addHero(Hero hero);
    
    public int deleteHero(int id);
    
    public int updateHero(Hero toEdit);

    public Hero getHeroByName(String name) throws DaoException;
    
}
