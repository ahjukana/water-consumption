package ee.water.helper;

import java.util.Calendar;

public class CalendarTimeFormat {

  public Calendar parseToCalendar(int year, int month) {
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(Calendar.YEAR, year);
    cal.set(Calendar.MONTH, month - 1);
    return cal;
  }

  public int getCurrentYear() {
    return Calendar.getInstance().get(Calendar.YEAR);
  }

  public int getCurrentMonth() {
    return Calendar.getInstance().get(Calendar.MONTH) + 1;
  }

  public int getMonth(Calendar calendar) {
    return calendar.get(Calendar.MONTH) + 1;
  }

  public int getYear(Calendar calendar) {
    return calendar.get(Calendar.YEAR);
  }
}
