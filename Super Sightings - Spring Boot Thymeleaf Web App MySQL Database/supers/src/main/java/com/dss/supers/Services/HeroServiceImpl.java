/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Services;

import com.dss.supers.daos.HeroDao;
import com.dss.supers.entities.Hero;
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
public class HeroServiceImpl {

    @Autowired
    HeroDao heroDao;

    public List<Hero> getAllHeroes() throws NoItemsException {

        List<Hero> allHeroes = heroDao.getAllHeroes();

        if (allHeroes.isEmpty()) {
            throw new NoItemsException("No heroes available");
        }
        return allHeroes;
    }

    public Hero getHeroById(int id) throws InvalidIdException {

        try {
            return heroDao.getHeroById(id);
        } catch (DaoException ex) {
            throw new InvalidIdException("Invalid id");
        }
    }

    public Hero addHero(Hero toAdd) throws InvalidEntityException {

        validate(toAdd);

        return heroDao.addHero(toAdd);
    }

    public void deleteHero(int id) throws InvalidIdException {

        int response = heroDao.deleteHero(id);

        if (response == 0) {
            throw new InvalidIdException("Invalid id");
        }
    }

    public void updateHero(Hero toEdit) throws InvalidEntityException, InvalidIdException {

        validate(toEdit);

        int response = heroDao.updateHero(toEdit);

        if (response == 0) {
            throw new InvalidIdException("Invalid id");
        }
    }

    public Hero getByHeroName(String name) throws InvalidNameException, InvalidEntityException {

        if (name == null
                || name.trim().length() == 0
                || name.trim().length() > 50) {
            throw new InvalidEntityException("Invalid entity");
        }
        try {
            return heroDao.getHeroByName(name);
        } catch (DaoException ex) {
            throw new InvalidNameException("Cannot get power by name");
        }

    }

    private void validate(Hero toAdd) throws InvalidEntityException {

        if (toAdd == null) {
            throw new InvalidEntityException("Null hero");
        }

        if (toAdd.getName() == null
                || toAdd.getName().trim().length() == 0
                || toAdd.getName().trim().length() > 50
                || toAdd.getDescription() == null
                || toAdd.getDescription().trim().length() == 0
                || toAdd.getDescription().trim().length() > 50) {
            throw new InvalidEntityException("Invalid entity");
        }
    }
}
