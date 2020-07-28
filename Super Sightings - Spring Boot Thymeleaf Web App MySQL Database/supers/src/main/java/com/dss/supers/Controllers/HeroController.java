/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Controllers;

import com.dss.supers.Services.HeroServiceImpl;
import com.dss.supers.Services.OrganizationServiceImpl;
import com.dss.supers.Services.PowerServiceImpl;
import com.dss.supers.Services.SightingServiceImpl;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Organization;
import com.dss.supers.entities.Power;
import com.dss.supers.entities.Sighting;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.InvalidNameException;
import com.dss.supers.exceptions.NoItemsException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
public class HeroController {

    @Autowired
    HeroServiceImpl heroService;

    @Autowired
    PowerServiceImpl powerService;

    @Autowired
    OrganizationServiceImpl orgService;
    
    @Autowired
    SightingServiceImpl sightingService;

    @GetMapping("heroes")
    public String displayHeroes(Model model) {

        List<Hero> allHeroes = new ArrayList<>();
        Hero hero = new Hero();
        hero.setSuperpower(new Power());
        hero.setAffiliatedOrganizaitons(new ArrayList<>());

        try {
            allHeroes = heroService.getAllHeroes();
        } catch (NoItemsException ex) {
        }

        List<Power> allPowers = new ArrayList();
        try {
            allPowers = powerService.getAllPowers();
        } catch (NoItemsException ex) {
        }

        List<Organization> allOrgs = new ArrayList();
        try {
            allOrgs = orgService.getAllOrgs();
        } catch (NoItemsException ex) {
        }

        model.addAttribute("heroes", allHeroes);
        model.addAttribute("hero", hero);
        model.addAttribute("powers", allPowers);
        model.addAttribute("organizations", allOrgs);

        return "heroes";
    }

    @GetMapping("heroDetail")
    public String heroDetail(Integer id, Model model) throws InvalidIdException {
        List<Sighting> matchingSightings = new ArrayList<>();
        Hero hero = heroService.getHeroById(id);
        
        try {
            matchingSightings = sightingService.getAllSightings();
            matchingSightings = matchingSightings.stream().filter(s -> s.getHero().getId() == id).collect(Collectors.toList());
        } catch (NoItemsException ex) {
        }

        model.addAttribute("sightings", matchingSightings);
        model.addAttribute("hero", hero);
        return "heroDetail";
    }

    @PostMapping("addHero")
    public String addHero(@Valid Hero toAdd, BindingResult result, HttpServletRequest request, Model model) throws InvalidIdException, InvalidEntityException {

         try {
            heroService.getByHeroName(toAdd.getName());

            FieldError error = new FieldError("hero", "name", "Hero already exists");
            result.addError(error);
        } catch (InvalidNameException | InvalidEntityException ex) {
        }
        
        String powerId = request.getParameter("powerId");
        String[] orgIds = request.getParameterValues("orgId");
        List<Organization> allOrgs = new ArrayList<>();

        if (orgIds == null) {
            toAdd.setAffiliatedOrganizaitons(new ArrayList<>());
            FieldError error = new FieldError("hero", "affiliatedOrganizaitons", "Must include at least one organization");
            result.addError(error);
        } else {
            for (String orgId : orgIds) {
                allOrgs.add(orgService.getOrgById(Integer.parseInt(orgId)));
            }
            toAdd.setAffiliatedOrganizaitons(allOrgs);
        }

        //Step 1: - check if powerId is null
        if (powerId == null) {
            //sub step 1 - set the power to the hero
            toAdd.setSuperpower(new Power());
            //sub step 2 - add a field error
            FieldError error = new FieldError("hero", "superpower", "Hero must have one power");
            result.addError(error);

            //Step 2: If not null
        } else {
            //sub step 1 -setting the power to the hero
            toAdd.setSuperpower(powerService.getPowerById(Integer.parseInt(powerId)));
        }

        if (result.hasErrors()) {

            List<Hero> allHeroes = new ArrayList<>();
            List<Power> allPowers = new ArrayList();
            List<Organization> orgs = new ArrayList();

            try {
                allHeroes = heroService.getAllHeroes();
            } catch (NoItemsException ex) {
            }

            try {
                allPowers = powerService.getAllPowers();
            } catch (NoItemsException ex) {
            }

            try {
                orgs = orgService.getAllOrgs();
            } catch (NoItemsException ex) {
            }

            model.addAttribute("heroes", allHeroes);
            model.addAttribute("hero", toAdd);
            model.addAttribute("powers", allPowers);
            model.addAttribute("organizations", orgs);

            return "heroes";
        }
        heroService.addHero(toAdd);

        return "redirect:/heroes";
    }

    @GetMapping("deleteHero")
    public String deleteHero(Integer id) throws InvalidIdException {

        heroService.deleteHero(id);

        return "redirect:/heroes";
    }

    @GetMapping("display-editHero")
    public String displayEditHero(Integer id, Model model) throws InvalidIdException {

        Hero toEdit = heroService.getHeroById(id);
        List<Organization> orgs = new ArrayList<>();
        List<Power> powers = new ArrayList<>();

        try {
            powers = powerService.getAllPowers();
        } catch (NoItemsException ex) {
        }

        try {
            orgs = orgService.getAllOrgs();
            orgs.forEach(o -> o.setHeroes(null));
        } catch (NoItemsException ex) {
        }

        model.addAttribute("hero", toEdit);
        model.addAttribute("organizations", orgs);
        model.addAttribute("powers", powers);

        return "editHero";
    }

    @PostMapping("editHero")
    public String editHero(@Valid Hero toEdit, BindingResult result, HttpServletRequest request, Model model) throws InvalidIdException, InvalidEntityException {

        String powerId = request.getParameter("powerId");
        String[] orgIds = request.getParameterValues("orgId");
        List<Organization> allOrgs = new ArrayList<>();
        Power power = new Power();
        
        try {
            Hero toCheck = heroService.getByHeroName(toEdit.getName());

            if (toEdit.getId() != toCheck.getId()) {
                FieldError error = new FieldError("hero", "name", "Hero already exists");
                result.addError(error);
            }

        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        if (orgIds == null) {
            FieldError error = new FieldError("hero", "affiliatedOrganizaitons", "Must include at least one organization");
            result.addError(error);
        } else {
            for (String orgId : orgIds) {
                allOrgs.add(orgService.getOrgById(Integer.parseInt(orgId)));
            }
        }
        toEdit.setAffiliatedOrganizaitons(allOrgs);

        //Step 1: - check if powerId is null
        if (powerId == null) {
            //sub step 1 - set the power to the hero
            //sub step 2 - add a field error
            //thymeleaf attribute in html is hero, "power" is hero.power
            FieldError error = new FieldError("hero", "power", "Hero must have one power");
            result.addError(error);

            //Step 2: If not null
        } else {
            //sub step 1 -setting the power to the hero
            power = powerService.getPowerById(Integer.parseInt(powerId));
        }
        toEdit.setSuperpower(power);

        if (result.hasErrors()) {

            List<Power> allPowers = new ArrayList();
            List<Organization> orgs = new ArrayList();

            try {
                allPowers = powerService.getAllPowers();
            } catch (NoItemsException ex) {
            }

            try {
                orgs = orgService.getAllOrgs();
            } catch (NoItemsException ex) {
            }

            model.addAttribute("hero", toEdit);
            model.addAttribute("powers", allPowers);
            model.addAttribute("organizations", orgs);

            return "editHero";
        }

        heroService.updateHero(toEdit);

        return "redirect:/heroes";
    }

}
