package com.tiimi1.petshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tiimi1.petshop.model.Manufacturer;
import com.tiimi1.petshop.model.ManufacturerRepository;


@Controller
public class ManufacturerController {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerController(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping("/manufacturers")
    public String showManufacturers(Model model) {
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        return "manufacturers";
    }

    @GetMapping("/addmanufacturer")
    public String addManufacturer(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        return "addmanufacturer";
    }

    @PostMapping("/savemanufacturer")
    public String save(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
        return "redirect:manufacturers";
    }

}
