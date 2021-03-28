package com.company;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.ContainerFactory;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.util.*;

public class Handler {


    public void updateCsv(String[] countries, String startDate, String endDate) throws Exception {

        // api call new data
        Api api = new Api();
        Csv csv = new Csv();
        for (String country:
             countries) {
            String response = api.requestData(country, startDate, endDate);
            ArrayList<Row> rowList = this.responseToRowArray(response, startDate, endDate);

            // write to csv
            csv.writeToCsv(rowList, country);
        }
    }


    private ArrayList<Row> responseToRowArray(String jsonResults, String startDate, String endDate) throws ParseException, java.text.ParseException {
        // takes in the json and returns the row object
        JSONParser parser = new JSONParser();

        ContainerFactory containerFactory = new ContainerFactory(){
            public ArrayList<Object> creatArrayContainer() {
                return new ArrayList<Object>();
            }

            public Map createObjectContainer() {
                return new LinkedHashMap();
            }

        };


        ArrayList<Object> json = (ArrayList<Object>)parser.parse(jsonResults, containerFactory);

        ArrayList<Row> wantedRows = new ArrayList<>();

        for (Object entry : json) {

//            System.out.println(entry.toString());

            Map<String, String> entryMap = this.entryToMap(entry.toString());

            Date date = this.stringToDate(entryMap.get("date"));

            Date startDateObject =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(startDate);
            Date endDateObject =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(endDate);


            if ((date.after(startDateObject) || date.equals(startDateObject))
                    && (date.before(endDateObject) || date.equals(endDateObject))) {
                wantedRows.add(new Row(entryMap));
            }
        }

        wantedRows.forEach(entryRow -> {
            System.out.println(entryRow.toString());
        });

        return wantedRows;
    }



    private Map<String, String> entryToMap(String entry) {
        String trimmed = entry.toString().substring( 1, entry.toString().length() - 1 );

        String[] strArr = trimmed.split(",");

        HashMap<String, String> entryDetails = new HashMap<String, String>();

        for (String str : strArr) {
            String[] keyValue = str.split("=");

            if (keyValue.length == 2) {
                entryDetails.put(keyValue[0].trim().toLowerCase(Locale.ROOT), keyValue[1].trim());
            } else if (keyValue.length == 1) {
                entryDetails.put(keyValue[0].trim().toLowerCase(Locale.ROOT), "");
            } else {
                System.out.println("this should not be happening...");
            }
        }

//        entryDetails.entrySet().forEach(entryRow -> {
//            System.out.println(entryRow.getKey() + " " + entryRow.getValue());
//        });

        return entryDetails;
    }

    private Date stringToDate(String date) throws java.text.ParseException {
        Date dateObj = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(date);
        return dateObj;
    }
}
