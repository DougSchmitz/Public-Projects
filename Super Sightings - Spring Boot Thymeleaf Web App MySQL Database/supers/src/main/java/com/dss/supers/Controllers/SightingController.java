/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Controllers;

import com.dss.supers.Services.HeroServiceImpl;
import com.dss.supers.Services.LocationServiceImpl;
import com.dss.supers.Services.SightingServiceImpl;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Location;
import com.dss.supers.entities.Sighting;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.NoItemsException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Schmi
 */
@Controller
public class SightingController {

    @Autowired
    SightingServiceImpl sightingService;

    @Autowired
    LocationServiceImpl locationService;

    @Autowired
    HeroServiceImpl heroService;

    @GetMapping("sightings")
    public String displaySightings(Model model) {

        List<Sighting> allSightings = new ArrayList<>();
        Sighting sighting = new Sighting();
        try {
            allSightings = sightingService.getAllSightings();
        } catch (NoItemsException ex) {
        }

        List<Location> allLocations = new ArrayList();
        try {
            allLocations = locationService.getAllLocations();
        } catch (NoItemsException ex) {
        }

        List<Hero> allHeroes = new ArrayList();
        try {
            allHeroes = heroService.getAllHeroes();
        } catch (NoItemsException ex) {
        }

        model.addAttribute("sightings", allSightings);
        model.addAttribute("sighting", sighting);
        model.addAttribute("locations", allLocations);
        model.addAttribute("heroes", allHeroes);

        return "sightings";
    }

    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) throws InvalidIdException {
        Sighting sighting = sightingService.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }

    @PostMapping("addSighting")
    public String addSighting(@Valid Sighting toAdd, BindingResult result, HttpServletRequest request, Model model) throws InvalidIdException, InvalidEntityException {


        if (toAdd.getHero() == null) {
            toAdd.setHero(new Hero());
            FieldError error = new FieldError("sighting", "hero", "Sighting must have one hero");
            result.addError(error);
        } else {
            toAdd.setHero(heroService.getHeroById(toAdd.getHero().getId()));
        }

        if (toAdd.getLocation() == null) {
            toAdd.setLocation(new Location());
            FieldError error = new FieldError("sighting", "location", "Sighting must have one location");
            result.addError(error);
        } else {
            toAdd.setLocation(locationService.getLocationById(toAdd.getLocation().getId()));
        }

        if (result.hasErrors()) {

            List<Sighting> allSightings = new ArrayList<>();
            List<Hero> allHeroes = new ArrayList();
            List<Location> allLocations = new ArrayList();

            try {
                allSightings = sightingService.getAllSightings();
            } catch (NoItemsException ex) {
            }

            try {
                allLocations = locationService.getAllLocations();
            } catch (NoItemsException ex) {
            }

            try {
                allHeroes = heroService.getAllHeroes();
            } catch (NoItemsException ex) {
            }
            model.addAttribute("sightings", allSightings);
            model.addAttribute("sighting", toAdd);
            model.addAttribute("locations", allLocations);
            model.addAttribute("heroes", allHeroes);

            return "sightings";
        }
        sightingService.addSighting(toAdd);

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) throws InvalidIdException {

        sightingService.deleteSighting(id);

        return "redirect:/sightings";
    }

    @GetMapping("display-editSighting")
    public String displayEditSighting(Integer id, Model model) throws InvalidIdException {

        Sighting toEdit = sightingService.getSightingById(id);
        List<Hero> allHeroes = new ArrayList();
        List<Location> allLocations = new ArrayList();

        try {
            allHeroes = heroService.getAllHeroes();
        } catch (NoItemsException ex) {
        }

        try {
            allLocations = locationService.getAllLocations();
        } catch (NoItemsException ex) {
        }

        model.addAttribute("sighting", toEdit);
        model.addAttribute("heroes", allHeroes);
        model.addAttribute("locations", allLocations);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String editSighting(@Valid Sighting toEdit, BindingResult result, HttpServletRequest request, Model model) throws InvalidIdException, InvalidEntityException {

        if (toEdit.getHero() == null) {
            toEdit.setHero(new Hero());
            FieldError error = new FieldError("sighting", "hero", "Sighting must have one hero");
            result.addError(error);
        } else {
            toEdit.setHero(heroService.getHeroById(toEdit.getHero().getId()));
        }

        if (toEdit.getLocation() == null) {
            toEdit.setLocation(new Location());
            FieldError error = new FieldError("sighting", "location", "Sighting must have one location");
            result.addError(error);
        } else {
            toEdit.setLocation(locationService.getLocationById(toEdit.getLocation().getId()));
        }

        if (result.hasErrors()) {

            List<Hero> allHeroes = new ArrayList();
            List<Location> allLocations = new ArrayList();

            try {
                allLocations = locationService.getAllLocations();
            } catch (NoItemsException ex) {
            }

            try {
                allHeroes = heroService.getAllHeroes();
            } catch (NoItemsException ex) {
            }
            model.addAttribute("sighting", toEdit);
            model.addAttribute("locations", allLocations);
            model.addAttribute("heroes", allHeroes);

            return "editSighting";
        }
        sightingService.updateSighting(toEdit);

        return "redirect:/sightings";
    }

}
