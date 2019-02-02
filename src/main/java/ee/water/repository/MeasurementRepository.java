package ee.water.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ee.water.model.Measurement;

public interface MeasurementRepository extends CrudRepository<Measurement, Long> {

  List<Measurement> findByApartmentNumber(String apartmentNumber);

}
