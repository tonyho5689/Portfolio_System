package com.example.portfolio_system.utility;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class WriteToFile {
    public void write() {
        try {
            FileWriter myWriter = new FileWriter("portfolio_report.txt");
            myWriter.write("Files in Java might be tricky, but it is fun enough!");
            myWriter.write("TEST");

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}