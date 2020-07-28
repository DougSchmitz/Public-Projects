/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.entities;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Schmi
 */
public class Hero {

    private int id;

    @NotBlank(message = "Hero name must not be blank")
    @Size(max = 50, message = "Hero name must not be longer than 50 characters")
    private String name;

    @NotBlank(message = "Hero description must not be blank")
    @Size(max = 50, message = "Hero description must not be longer than 50 characters")
    private String description;

    private Power superpower;

    private List<Organization> affiliatedOrganizaitons;

    public Hero() {
    }

    public Hero(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Hero(String name, String description, Power superpower, List<Organization> affiliatedOrganizaitons) {
        this.name = name;
        this.description = description;
        this.superpower = superpower;
        this.affiliatedOrganizaitons = affiliatedOrganizaitons;
    }

    public Hero(int id, String name, String description, Power superpower, List<Organization> affiliatedOrganizaitons) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.superpower = superpower;
        this.affiliatedOrganizaitons = affiliatedOrganizaitons;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.description);
        hash = 59 * hash + Objects.hashCode(this.superpower);
        hash = 59 * hash + Objects.hashCode(this.affiliatedOrganizaitons);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hero other = (Hero) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.superpower, other.superpower)) {
            return false;
        }
        if (!Objects.equals(this.affiliatedOrganizaitons, other.affiliatedOrganizaitons)) {
            return false;
        }
        return true;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the superpower
     */
    public Power getSuperpower() {
        return superpower;
    }

    /**
     * @param superpower the superpower to set
     */
    public void setSuperpower(Power superpower) {
        this.superpower = superpower;
    }

    /**
     * @return the affiliatedOrganizaitons
     */
    public List<Organization> getAffiliatedOrganizaitons() {
        return affiliatedOrganizaitons;
    }

    /**
     * @param affiliatedOrganizaitons the affiliatedOrganizaitons to set
     */
    public void setAffiliatedOrganizaitons(List<Organization> affiliatedOrganizaitons) {
        this.affiliatedOrganizaitons = affiliatedOrganizaitons;
    }

}
