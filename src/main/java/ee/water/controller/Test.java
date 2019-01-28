package ee.water.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ee.water.service.ApartmentService;

@Controller
@RequestMapping("")
public class Test {

  @Autowired
  ApartmentService apartmentService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String test(Model model) {
    model.addAttribute("apartments", apartmentService.getApartments());
    return "test";
  }

  @RequestMapping(value = "/summary", method = RequestMethod.GET)
  public String summary(Model model) {
    model.addAttribute("apartments", apartmentService.getApartments());
    return "test";
  }
}
