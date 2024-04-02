package com.tiimi1.petshop.model;

import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.tiimi1.petshop.model.ManufacturerRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManufacturerController {

    @Autowired
    private ProductRepository prodRepo;
    @Autowired
    private ManufacturerRepository manuRepo;

    @RequestMapping(value = { "/manufacturers" }, method = RequestMethod.GET)
    public String showManufacturers(Model model) {
        model.addAttribute("manufacturers", manuRepo.findAll());
        return "manufacturerlist";
    }

}
