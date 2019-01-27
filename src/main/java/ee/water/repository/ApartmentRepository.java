package ee.water.repository;

import org.springframework.data.repository.CrudRepository;

import ee.water.model.Apartment;

public interface ApartmentRepository extends CrudRepository<Apartment, Integer> {
}
