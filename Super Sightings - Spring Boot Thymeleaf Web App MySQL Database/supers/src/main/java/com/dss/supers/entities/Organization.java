/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.entities;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Schmi
 */
public class Organization {

    private int id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name cannot be longer than 50 Characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 50, message = "Description cannot be longer than 250 Characters")
    private String description;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 50, message = "Address cannot be longer than 75 Characters")
    private String address;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Must be a valid email")
    @Size(max = 50, message = "Email cannot be longer than 50 characters")
    private String email;

    private List<Hero> heroes;

    public Organization() {
    }


    public Organization(String name, String description, String address, String email, List<Hero> heroes) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.email = email;
        this.heroes = heroes;
    }

    public Organization(int id, String name, String description, String address, String email) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.email = email;
    }

    public Organization(int id, String name, String description, String address, String email, List<Hero> heroes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.email = email;
        this.heroes = heroes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.description);
        hash = 59 * hash + Objects.hashCode(this.address);
        hash = 59 * hash + Objects.hashCode(this.email);
        hash = 59 * hash + Objects.hashCode(this.heroes);
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
        final Organization other = (Organization) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.heroes, other.heroes)) {
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the heroes
     */
    public List<Hero> getHeroes() {
        return heroes;
    }

    /**
     * @param heroes the heroes to set
     */
    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

}
