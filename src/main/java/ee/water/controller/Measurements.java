package ee.water.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.water.helper.CalendarTimeFormat;
import ee.water.model.Apartment;
import ee.water.model.Measurement;
import ee.water.service.ApartmentService;
import ee.water.service.MeasurementService;

@Controller
@RequestMapping("")
public class Measurements {

  private static final int YEARS_FROM = 2018;

  @Autowired
  ApartmentService apartmentService;

  @Autowired
  MeasurementService measurementService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String openMonthlyMeasurement(Model model) throws Exception {
    Apartment loggedInApartment = getLoggedInApartment();
    Measurement lastMeasurement = measurementService.getLastApartmentMeasurement(
        loggedInApartment.getNumber());
    boolean currentMonthInserted = isCurrentMonthMeasurementAdded(lastMeasurement);

    model.addAttribute("currentMonthInserted", currentMonthInserted);
    model.addAttribute("lastMeasurement", lastMeasurement);
    model.addAttribute("apartment", loggedInApartment);
    model.addAttribute("newMeasurement", new Measurement());
    model.addAttribute("dropdownYears", getActiveYears());
    return "monthlyMeasurement";
  }

  @RequestMapping(value = "/getMeasurement", method = RequestMethod.GET)
  @ResponseBody
  public Measurement getMeasurement(@RequestParam int selectedYear,
      @RequestParam int selectedMonth) throws Exception {
    return measurementService.getMeasurement(getLoggedInApartment().getNumber(),
        new CalendarTimeFormat().parseToCalendar(selectedYear, selectedMonth));
  }

  @RequestMapping(value = "/saveMeasurement", method = RequestMethod.POST)
  public String saveMonthlyMeasurement(
      @ModelAttribute("newMeasurement") @Valid Measurement newMeasurement,
      BindingResult bindingResult) throws Exception {
    // TODO bindingResult errors
    // TODO kontrollida, et kuupäev pole liiga uus ega vana
    newMeasurement.calculateCalendarTime();
    newMeasurement.setApartment(getLoggedInApartment());
    measurementService.saveMeasurement(newMeasurement);
    return "redirect:/"; // TODO ajaxiga teha? ja redirect siis juba js sees?
    // või proovida flashAttribute?
  }

  @RequestMapping(value = "/summary", method = RequestMethod.GET)
  public String openSummary(Model model) throws Exception {
    model.addAttribute("apartment", getLoggedInApartment());
    model.addAttribute("apartments", apartmentService.getApartments());
    return "summary";
  }

  private boolean isCurrentMonthMeasurementAdded(Measurement lastMeasurement) {
    if (lastMeasurement == null) {
      return false;
    }
    CalendarTimeFormat calFormat = new CalendarTimeFormat();
    Calendar lastUpdated = lastMeasurement.getDate();
    return calFormat.getCurrentYear() == calFormat.getYear(lastUpdated)
        && calFormat.getCurrentMonth() == calFormat.getMonth(lastUpdated);
  }

  private Apartment getLoggedInApartment() throws Exception {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      String apartmentNumber = ((UserDetails) principal).getUsername();
      return apartmentService.getApartment(apartmentNumber);
    }
    throw new Exception("Logged in apartment not found");
  }

  private List<Integer> getActiveYears() {
    List<Integer> years = new ArrayList<>();
    int currentYear = new CalendarTimeFormat().getCurrentYear();
    for (int i = YEARS_FROM; i <= currentYear; i++) {
      years.add(i);
    }
    return years;
  }

}
