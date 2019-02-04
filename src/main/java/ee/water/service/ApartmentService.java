package ee.water.service;

import java.util.List;

import ee.water.model.Apartment;

public interface ApartmentService {

  List<Apartment> getApartments();

  Apartment getApartment(String apartmentNumber);

  void saveApartment(Apartment apartment);

}
