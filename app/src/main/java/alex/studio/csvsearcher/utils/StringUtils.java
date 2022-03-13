package alex.studio.csvsearcher.utils;

public class StringUtils {

    public static String generateDateString(int day, int month, int year) {
        return addZero(day) + "." + addZero(month + 1) + "." + year;
    }

    public static String addZero(int val) {
        if(val < 10) {
            return "0" + val;
        }
        return val + "";
    }
}
