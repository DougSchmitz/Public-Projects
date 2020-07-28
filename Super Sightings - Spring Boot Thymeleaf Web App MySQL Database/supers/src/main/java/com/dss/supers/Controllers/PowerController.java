/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dss.supers.Controllers;

import com.dss.supers.Services.HeroServiceImpl;
import com.dss.supers.Services.PowerServiceImpl;
import com.dss.supers.entities.Hero;
import com.dss.supers.entities.Power;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.dss.supers.exceptions.InvalidEntityException;
import com.dss.supers.exceptions.InvalidIdException;
import com.dss.supers.exceptions.InvalidNameException;
import com.dss.supers.exceptions.NoItemsException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 *
 * @author Schmi
 */
@Controller
public class PowerController {

    @Autowired
    PowerServiceImpl powerService;

    @Autowired
    HeroServiceImpl heroService;

    @GetMapping("powers")
    public String displayPowers(Model model) {

        List<Power> allPowers = new ArrayList<>();
        Power power = new Power();
        try {
            allPowers = powerService.getAllPowers();
        } catch (NoItemsException ex) {
        }
        model.addAttribute("powers", allPowers);
        model.addAttribute("power", power);
        return "powers";
    }

    @GetMapping("powerDetail")
    public String powerDetail(Integer id, Model model) throws InvalidIdException {
        Power power = powerService.getPowerById(id);
        List<Hero> matchingHeroes = new ArrayList<>();

        try {
            matchingHeroes = heroService.getAllHeroes();
            matchingHeroes = matchingHeroes.stream().filter(h -> h.getSuperpower().getId() == id).collect(Collectors.toList());

        } catch (NoItemsException ex) {
        }

        model.addAttribute("heroes", matchingHeroes);
        model.addAttribute("power", power);
        return "powerDetail";
    }

    @PostMapping("addPower")
    public String addPower(@Valid Power toAdd, BindingResult result, Model model) throws InvalidEntityException {

        try {
            powerService.getBySuperPowerName(toAdd.getPower());

            FieldError error = new FieldError("power", "power", "Superpower already exists");
            result.addError(error);
        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        if (result.hasErrors()) {
            List<Power> allPowers = new ArrayList<>();
            try {
                allPowers = powerService.getAllPowers();
            } catch (NoItemsException ex) {
            }
            model.addAttribute("powers", allPowers);
            model.addAttribute("power", toAdd);
            return "powers";
        }

        powerService.addPower(toAdd);

        return "redirect:/powers";
    }

    @GetMapping("deletePower")
    public String deletePower(Integer id) throws InvalidIdException {

        powerService.deletePower(id);

        return "redirect:/powers";
    }

    @GetMapping("display-editPower")
    public String displayEditPower(Integer id, Model model) throws InvalidIdException {

        Power toEdit = powerService.getPowerById(id);
        model.addAttribute("power", toEdit);

        return "editPower";
    }

    @PostMapping("editPower")
    public String editPower(@Valid Power toEdit, BindingResult result, Model model) throws InvalidEntityException, InvalidIdException {

        try {
            Power toCheck = powerService.getBySuperPowerName(toEdit.getPower());

            if (toEdit.getId() != toCheck.getId()) {
                FieldError error = new FieldError("power", "power", "Superpower already exists");
                result.addError(error);
            }
        } catch (InvalidNameException | InvalidEntityException ex) {
        }

        if (result.hasErrors()) {
            model.addAttribute("power", toEdit);
            return "editPower";
        }

        powerService.updatePower(toEdit);

        return "redirect:/powers";
    }

}
