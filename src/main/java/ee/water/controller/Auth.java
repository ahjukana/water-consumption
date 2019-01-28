package ee.water.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ee.water.service.ApartmentService;

@Controller
public class Auth {

  @Autowired
  ApartmentService apartmentService;

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(Model model, String error, String logout) {
    String message = null;
    if (error != null) {
      message = "login.error";
    } else if (logout != null) {
      message = "login.logout";
    }
    model.addAttribute("message", message);
    model.addAttribute("apartments", apartmentService.getApartments());
    return "login";
  }

}
