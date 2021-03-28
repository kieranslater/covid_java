package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Api {
    private final String URL_COVID = " https://api.covid19api.com/";

    // default value of 7 days of data
    public String requestData(String country) throws Exception {


        // new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(mapDetails.get("date"))
        Date startDate = new Date();
        startDate.setTime(System.currentTimeMillis() - 7 * 60 * 60 * 1000);

        Date endDate = new Date();
        endDate.setTime(System.currentTimeMillis());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String strStartDate = dateFormat.format(startDate);
        String strEndDate = dateFormat.format(endDate);

        return this.requestData(country, strStartDate, strEndDate);
    }

    public String requestData(String country, String startDate, String endDate) throws Exception {

        // https://api.covid19api.com/country/south-africa?from=2020-03-01T00:00:00Z&to=2020-04-01T00:00:00Z
        String path = "/country/";

        String urlString = URL_COVID + "country/" + country + "?from=" + startDate + "&to=" + endDate;

        URL url = new URL(urlString);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        con.setRequestMethod("GET");

        int status = con.getResponseCode();

        if (status == 200) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();

            return content.toString();
        } else {
            throw new Exception("call failed return code " + status);
        }
    }


}
