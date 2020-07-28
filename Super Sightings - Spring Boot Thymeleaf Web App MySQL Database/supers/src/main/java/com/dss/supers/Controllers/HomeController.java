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
import com.dss.supers.exceptions.NoItemsException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Schmi
 */
@Controller
public class HomeController {

    @Autowired
    SightingServiceImpl sightingService;

    @GetMapping({"/", "/home"})
    public String displayHomePage(Model model) {

        List<Sighting> tenSightings = new ArrayList<>();
        Sighting sighting = new Sighting();
        
        try {
            tenSightings = sightingService.getTenSightings();
        } catch (NoItemsException ex) {
        }

        model.addAttribute("sightings", tenSightings);
        model.addAttribute("sighting", sighting);

        return "home";
    }

}