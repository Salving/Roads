package salving.roads.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    public static Date tomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

}
