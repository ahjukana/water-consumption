package ee.water.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.water.service.ApartmentService;
import ee.water.service.UserService;

@Controller
public class Auth {

  @Autowired
  ApartmentService apartmentService;

  @Autowired
  private UserService userService;

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

  @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
  public String openChangePassword(Model model) throws Exception {
    model.addAttribute("apartment", userService.getLoggedInApartment());
    return "changePassword";
  }

  @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> changeUserPassword(@RequestParam String newPass,
      @RequestParam String oldPass)
      throws Exception {
    Map<String, Object> map = new HashMap<>();
    if (!userService.validateOldPassword(oldPass)) {
      map.put("hasErrors", true);
    } else {
      userService.changePassword(newPass);
      map.put("hasErrors", false);
    }
    return map;
  }

}
