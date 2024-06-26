package com.tiimi1.petshop.web;

import java.util.Objects;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tiimi1.petshop.model.Customer;
import com.tiimi1.petshop.model.CustomerRepository;

import jakarta.validation.Valid;

@Controller
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/admin/customers")
    public String showCustomers(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "customers";
    }

    @GetMapping("/admin/customerdeleteconfirmation/{id}")
    public String confirmDelete(@PathVariable("id") Long customerId, Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("confirmId", customerId);
        return "customers";
    }

    @GetMapping("/admin/addcustomer")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "addcustomer";
    }

    @PostMapping("/admin/addcustomer")
    public String saveCustomer(@Valid Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/addcustomer";
        } else {
            customer.setPassword(encoder.encode(customer.getPassword()));
            customerRepository.save(customer);
            return "redirect:/admin/customers";
        }
    }

    @GetMapping("/admin/deletecustomer/{id}")
    public String deleteCustomer(@PathVariable("id") Long customerId, RedirectAttributes redirectAttributes) {
        try {
            customerRepository.deleteById(customerId);
        } catch (Exception error) {
            redirectAttributes.addAttribute("error", "Customer cannot be removed.");
        }
        return "redirect:/admin/customers";
    }

    @GetMapping("/admin/editcustomer/{id}")
    public String editCustomer(@PathVariable("id") Long customerId, Model model) {
        model.addAttribute("customer", customerRepository.findById(customerId).get());
        model.addAttribute("customers", customerRepository.findAll());
        return "editcustomer";
    }

    // customerId has to be here for it to work
    @PostMapping("admin/editcustomer/{id}")
    public String editCustomerSave(@PathVariable("id") Long customerId, @Valid Customer customer,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customers", customerRepository.findAll());
            return "/editcustomer";
        } else {
            customerRepository.save(customer);
            return "redirect:/admin/customers";
        }
    }

    // get all reservations by customer
    @GetMapping("/admin/reservationsbycustomer/{id}")
    public String getAllReservationsByCustomer(@PathVariable("id") Long customerId, Model model) {
        Objects.requireNonNull(customerId);
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        model.addAttribute("customer", customerRepository.findById(customerId).get());
        if (customerOptional.get().getReservations().size() == 0) {
            model.addAttribute("noReservations", true);
        } else {
            model.addAttribute("reservations", customerOptional.get().getReservations());
        }
        return "reservationsbycustomer";
    }

}
