package ee.water.service;

import java.util.Calendar;
import java.util.List;

import ee.water.model.Measurement;

public interface MeasurementService {

  Measurement getLastApartmentMeasurement(String apartmentNumber);

  Measurement getMeasurement(String apartmentNumber, Calendar date);

  List<Measurement> getMeasurementsForDate(Calendar date);

  void saveMeasurement(Measurement measurement);

}
