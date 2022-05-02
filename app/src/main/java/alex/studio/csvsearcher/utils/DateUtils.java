package alex.studio.csvsearcher.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static Date toDate(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static Date doDateOperation(Date date, int field, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, count);
        return c.getTime();
    }

    public static int getDateField(Date date, int field) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(field);
    }

    public static String toDateString(Integer day, Integer month, Integer year) {
        StringBuilder sb = new StringBuilder();
        int finalDay = getMaxDayOfMonth(day, (month - 1), year);
        sb.append(finalDay < 10 ? "0" + finalDay : String.valueOf(finalDay));
        sb.append(".");
        sb.append(month < 10 ? "0" + month : String.valueOf(month));
        sb.append(".");
        sb.append(year);
        return sb.toString();
    }

    public static int getMaxDayOfMonth(int curDay, int month, int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1);
        int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (curDay > maxDay) {
            return maxDay;
        }
        return curDay;
    }
}
