package ee.water.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.water.model.Measurement;
import ee.water.repository.MeasurementRepository;
import ee.water.service.MeasurementService;

@Service(value = "measurementService")
public class BasicMeasurementService implements MeasurementService {

  @Autowired
  MeasurementRepository measurementRepository;

  @Override
  public Measurement getLastApartmentMeasurement(String apartmentNumber) {
    List<Measurement> measurements = measurementRepository.findByApartmentNumber(apartmentNumber);
    if (measurements.isEmpty()) {
      return null;
    }
    Measurement lastMeasurement = Collections.max(measurements,
        Comparator.comparing(c -> c.getDate()));
    return lastMeasurement;
  }

  @Override
  public Measurement getMeasurement(String apartmentNumber, Calendar date) {
    List<Measurement> measurements = measurementRepository.findByApartmentNumber(apartmentNumber);
    if (measurements.isEmpty()) {
      return null;
    }
    for (Measurement measurement : measurements) {
      if (measurement.getDate().equals(date)) {
        return measurement;
      }
    }
    return null;
  }

  @Override
  public void saveMeasurement(Measurement measurement) {
    measurementRepository.save(measurement);
  }
}
