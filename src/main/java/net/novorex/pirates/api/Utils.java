package net.novorex.pirates.api;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT_LIGHT = new SimpleDateFormat("dd.MM.yyyy");

    public static String getDate() {
        return DATE_FORMAT_LIGHT.format(new Date());
    }

    public static String getFullDate() {
        return DATE_FORMAT.format(new Date());
    }
}