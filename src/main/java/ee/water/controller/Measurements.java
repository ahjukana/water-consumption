package ee.water.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ee.water.helper.CalendarTimeFormat;
import ee.water.model.Apartment;
import ee.water.model.ApartmentPeriodSummary;
import ee.water.model.ErrorField;
import ee.water.model.Measurement;
import ee.water.service.ApartmentService;
import ee.water.service.MeasurementService;
import ee.water.service.UserService;

@Controller
@RequestMapping("")
public class Measurements {

  private static final int YEARS_FROM = 2018;

  @Autowired
  ApartmentService apartmentService;

  @Autowired
  MeasurementService measurementService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private UserService userService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String openMonthlyMeasurement(Model model) throws Exception {
    Apartment loggedInApartment = userService.getLoggedInApartment();
    Measurement lastMeasurement = measurementService.getLastApartmentMeasurement(
        loggedInApartment.getNumber());
    boolean currentMonthInserted = isCurrentMonthMeasurementAdded(lastMeasurement);

    model.addAttribute("currentMonthInserted", currentMonthInserted);
    model.addAttribute("apartment", loggedInApartment);
    model.addAttribute("lastMeasurement", lastMeasurement);
    model.addAttribute("newMeasurement", new Measurement());
    model.addAttribute("dropdownYears", getActiveYears());
    return "monthlyMeasurement";
  }

  @RequestMapping(value = "/getMeasurement", method = RequestMethod.GET)
  @ResponseBody
  public Measurement getMeasurement(@RequestParam int selectedYear,
      @RequestParam int selectedMonth) throws Exception {
    return measurementService.getMeasurement(userService.getLoggedInApartment().getNumber(),
        new CalendarTimeFormat().parseToCalendar(selectedYear, selectedMonth));
  }

  @RequestMapping(value = "/saveMeasurement", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> saveMonthlyMeasurement(
      @ModelAttribute("newMeasurement") @Valid Measurement newMeasurement,
      BindingResult bindingResult) throws Exception {
    Map<String, Object> map = new HashMap<>();
    List<ErrorField> errorFieldList = new ArrayList<>();
    if (bindingResult.hasFieldErrors()) {
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        errorFieldList.add(new ErrorField(fieldError.getField(),
            messageSource
                .getMessage(fieldError.getDefaultMessage(), null, LocaleContextHolder.getLocale())
        ));
      }
    }

    if (errorFieldList.isEmpty()) {
      newMeasurement.calculateCalendarTime();
      newMeasurement.setApartment(userService.getLoggedInApartment());
      measurementService.saveMeasurement(newMeasurement);

      map.put("hasErrors", false);
      map.put("url", "/");
    } else {
      map.put("hasErrors", true);
      map.put("errorFieldList", errorFieldList);
    }
    return map;
  }

  @RequestMapping(value = "/summary", method = RequestMethod.GET)
  public String openSummary(@RequestParam int year, @RequestParam int month, Model model)
      throws Exception {
    model.addAttribute("apartment", userService.getLoggedInApartment());
    model.addAttribute("dropdownYears", getActiveYears());
    model.addAttribute("report", getPeriodReport(year, month));
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

  private List<Integer> getActiveYears() {
    List<Integer> years = new ArrayList<>();
    int currentYear = new CalendarTimeFormat().getCurrentYear();
    for (int i = YEARS_FROM; i <= currentYear; i++) {
      years.add(i);
    }
    return years;
  }

  private List<ApartmentPeriodSummary> getPeriodReport(int year, int month) {
    Calendar calendar = new CalendarTimeFormat().parseToCalendar(year, month);
    List<Measurement> measurements = measurementService.getMeasurementsForDate(calendar);
    List<ApartmentPeriodSummary> report = new ArrayList<>();
    for (int i = 1; i <= 25; i++) {
      String num = Integer.toString(i);
      boolean hasMeasurement = false;
      ApartmentPeriodSummary summary = new ApartmentPeriodSummary();
      summary.setApartmentNumber(num);
      for (Measurement measurement : measurements) {
        if (num.equals(measurement.getApartment().getNumber())) {
          hasMeasurement = true;
          summary.setMeasurement(measurement);
        }
      }
      summary.setMeasurementsMissing(hasMeasurement);
      report.add(summary);
    }
    return report;
  }

}
