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
}
