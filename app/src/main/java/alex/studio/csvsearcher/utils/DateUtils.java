package alex.studio.csvsearcher.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date dateFromString(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
