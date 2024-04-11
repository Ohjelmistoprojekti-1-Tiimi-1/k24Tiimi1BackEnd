package com.tiimi1.petshop.web;

import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tiimi1.petshop.model.Manufacturer;
import com.tiimi1.petshop.model.ManufacturerRepository;


@Controller
public class ManufacturerController {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerController(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping("/manufacturers")
    public String showManufacturers(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("deleteError", error);
        }else {
            model.addAttribute("deleteError", null);
        }

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

     @GetMapping("/deletemanufacturer/{id}")
    public String deleteManufacturer(@PathVariable("id") Long manufacturerId, RedirectAttributes redirectAttributes) {
        Objects.requireNonNull(manufacturerId);
        try {
            manufacturerRepository.deleteById(manufacturerId);
        }catch (Exception error) {
            redirectAttributes.addAttribute("error", "You can't remove a manufacturer that has products assigned");
        }
        return "redirect:/manufacturers";
    }

}
