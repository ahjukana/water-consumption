package ee.water.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Measurement {

  @Id
  @GeneratedValue
  private long id;
  private Double coldKitchen;
  private Double hotKitchen;
  private Double coldBathroom;
  private Double hotBathroom;
  @Temporal(TemporalType.DATE)
  private Calendar calendarDate;
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

  public Calendar getCalendarDate() {
    return calendarDate;
  }

  public void setCalendarDate(Calendar calendarDate) {
    this.calendarDate = calendarDate;
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
