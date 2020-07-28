/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.InMemDaos;

import com.dss.supers.daos.SightingDao;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Location;
import com.dss.supers.entities.Sighting;
import com.dss.supers.exceptions.DaoException;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class InMemSightingDao implements SightingDao {

    List<Sighting> allSightings = new ArrayList<>();

    public void setUp() {
        allSightings.clear();

        Hero hero2 = new Hero(2, "Storm", "Mutant Amazon Woman", null, null);
        Location loc1 = new Location(1, "Super Hero Bar", "Supers Favorite Hangout", "121 Lake Street, Minneapolis, MN 55415", new BigDecimal("45.123456"), new BigDecimal("120.123456"));
        Hero hero1 = new Hero(1, "Cyclops", "Gifted mutant with leadership qualities", null, null);

        Sighting expected = new Sighting(1, LocalDate.parse("2010-01-01"), loc1, hero1);
        Sighting expected2 = new Sighting(2, LocalDate.parse("2015-02-01"), loc1, hero2);
        Sighting expected3 = new Sighting(3, LocalDate.parse("2015-02-01"), loc1, hero1);
        allSightings.add(expected);
        allSightings.add(expected2);
        allSightings.add(expected3);
    }

    public void remove() {
        allSightings.clear();
    }

    @Override
    public List<Sighting> getAllSightings() {
        return allSightings;
    }

    @Override
    public List<Sighting> getTenSightings() {

        List<Sighting> topTen = new ArrayList<>();
        for (int i = 0; i < allSightings.size(); i++) {
            if (i != 11) {
                Sighting sighting = allSightings.get(i);
                topTen.add(i, sighting);
            } else {
                break;
            }
        }
        return topTen;
    }

    @Override
    public Sighting getSightingById(int id) throws DaoException {

        Sighting toReturn = allSightings.stream().filter(s -> s.getId() == id).findAny().orElse(null);
        if (toReturn == null) {
            throw new DaoException("No match on id");
        }
        return toReturn;
    }

    @Override
    public Sighting addSighting(Sighting toAdd) {

        int id = allSightings.stream().mapToInt(s -> s.getId()).max().orElse(0) + 1;

        toAdd.setId(id);
        allSightings.add(toAdd);

        return toAdd;
    }

    @Override
    public int deleteSighting(int id) {

        int deletion = 0;

        for (Sighting s : allSightings) {
            if (s.getId() == id) {
                allSightings.remove(s);
                deletion = 1;
                break;
            }
        }
        return deletion;
    }

    @Override
    public int editSighting(Sighting toEdit) {

        int updated = 0;

        for (int i = 0; i < allSightings.size(); i++) {
            if (allSightings.get(i).getId() == toEdit.getId()) {
                allSightings.set(i, toEdit);
                updated = 1;
                break;
            }
        }
        return updated;
    }
}
