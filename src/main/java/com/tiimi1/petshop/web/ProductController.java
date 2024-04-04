package com.tiimi1.petshop.web;

import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.tiimi1.petshop.model.ManufacturerRepository;
import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ProductRepository;


@Controller
public class ProductController {
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ProductController(ProductRepository productRepository, ManufacturerRepository manufacturerRepository) {
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping({ "/", "/products" })
    public String showProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("/addproduct")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        return "addproduct";
    }

    @PostMapping("/saveproduct")
    public String save(Product product) {
        productRepository.save(product);
        return "redirect:products";
    }

        @GetMapping("/deleteproduct/{id}")
    public String deleteProduct(@PathVariable("id") Long productId) {
        Objects.requireNonNull(productId);
        productRepository.deleteById(productId);
        return "redirect:/products";
    }

}
