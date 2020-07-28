/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.daos;

import com.dss.supers.entities.Power;
import com.dss.supers.exceptions.DaoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Schmi
 */
@Repository
@Profile("database")
public class PowerDaoDb implements PowerDao {

    @Autowired
    JdbcTemplate template;

    @Override
    public List<Power> getAllPowers() {

        return template.query("select * from power", new PowerMapper());
    }

    @Override
    public Power getPowerById(int id) throws DaoException {
        try {
            Power toReturn = template.queryForObject("select * from power where id = ?", new PowerMapper(), id);

            return toReturn;
        } catch (DataAccessException ex) {
            throw new DaoException("Failed to get power object back");
        }
    }

    @Override
    @Transactional
    public Power addPower(Power toAdd) {

        template.update("insert into power(power) values (?)", toAdd.getPower());
        int newId = template.queryForObject("Select LAST_INSERT_ID()", Integer.class);
        toAdd.setId(newId);
        return toAdd;

    }

    @Override
    @Transactional
    public int deletePower(int id) {

        final String DELETE_HERO_ORG_BY_POWER_ID = "delete ho from hero_organization ho inner join hero h on ho.heroId = h.id "
                + "inner join power p on h.powerId = p.id "
                + "where p.id = ?";

        final String DELETE_SIGHTING_BY_POWER_ID = "delete s from sighting s inner join hero h on s.heroId = h.id "
                + "inner join power p on h.powerId = p.id "
                + "where p.id = ?";

        final String DELETE_HERO_BY_POWER_ID = "delete from hero h where h.powerId = ?";

        final String DELETE_POWER_BY_ID = "delete from power where power.id = ?";

        return template.update(DELETE_HERO_ORG_BY_POWER_ID, id) + template.update(DELETE_SIGHTING_BY_POWER_ID, id)
                + template.update(DELETE_HERO_BY_POWER_ID, id) + template.update(DELETE_POWER_BY_ID, id);
    }

    @Override
    @Transactional
    public int updatePower(Power toEdit) {

        return template.update("update power set power = ? where id = ?", toEdit.getPower(), toEdit.getId());

    }

    @Override
    public Power getPowerByName(String name) throws DaoException {

        try {
            return template.queryForObject("select * from power where power.power = ?", new PowerMapper(), name);

        } catch (DataAccessException ex) {
            throw new DaoException("Failed to get power object back");
        }

    }

    public static class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int i) throws SQLException {
            Power toReturn = new Power(rs.getInt("id"), rs.getString("power"));

            return toReturn;
        }
    }

}
