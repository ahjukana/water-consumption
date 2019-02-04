package ee.water.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.water.model.Apartment;
import ee.water.repository.ApartmentRepository;
import ee.water.service.ApartmentService;

@Service(value = "apartmentService")
public class BasicApartmentService implements ApartmentService {

  @Autowired
  ApartmentRepository apartmentRepository;

  @Override
  public List<Apartment> getApartments() {
    List<Apartment> apartments = new ArrayList<>();
    apartmentRepository.findAll().forEach(e -> apartments.add(e));
    return apartments;
  }

  @Override
  public Apartment getApartment(String apartmentNumber) {
    return apartmentRepository.findByNumber(apartmentNumber);
  }

  @Override
  public void saveApartment(Apartment apartment) {
    apartmentRepository.save(apartment);
  }

}
