package br.com.amcom.laa.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {

    public static final String DD_MM_YYYY = "dd/MM/yyyy";

    private DateUtil() { }

    public static boolean isValidDate(String data) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
            sdf.setLenient(false);
            sdf.parse(data);

            return true;
        } catch (ParseException ex) {
            return false;
        }
    }
}
