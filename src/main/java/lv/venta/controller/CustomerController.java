package lv.venta.controller;

import jakarta.validation.Valid;
import lv.venta.model.*;
import lv.venta.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    private ICustomerService customerService;
    @GetMapping("/create/person")
    public String createPerson(Model model) {
        try {
            model.addAttribute("customerAsPerson", new CustomerAsPerson());
            model.addAttribute("title", "Add Customer as person");
            return "insert-person-page";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @PostMapping("/create/person")
    public String postCreatePerson(@Valid CustomerAsPerson customerAsPerson,BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println(customerAsPerson );
            System.out.println(result);
            return "insert-person-page";
        } else {
            try {
                customerService.insertNewCustomerAsPerson(customerAsPerson);
                return "redirect:/parcel/show/all";
            } catch (Exception e) {
                model.addAttribute("msg", e.getMessage());
                model.addAttribute("title", "Error Page");
                return "error-page";
            }

        }
    }
    @GetMapping("/create/company")
    public String createCompany(Model model) {
        try {
            model.addAttribute("customerAsCompany", new CustomerAsCompany());
            model.addAttribute("title", "Add Customer as company");
            return "insert-company-page";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @PostMapping("/create/company")
    public String postCreateCompany(@Valid CustomerAsCompany customerAsCompany,BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println(customerAsCompany);
            System.out.println(result);
            return "insert-company-page";
        } else {
            try {
                customerService.insertNewCustomerAsCompany(customerAsCompany);
                return "redirect:/parcel/show/all";
            } catch (Exception e) {
                model.addAttribute("msg", e.getMessage());
                model.addAttribute("title", "Error Page");
                return "error-page";
            }

        }
    }
    @GetMapping("/add/address")
    public String addAddress(@RequestParam("customerId") long customerId, Model model) {
        try {
            model.addAttribute("address", new Address());
            model.addAttribute("customerId", customerId);
            model.addAttribute("title", "Add Address For customer");
            return "add-address-page";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @PostMapping("/add/address")
    public String postAddAddress(@RequestParam("customerId") long customerId, @Valid Address address,BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println(address);
            System.out.println(result);
            return "add-address-page";
        } else {
            try {
                customerService.addAddressToCustomerByCustomerId(address, customerId);
                return "redirect:/parcel/show/all";
            } catch (Exception e) {
                model.addAttribute("msg", e.getMessage());
                model.addAttribute("title", "Error Page");
                return "error-page";
            }

        }
    }

}