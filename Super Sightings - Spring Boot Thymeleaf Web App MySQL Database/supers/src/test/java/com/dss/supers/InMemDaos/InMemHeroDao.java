/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.InMemDaos;

import com.dss.supers.daos.HeroDao;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Organization;
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
public class InMemHeroDao implements HeroDao {

    List<Hero> allHeroes = new ArrayList();
    List<Organization> orgList = new ArrayList<>();

    public void setUp() {

        allHeroes.clear();
        orgList.clear();

        Organization xmen = new Organization(1, "X-Men",
                "The X-Men fight for peace and equality",
                "1407 Graymalkin Lane, Salem Center, New York 11897",
                "info@xmen.com", null);
        orgList.add(xmen);

        Hero expected = new Hero(1, "Wolverine", "Super agile superhero", new Power(1, "super healing"), orgList);
        Hero expected2 = new Hero(2, "Cyclops", "Gifted mutant with leadership qualities", new Power(2, "beam of concussive blast"), orgList);
        Hero expected3 = new Hero(3, "Storm", "Mutant Amazon Woman", new Power(3, "can control the weather"), orgList);
        allHeroes.add(expected);
        allHeroes.add(expected2);
        allHeroes.add(expected3);
    }

    public void remove() {
        allHeroes.clear();
    }

    @Override
    public List<Hero> getAllHeroes() {

        return allHeroes;
    }

    @Override
    public Hero getHeroById(int id) throws DaoException {

        Hero toReturn = allHeroes.stream().filter(h -> h.getId() == id).findAny().orElse(null);
        if (toReturn == null) {
            throw new DaoException("No match on id");
        }
        return toReturn;
    }

    @Override
    public Hero addHero(Hero toAdd) {

        int id = allHeroes.stream().mapToInt(h -> h.getId()).max().orElse(0) + 1;

        toAdd.setId(id);
        allHeroes.add(toAdd);

        return toAdd;
    }

    @Override
    public int deleteHero(int id) {

        int deletion = 0;

        for (Hero h : allHeroes) {
            if (h.getId() == id) {
                allHeroes.remove(h);
                deletion = 1;
                break;
            }
        }
        return deletion;
    }

    @Override
    public int updateHero(Hero toEdit) {

        int updated = 0;

        for (int i = 0; i < allHeroes.size(); i++) {
            if (allHeroes.get(i).getId() == toEdit.getId()) {
                allHeroes.set(i, toEdit);
                updated = 1;
                break;
            }
        }
        return updated;
    }

    @Override
    public Hero getHeroByName(String name) throws DaoException {
        return allHeroes.stream().filter(h -> h.getName().equals(name)).findFirst().orElseThrow(() -> new DaoException("No match on hero name"));
    }
}
