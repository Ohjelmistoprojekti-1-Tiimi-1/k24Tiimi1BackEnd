package com.tiimi1.petshop.web;

import java.util.Objects;
import java.util.Optional;

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
import com.tiimi1.petshop.model.Product;

import jakarta.validation.Valid;


@Controller
public class ManufacturerController {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerController(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping("/admin/manufacturers")
    public String showManufacturers(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("deleteError", error);
        }else {
            model.addAttribute("deleteError", null);
        }

        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        return "manufacturers";
    }

    @GetMapping("/admin/addmanufacturer")
    public String addManufacturer(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        return "addmanufacturer";
    }

    @PostMapping("/admin/savemanufacturer")
    public String save(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
        return "redirect:/admin/manufacturers";
    }

     @GetMapping("/admin/deletemanufacturer/{id}")
    public String deleteManufacturer(@PathVariable("id") Long manufacturerId, RedirectAttributes redirectAttributes) {
        Objects.requireNonNull(manufacturerId);
        try {
            manufacturerRepository.deleteById(manufacturerId);
        }catch (Exception error) {
            redirectAttributes.addAttribute("error", "You can't remove a manufacturer that has products assigned");
        }
        return "redirect:/admin/manufacturers";
    }

    @GetMapping("/admin/editmanufacturer/{id}")
    public String editManufacturer(@PathVariable("id") Long manufacturerId, Model model) {
        Objects.requireNonNull(manufacturerId);
        model.addAttribute("manufacturer", manufacturerRepository.findById(manufacturerId).get());
        return "editmanufacturer";
    }

    @SuppressWarnings("null")
    @PostMapping("/admin/editmanufacturer/{id}")
    public String editManufacturerSave(@PathVariable("id") Long manufacturerId, Manufacturer manufacturer, Model model) {
        
        manufacturerRepository.save(manufacturer);
        return "redirect:/admin/manufacturers";
    }

    @GetMapping("/admin/productsbymanufacturer/{id}")
    public String getAllProductByManufacturer(@PathVariable("id") Long manufacturerId, Model model) {
        Objects.requireNonNull(manufacturerId);
        Optional<Manufacturer> manufacturerOpt = manufacturerRepository.findById(manufacturerId);
        model.addAttribute("products", manufacturerOpt.get().getProducts());
        model.addAttribute("manufacturer", manufacturerRepository.findById(manufacturerId).get());
        return "productsbymanufacturer";
    }



}
