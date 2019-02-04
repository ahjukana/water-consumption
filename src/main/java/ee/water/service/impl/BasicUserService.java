package ee.water.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ee.water.model.Apartment;
import ee.water.service.ApartmentService;
import ee.water.service.UserService;

@Service(value = "userService")
public class BasicUserService implements UserService {

  @Autowired
  ApartmentService apartmentService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public Apartment getLoggedInApartment() throws Exception {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      String apartmentNumber = ((UserDetails) principal).getUsername();
      return apartmentService.getApartment(apartmentNumber);
    }
    throw new Exception("Logged in apartment not found");
  }

  @Override
  public boolean validateOldPassword(String oldPass) throws Exception {
    return passwordEncoder.matches(oldPass, getLoggedInApartment().getEncodedPass());
  }

  @Override
  public void changePassword(String newPass) throws Exception {
    Apartment loggedInApartment = getLoggedInApartment();
    loggedInApartment.setEncodedPass(passwordEncoder.encode(newPass));
    apartmentService.saveApartment(loggedInApartment);
  }
}
