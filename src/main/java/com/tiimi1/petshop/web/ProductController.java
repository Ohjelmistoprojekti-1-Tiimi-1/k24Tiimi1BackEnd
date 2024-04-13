package com.tiimi1.petshop.web;

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
import com.tiimi1.petshop.model.SizeRepository;

import jakarta.validation.Valid;

@Controller
public class ProductController {
    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ProductTypeRepository productTypeRepository;
    private final SizeRepository sizeRepository;

    public ProductController(ProductRepository productRepository, ManufacturerRepository manufacturerRepository,
            ProductTypeRepository productTypeRepository, SizeRepository sizeRepository) {
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.productTypeRepository = productTypeRepository;
        this.sizeRepository = sizeRepository;
    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @GetMapping("/addproduct")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        model.addAttribute("sizes", sizeRepository.findAll());
        model.addAttribute("productTypes", productTypeRepository.findAll());
        return "addproduct";
    }

    @SuppressWarnings("null")
    @PostMapping("/addproduct")
    public String checkManufacturerNewProcuctForm(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Objects.requireNonNull(bindingResult.getFieldError());
            if ((bindingResult.getFieldError().getDefaultMessage().equals("message"))) {
                model.addAttribute("priceErrorMessage", "A price is needed");
            } else {
                model.addAttribute("priceErrorMessage", "Price must be a number");
            }
            model.addAttribute("manufacturers", manufacturerRepository.findAll());
            model.addAttribute("sizes", sizeRepository.findAll());
            model.addAttribute("productTypes", productTypeRepository.findAll());
            return "addproduct";
        }
        Objects.requireNonNull(product);
        productRepository.save(product);
        return "redirect:/products";
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

    @GetMapping("/editproduct/{id}")
    public String editProdcut(@PathVariable("id") Long productId, Model model) {
        Objects.requireNonNull(productId);
        model.addAttribute("product", productRepository.findById(productId).get());
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        model.addAttribute("productTypes", productTypeRepository.findAll());
        model.addAttribute("sizes", sizeRepository.findAll());
        return "editproduct";
    }

    @SuppressWarnings("null")
    @PostMapping("/editproduct/{id}")
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
            return "editproduct";
        }
        Objects.requireNonNull(product);
        productRepository.save(product);
        return "redirect:/products";
    }

    // just temporarely here
    @GetMapping({ "/", "/home" })
    public String home() {
        return "home";
    }

}
