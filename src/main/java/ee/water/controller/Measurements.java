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
import ee.water.model.ErrorField;
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

  @Autowired
  private MessageSource messageSource;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String openMonthlyMeasurement(Model model) throws Exception {
    Apartment loggedInApartment = apartmentService.getLoggedInApartment();
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
    return measurementService.getMeasurement(apartmentService.getLoggedInApartment().getNumber(),
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
      newMeasurement.setApartment(apartmentService.getLoggedInApartment());
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
  public String openSummary(Model model) throws Exception {
    model.addAttribute("apartment", apartmentService.getLoggedInApartment());
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

  private List<Integer> getActiveYears() {
    List<Integer> years = new ArrayList<>();
    int currentYear = new CalendarTimeFormat().getCurrentYear();
    for (int i = YEARS_FROM; i <= currentYear; i++) {
      years.add(i);
    }
    return years;
  }

}
