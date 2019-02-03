package ee.water.service;

import java.util.Calendar;

import ee.water.model.Measurement;

public interface MeasurementService {

  Measurement getLastApartmentMeasurement(String apartmentNumber);

  Measurement getMeasurement(String apartmentNumber, Calendar date);

  void saveMeasurement(Measurement measurement);
}
