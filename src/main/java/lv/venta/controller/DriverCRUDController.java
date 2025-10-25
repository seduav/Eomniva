package lv.venta.controller;

import jakarta.validation.Valid;
import lv.venta.model.Driver;
import lv.venta.service.ICustomerService;
import org.springframework.ui.Model;
import lv.venta.service.IDriverCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/driver")
public class DriverCRUDController {

    @Autowired
    private IDriverCRUDService driverCRUDService;
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/show/all")
    public String showAllDrivers(Model model){
        try {
            model.addAttribute("myobjs", driverCRUDService.selectAllDrivers());
            model.addAttribute("customerId", customerService.retrieveAllIdForCustomers());
            model.addAttribute("title", "All drivers");
            return "show-all-drivers";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @GetMapping("/show")
    public String getDriverById(@RequestParam("id") long id, Model model){
        try {
            model.addAttribute("title", "Driver by ID");
            model.addAttribute("customerId", customerService.retrieveAllIdForCustomers());
            model.addAttribute("myobj", driverCRUDService.selectDriverById(id));
            return "show-single-driver.html";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @GetMapping("/remove/{id}")
    public String removeDriverById(@PathVariable("id") long id, Model model){
        try {
            model.addAttribute("title", "Driver removed");
            model.addAttribute("myobj", driverCRUDService.deleteDriverById(id));
            model.addAttribute("customerId", customerService.retrieveAllIdForCustomers());
            return "show-single-driver.html";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @GetMapping("/add")
    public String getDriverAdd(Model model){
        model.addAttribute("title", "Add driver");
        model.addAttribute("driver", new Driver());
        return "insert-driver-page";
    }
    @PostMapping("/add")
    public String postDriverAdd(@Valid Driver driver, BindingResult result){
        if(result.hasErrors()){
            return "insert-driver-page";
        }else{
            try {
                driverCRUDService.insertNewDriver(driver);
                return "redirect:/driver/show/all";
            } catch (Exception e) {
                return "redirect:/error";
            }
        }
    }
    @GetMapping("/update/{id}")
    public String getDriverUpdate(@PathVariable("id") long id, Model model){
        try {
            model.addAttribute("title", "Update driver");
            model.addAttribute("driver", driverCRUDService.selectDriverById(id));
            return "update-driver-page";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @PostMapping("/update/{id}")
    public String postDriverUpdate(@PathVariable("id") long id, @Valid Driver driver, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("id", id);
            return "update-driver-page";
        }else{
            try {
                driverCRUDService.updateDriverById(id, driver);
                return "redirect:/driver/show/all";
            } catch (Exception e) {
                return "redirect:/error";
            }
        }
    }

}