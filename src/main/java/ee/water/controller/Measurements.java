package ee.water.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ee.water.model.Apartment;
import ee.water.service.ApartmentService;

@Controller
@RequestMapping("")
public class Measurements {

  @Autowired
  ApartmentService apartmentService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String openMonthlyMeasurement(Model model) throws Exception {
    model.addAttribute("apartment", getLoggedInApartment());
    return "monthlyMeasurement";
  }

  @RequestMapping(value = "/summary", method = RequestMethod.GET)
  public String openSummary(Model model) {
    model.addAttribute("apartments", apartmentService.getApartments());
    return "summary";
  }

  private Apartment getLoggedInApartment() throws Exception {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      String apartmentNumber = ((UserDetails) principal).getUsername();
      return apartmentService.getApartment(apartmentNumber);
    }
    throw new Exception("Logged in apartment not found");
  }

}
