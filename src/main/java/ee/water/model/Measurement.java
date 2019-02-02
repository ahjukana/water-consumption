package ee.water.model;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.water.helper.CalendarTimeFormat;

@Entity
public class Measurement {

  @Id
  @GeneratedValue
  private long id;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "APARTMENT_ID", nullable = false)
  private Apartment apartment;

  private Double coldKitchen;
  private Double hotKitchen;
  private Double coldBathroom;
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

  public Double getColdKitchen() {
    return coldKitchen;
  }

  public void setColdKitchen(Double coldKitchen) {
    this.coldKitchen = coldKitchen;
  }

  public Double getHotKitchen() {
    return hotKitchen;
  }

  public void setHotKitchen(Double hotKitchen) {
    this.hotKitchen = hotKitchen;
  }

  public Double getColdBathroom() {
    return coldBathroom;
  }

  public void setColdBathroom(Double coldBathroom) {
    this.coldBathroom = coldBathroom;
  }

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
}
