package salving.roads.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SS";

    public static Date tomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

    public static Date nextMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

}
