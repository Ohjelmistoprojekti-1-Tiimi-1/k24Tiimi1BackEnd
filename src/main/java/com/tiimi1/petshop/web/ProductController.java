package com.tiimi1.petshop.web;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.tiimi1.petshop.model.ManufacturerRepository;
import com.tiimi1.petshop.model.Product;
import com.tiimi1.petshop.model.ProductRepository;
import com.tiimi1.petshop.model.ProductTypeRepository;

import jakarta.validation.Valid;

@Controller
public class ProductController {
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ProductTypeRepository productTypeRepository;
    // Modify this list to change available product sizes.
    private final List<String> sizes = Arrays.asList("S", "M", "L");

    public ProductController(ProductRepository productRepository, ManufacturerRepository manufacturerRepository,
            ProductTypeRepository productTypeRepository) {
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.productTypeRepository = productTypeRepository;
    }

    @GetMapping("/admin/products")
    public String showProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("/admin/deleteconfirmation/{id}")
    public String confirmDelete(@PathVariable("id") Long productId, Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("confirmId", productId);
        return "products";
    }

    @GetMapping("/admin/addproduct")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        model.addAttribute("sizes", sizes);
        model.addAttribute("productTypes", productTypeRepository.findAll());
        return "addproduct";
    }

    @SuppressWarnings("null")
    @PostMapping("/admin/addproduct")
    public String checkManufacturerNewProcuctForm(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Objects.requireNonNull(bindingResult.getFieldError());
            if ((bindingResult.getFieldError().getDefaultMessage().equals("message"))) {
                model.addAttribute("priceErrorMessage", "A price is needed");
            } else {
                model.addAttribute("priceErrorMessage", "Price must be a number");
            }
            model.addAttribute("manufacturers", manufacturerRepository.findAll());
            model.addAttribute("productTypes", productTypeRepository.findAll());
            model.addAttribute("sizes", sizes);
            return "addproduct";
        }
        Objects.requireNonNull(product);
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/deleteproduct/{id}")
    public String deleteProduct(@PathVariable("id") Long productId) {
        Objects.requireNonNull(productId);
        productRepository.deleteById(productId);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/editproduct/{id}")
    public String editProdcut(@PathVariable("id") Long productId, Model model) {
        Objects.requireNonNull(productId);
        model.addAttribute("product", productRepository.findById(productId).get());
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        model.addAttribute("productTypes", productTypeRepository.findAll());
        model.addAttribute("sizes", sizes);
        return "editproduct";
    }

    @SuppressWarnings("null")
    @PostMapping("/admin/editproduct/{id}")
    public String editProductSave(@PathVariable("id") Long productId, @Valid Product product,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Objects.requireNonNull(bindingResult.getFieldError());
            if ((bindingResult.getFieldError().getDefaultMessage().equals("message"))) {
                model.addAttribute("priceErrorMessage", "A price is needed");
            } else {
                model.addAttribute("priceErrorMessage", "Price must be a number");
            }
            model.addAttribute("manufacturers", manufacturerRepository.findAll());
            model.addAttribute("productTypes", productTypeRepository.findAll());
            model.addAttribute("sizes", sizes);
            return "editproduct";
        }
        Objects.requireNonNull(product);
        productRepository.save(product);
        return "redirect:/admin/products";
    }

}
