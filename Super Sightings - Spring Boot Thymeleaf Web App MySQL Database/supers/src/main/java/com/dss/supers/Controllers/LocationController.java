/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Controllers;

import com.dss.supers.Services.LocationServiceImpl;
import com.dss.supers.Services.SightingServiceImpl;
import com.dss.supers.entities.Location;
import com.dss.supers.entities.Sighting;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.InvalidNameException;
import com.dss.supers.exceptions.NoItemsException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
public class LocationController {

    @Autowired
    LocationServiceImpl locationService;

    @Autowired
    SightingServiceImpl sightingService;

    @GetMapping("locations")
    public String displayLocations(Model model) {

        List<Location> allLocations = new ArrayList<>();
        Location location = new Location();
        try {
            allLocations = locationService.getAllLocations();
        } catch (NoItemsException ex) {
        }
        model.addAttribute("locations", allLocations);
        model.addAttribute("location", location);
        return "locations";
    }

    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model) throws InvalidIdException {
        Location location = locationService.getLocationById(id);
        List<Sighting> matchingSightings = new ArrayList<>();

        try {
            matchingSightings = sightingService.getAllSightings();
            matchingSightings = matchingSightings.stream().filter(s -> s.getLocation().getId() == id).collect(Collectors.toList());
        } catch (NoItemsException ex) {
        }

        model.addAttribute("sightings", matchingSightings);
        model.addAttribute("location", location);
        return "locationDetail";
    }

    @PostMapping("addLocation")
    public String addLocation(@Valid Location toAdd, BindingResult result, Model model) throws InvalidEntityException {

        try {
            locationService.getByLocationName(toAdd.getName());

            FieldError error = new FieldError("location", "name", "Location already exists");
            result.addError(error);
        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        if (result.hasErrors()) {
            List<Location> allLocations = new ArrayList<>();
            try {
                allLocations = locationService.getAllLocations();
            } catch (NoItemsException ex) {
            }
            model.addAttribute("locations", allLocations);
            model.addAttribute("location", toAdd);
            return "locations";
        }
        locationService.addLocation(toAdd);

        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) throws InvalidIdException {

        locationService.deleteLocation(id);

        return "redirect:/locations";
    }

    @GetMapping("display-editLocation")
    public String displayEditLocation(Integer id, Model model) throws InvalidIdException {

        Location toEdit = locationService.getLocationById(id);
        model.addAttribute("location", toEdit);

        return "editLocation";
    }

    @PostMapping("editLocation")
    public String editLocation(@Valid Location toEdit, BindingResult result, Model model) throws InvalidEntityException, InvalidIdException {

        try {
            Location toCheck = locationService.getByLocationName(toEdit.getName());

            if (toEdit.getId() != toCheck.getId()) {
                FieldError error = new FieldError("location", "name", "Location already exists");
                result.addError(error);
            }
        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        if (result.hasErrors()) {
            model.addAttribute("location", toEdit);
            return "editLocation";
        }
        locationService.updateLocation(toEdit);

        return "redirect:/locations";
    }

}
