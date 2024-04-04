package com.tiimi1.petshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.tiimi1.petshop.model.Manufacturer;
import com.tiimi1.petshop.model.ManufacturerRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ManufacturerController {

    @Autowired
    private ManufacturerRepository manuRepo;

    @RequestMapping(value = { "/manufacturers" }, method = RequestMethod.GET)
    public String showManufacturers(Model model) {
        model.addAttribute("manufacturers", manuRepo.findAll());
        return "manufacturers";
    }

    @RequestMapping("/addmanufacturer")
    public String addManufacturer(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        return "addmanufacturer";
    }

    @RequestMapping(value = "/savemanufacturer", method = RequestMethod.POST)
    public String save(Manufacturer manufacturer) {
        manuRepo.save(manufacturer);
        return "redirect:manufacturers";
    }

}
