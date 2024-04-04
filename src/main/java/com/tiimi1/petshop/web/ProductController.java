package com.tiimi1.petshop.web;

import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.tiimi1.petshop.model.ManufacturerRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ProductController {

    @Autowired
    private ProductRepository prodRepo;
    @Autowired
    private ManufacturerRepository manuRepo;

    @RequestMapping(value = { "/", "/products" }, method = RequestMethod.GET)
    public String showProducts(Model model) {
        model.addAttribute("products", prodRepo.findAll());
        return "products";
    }

    @RequestMapping("/addproduct")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("manufacturers", manuRepo.findAll());
        return "addproduct";
    }

    @RequestMapping(value = "/saveproduct", method = RequestMethod.POST)
    public String save(Product product) {
        prodRepo.save(product);
        return "redirect:products";
    }



}
