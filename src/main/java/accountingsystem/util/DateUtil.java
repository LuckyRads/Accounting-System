package accountingsystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * Parses date from String dd/MM/yyyy to Java Date format
     * @param stringDate
     * @return
     */
    public Date parseDate(String stringDate) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            System.out.print("Bad date format!");
        }
        return date;
    }

}
