package ee.water.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.water.helper.CalendarTimeFormat;
import ee.water.validation.MeasurementValuesValidatable;
import ee.water.validation.annotation.CompareWithPreviousMeasurement;

@Entity
@CompareWithPreviousMeasurement
public class Measurement implements MeasurementValuesValidatable {

  @Id
  @GeneratedValue
  private long id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "APARTMENT_ID", nullable = false)
  private Apartment apartment;

  @NotNull(message = "fieldError.required")
  private Double coldKitchen;

  @NotNull(message = "fieldError.required")
  private Double hotKitchen;

  @NotNull(message = "fieldError.required")
  private Double coldBathroom;

  @NotNull(message = "fieldError.required")
  private Double hotBathroom;

  @Temporal(TemporalType.DATE)
  private Calendar date;

  @Transient
  private int year;

  @Transient
  private int month;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Apartment getApartment() {
    return apartment;
  }

  public void setApartment(Apartment apartment) {
    this.apartment = apartment;
  }

  @Override
  public Double getColdKitchen() {
    return coldKitchen;
  }

  public void setColdKitchen(Double coldKitchen) {
    this.coldKitchen = coldKitchen;
  }

  @Override
  public Double getHotKitchen() {
    return hotKitchen;
  }

  public void setHotKitchen(Double hotKitchen) {
    this.hotKitchen = hotKitchen;
  }

  @Override
  public Double getColdBathroom() {
    return coldBathroom;
  }

  public void setColdBathroom(Double coldBathroom) {
    this.coldBathroom = coldBathroom;
  }

  @Override
  public Double getHotBathroom() {
    return hotBathroom;
  }

  public void setHotBathroom(Double hotBathroom) {
    this.hotBathroom = hotBathroom;
  }

  public Calendar getDate() {
    return date;
  }

  public void calculateCalendarTime() {
    this.date = new CalendarTimeFormat().parseToCalendar(year, month);
  }

  public String getDateStringForJS() {
    CalendarTimeFormat ctf = new CalendarTimeFormat();
    return ctf.getYear(date) + "," + (ctf.getMonth(date) - 1) + ",1";
  }

  public void setDate(Calendar date) {
    this.date = date;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public Double getHotWater() {
    return round(Double.sum(hotBathroom, hotKitchen));
  }

  public Double getColdWater() {
    return round(Double.sum(coldBathroom, coldKitchen));
  }

  public Double getTotalWater() {
    double hot = Double.sum(hotBathroom, hotKitchen);
    double cold = Double.sum(coldBathroom, coldKitchen);
    return round(Double.sum(hot, cold));
  }

  private double round(double value) {
    BigDecimal bd = new BigDecimal(Double.toString(value));
    bd = bd.setScale(3, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
}
