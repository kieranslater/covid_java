package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Csv {

    public void writeToCsv(ArrayList<Row> rowList, String country) throws IOException {
        String filename = country + "_stats.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Row row :
             rowList) {
            writer.append(row.toString() + "\r\n");
        }
        writer.close();
    }
}
