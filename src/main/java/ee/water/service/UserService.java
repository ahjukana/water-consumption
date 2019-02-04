package ee.water.service;

import ee.water.model.Apartment;

public interface UserService {

  Apartment getLoggedInApartment() throws Exception;

  boolean validateOldPassword(String oldPass) throws Exception;

  void changePassword(String newPass) throws Exception;
}
