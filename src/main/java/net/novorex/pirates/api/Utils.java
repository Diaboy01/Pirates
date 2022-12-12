package net.novorex.pirates.api;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return simpleDateFormat.format(new Date());
    }
}