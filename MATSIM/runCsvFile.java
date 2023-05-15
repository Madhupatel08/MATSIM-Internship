package org.matsim.delhi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class runCsvFile {
    public static void main(String[] args) {


        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/home/madhu/Desktop/IIT Roorkee/matsim-example-project/Input/Delhi/wards_in_delhi.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                String origin = values[0];
                String destination = values[1];
                System.out.println(origin + " "+destination);
                if(values == null || origin == null || destination == null){
                    continue;
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("An error occurred while closing the file reader: " + e.getMessage());
            }
        }
    }
}
