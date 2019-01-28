package ee.water.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Apartment {

  @Id
  @GeneratedValue
  private int id;
  private String number;
  private String residentFirstName;
  private String residentSurname;
  private boolean managerialRights;
  private String encodedPass;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getResidentFirstName() {
    return residentFirstName;
  }

  public void setResidentFirstName(String residentFirstName) {
    this.residentFirstName = residentFirstName;
  }

  public String getResidentSurname() {
    return residentSurname;
  }

  public void setResidentSurname(String residentSurname) {
    this.residentSurname = residentSurname;
  }

  public boolean hasManagerialRights() {
    return managerialRights;
  }

  public void setManagerialRights(boolean managerialRights) {
    this.managerialRights = managerialRights;
  }

  public String getEncodedPass() {
    return encodedPass;
  }

  public void setEncodedPass(String encodedPass) {
    this.encodedPass = encodedPass;
  }
}
