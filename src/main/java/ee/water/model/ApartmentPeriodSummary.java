package ee.water.model;

public class ApartmentPeriodSummary {

  private String apartmentNumber;
  private boolean measurementsMissing;
  private Measurement measurement;

  public String getApartmentNumber() {
    return apartmentNumber;
  }

  public void setApartmentNumber(String apartmentNumber) {
    this.apartmentNumber = apartmentNumber;
  }

  public boolean isMeasurementsMissing() {
    return measurementsMissing;
  }

  public void setMeasurementsMissing(boolean measurementsMissing) {
    this.measurementsMissing = measurementsMissing;
  }

  public Measurement getMeasurement() {
    return measurement;
  }

  public void setMeasurement(Measurement measurement) {
    this.measurement = measurement;
  }
}
