package lv.venta.controller;

import lv.venta.service.ICustomerService;
import lv.venta.service.IDriverCRUDService;
import lv.venta.service.IParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.time.LocalDateTime;

@Controller
public class MainController {

    @Autowired
    private IDriverCRUDService driverCRUDService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IParcelService parcelService;

    @GetMapping("/")
    public String getPage(Model model) {
        try {
            model.addAttribute("title", "Hello to EOmniva Page! ");
            model.addAttribute("msg", LocalDateTime.now().toString());
            return "msg-page";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("title", "Error Page");
            return "error-page";
        }
    }

}