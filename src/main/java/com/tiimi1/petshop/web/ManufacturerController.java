package com.tiimi1.petshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.tiimi1.petshop.model.Manufacturer;
import com.tiimi1.petshop.model.ManufacturerRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class ManufacturerController {

    @Autowired
    private ManufacturerRepository manuRepo;

    @GetMapping("/manufacturers")
    public String showManufacturers(Model model) {
        model.addAttribute("manufacturers", manuRepo.findAll());
        return "manufacturers";
    }

    @GetMapping("/addmanufacturer")
    public String addManufacturer(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        return "addmanufacturer";
    }

    @PostMapping("/savemanufacturer")
    public String save(Manufacturer manufacturer) {
        manuRepo.save(manufacturer);
        return "redirect:manufacturers";
    }

}
