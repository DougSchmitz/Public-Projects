/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Controllers;

import com.dss.supers.Services.HeroServiceImpl;
import com.dss.supers.Services.OrganizationServiceImpl;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Organization;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.InvalidNameException;
import com.dss.supers.exceptions.NoItemsException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class OrganizationController {

    @Autowired
    OrganizationServiceImpl orgService;

    @Autowired
    HeroServiceImpl heroService;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {

        List<Organization> allOrgs = new ArrayList<>();
        Organization organization = new Organization();
        organization.setHeroes(new ArrayList<>());

        try {
            allOrgs = orgService.getAllOrgs();
        } catch (NoItemsException ex) {
        }

        List<Hero> allHeroes = new ArrayList<>();
        try {
            allHeroes = heroService.getAllHeroes();
        } catch (NoItemsException ex) {
        }
        model.addAttribute("heroes", allHeroes);
        model.addAttribute("organizations", allOrgs);
        model.addAttribute("organization", organization);

        return "organizations";
    }

    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) throws InvalidIdException {
        Organization organization = orgService.getOrgById(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }

    @PostMapping("addOrganization")
    public String addOrganization(@Valid Organization toAdd, BindingResult result, HttpServletRequest request, Model model) throws InvalidIdException, InvalidEntityException {

        try {
            orgService.getByOrgName(toAdd.getName());

            FieldError error = new FieldError("organization", "name", "Organization already exists");
            result.addError(error);
        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        String[] heroIds = request.getParameterValues("heroId");
        List<Hero> heroes = new ArrayList<>();

        if (heroIds == null) {
            FieldError error = new FieldError("organization", "heroes", "Must include one hero");
            result.addError(error);
        } else {
            for (String heroId : heroIds) {
                heroes.add(heroService.getHeroById(Integer.parseInt(heroId)));
            }
        }
        toAdd.setHeroes(heroes);

        if (result.hasErrors()) {

            List<Organization> allOrgs = new ArrayList<>();
            List<Hero> allHeroes = new ArrayList<>();

            try {
                allOrgs = orgService.getAllOrgs();
            } catch (NoItemsException ex) {
            }

            try {
                allHeroes = heroService.getAllHeroes();
            } catch (NoItemsException ex) {
            }
            model.addAttribute("heroes", allHeroes);
            model.addAttribute("organizations", allOrgs);
            model.addAttribute("organization", toAdd);
            return "organizations";
        }
        orgService.addOrganization(toAdd);

        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) throws InvalidIdException {

        orgService.deleteOrganization(id);

        return "redirect:/organizations";
    }

    @GetMapping("display-editOrganization")
    public String displayEditOrganization(Integer id, Model model) throws InvalidIdException {

        Organization toEdit = orgService.getOrgById(id);
        List<Hero> heroes = new ArrayList<>();
        try {
            heroes = heroService.getAllHeroes();
            heroes.forEach(h -> h.setAffiliatedOrganizaitons(null));
        } catch (NoItemsException ex) {
        }

        model.addAttribute("organization", toEdit);
        model.addAttribute("heroes", heroes);

        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String editOrganization(@Valid Organization toEdit, BindingResult result, HttpServletRequest request, Model model) throws InvalidEntityException, InvalidIdException {

        try {
            Organization toCheck = orgService.getByOrgName(toEdit.getName());

            if (toEdit.getId() != toCheck.getId()) {
                FieldError error = new FieldError("organization", "name", "Organization already exists");
                result.addError(error);
            }

        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        String[] heroIds = request.getParameterValues("heroId");
        List<Hero> heroes = new ArrayList<>();

        if (heroIds == null) {
            FieldError error = new FieldError("organization", "heroes", "Must include one hero");
            result.addError(error);
        } else {
            for (String heroId : heroIds) {
                heroes.add(heroService.getHeroById(Integer.parseInt(heroId)));
            }
        }
        toEdit.setHeroes(heroes);

        if (result.hasErrors()) {

            List<Hero> allHeroes = new ArrayList<>();
            try {
                allHeroes = heroService.getAllHeroes();
            } catch (NoItemsException ex) {
            }

            model.addAttribute("heroes", allHeroes);
            model.addAttribute("organization", toEdit);

            return "editOrganization";
        }
        orgService.updateOrganization(toEdit);

        return "redirect:/organizations";
    }

}
