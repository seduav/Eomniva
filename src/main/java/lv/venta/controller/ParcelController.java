package lv.venta.controller;

import lv.venta.model.City;
import lv.venta.model.Parcel;
import lv.venta.service.ICustomerService;
import lv.venta.service.IDriverCRUDService;
import org.springframework.ui.Model;
import lv.venta.service.IParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/parcel")
public class ParcelController {

    @Autowired
    private IParcelService parcelService;
    @Autowired
    private IDriverCRUDService driverCRUDService;
    @Autowired
    private ICustomerService customerService;
    @GetMapping("/show/all")
    public String showAllParcels(Model model){
        try {
            model.addAttribute("title", "All parcels");
            model.addAttribute("myobjs", parcelService.selectAllParcels());
            model.addAttribute("driver", driverCRUDService.selectAllDrivers());
            model.addAttribute("customer", customerService.retrieveAllCustomerCode());
            model.addAttribute("customerId", customerService.retrieveAllIdForCustomers());
            model.addAttribute("currCity", "Not selected");

            return "show-all-parcels";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @GetMapping("/show/customer")
    public String showAllParcelsByCustomerId(@RequestParam("id") long id, Model model){
        try {
            model.addAttribute("title", "All parcels by customer");
            model.addAttribute("myobjs", parcelService.selectAllParcelsByCustomerId(id));
            model.addAttribute("currCity", "Not selected");
            model.addAttribute("customerId", customerService.retrieveAllIdForCustomers());
            model.addAttribute("driver", driverCRUDService.selectAllDrivers());
            model.addAttribute("customer", customerService.retrieveAllCustomerCode());
            return "show-all-parcels";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @GetMapping("/show/driver")
    public String showAllParcelsByDriverId(@RequestParam("id") long id, Model model){
        try {
            model.addAttribute("title", "All parcels by driver");
            model.addAttribute("myobjs", parcelService.selectAllParcelsDeliveredByDriverId(id));
            model.addAttribute("currCity", "Not selected");
            model.addAttribute("customerId", customerService.retrieveAllIdForCustomers());
            model.addAttribute("driver", driverCRUDService.selectAllDrivers());
            model.addAttribute("customer", customerService.retrieveAllCustomerCode());

            return "show-all-parcels";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @GetMapping("/show/city")
    public String showAllParcelsDeliveredToCity(@RequestParam("city") String city, Model model){
        try {
            model.addAttribute("title", "All parcels delivered to city");
            model.addAttribute("myobjs", parcelService.selectAllParcelsDeliveredToCity(City.valueOf(city)));
            model.addAttribute("currCity", City.valueOf(city));
            model.addAttribute("driver", driverCRUDService.selectAllDrivers());
            model.addAttribute("customerId", customerService.retrieveAllIdForCustomers());
            model.addAttribute("customer", customerService.retrieveAllCustomerCode());

            return "show-all-parcels";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @GetMapping("/show/price")
    public String showAllParcelsByPrice(@RequestParam("price") float price, Model model){
        try {
            model.addAttribute("title", "All parcels cheaper than " + price + "€");
            model.addAttribute("myobjs", parcelService.selectAllParcelsPriceLessThan(price));
            model.addAttribute("driver", driverCRUDService.selectAllDrivers());
            model.addAttribute("customerId", customerService.retrieveAllIdForCustomers());
            model.addAttribute("customer", customerService.retrieveAllCustomerCode());
            model.addAttribute("currCity", "Not selected");

            return "show-all-parcels";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @GetMapping("/add")
    public String addParcel(@RequestParam("customerCode") String customerCode, @RequestParam("driverId") long driverId, Model model) {
        try {
            model.addAttribute("customerCode", customerCode);
            model.addAttribute("driverId", driverId);
            model.addAttribute("parcel", new Parcel());
            model.addAttribute("title", "Add parcel");
            return "insert-parcel-page";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @PostMapping("/add/{customerCode}/{driverId}")
    public String postDriverAdd(@PathVariable("customerCode") String customerCode, @PathVariable("driverId") long driverId, Parcel parcel, BindingResult result, Model model){

            try {
                parcelService.insertNewParcelByCustomerCodeAndDriverId(parcel, customerCode, driverId);
                return "redirect:/parcel/show/all";
            } catch (Exception e) {
                model.addAttribute("msg", e.getMessage());
                model.addAttribute("title", "Error Page");
                return "error-page";
            }

    }
    @GetMapping("/change/{parcelid}")
    public String changeParcelDriver(@PathVariable("parcelid") long parcelId, @RequestParam("driverId") long driverId, Model model){
        try {
            parcelService.changeParcelDriverByParcelIdAndDriverId(parcelId, driverId);
            return "redirect:/parcel/show/all";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @GetMapping("/calculate/income")
    public String calculateIncome(@RequestParam("customerId") long customerId, Model model){
        try {
            model.addAttribute("title", "Calculated income by customer with id:" + customerId);
            model.addAttribute("customerId", customerId);
            model.addAttribute("msg", parcelService.calculateIncomeOfParcelsByCustomerId(customerId));
            return "msg-page";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    @GetMapping("/calculate/count/today")
    public String calculateCountToday(Model model){
        try {
            model.addAttribute("title", "Parcels That need to be delivered today: ");
            model.addAttribute("msg", parcelService.calculateHowManyParcelsNeedToDeliverToday());
            return "msg-page";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }
    
}