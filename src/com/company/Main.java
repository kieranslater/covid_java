package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {
        String[] a;
        if (args.length > 0) {
            a = args[0].split( ",");
        } else {
             a = new String[]{"south-africa", "canada"};
        }

        String strStartDate = "";
        String strEndDate = "";

        if (args.length > 2) {
            strStartDate = args[1];
            strEndDate = args[2];
        } else {
            LocalDate localStartDate = LocalDate.now().minusDays(7);
            Date startDate = java.sql.Date.valueOf(localStartDate);

            Date endDate = new Date();
            endDate.setTime(System.currentTimeMillis());

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            strStartDate = dateFormat.format(startDate);
            strEndDate = dateFormat.format(endDate);
        }

        Handler h = new Handler();

        h.updateCsv(a, strStartDate, strEndDate);

    }
}
