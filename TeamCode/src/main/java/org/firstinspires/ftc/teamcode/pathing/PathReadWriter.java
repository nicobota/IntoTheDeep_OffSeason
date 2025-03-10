package org.firstinspires.ftc.teamcode.pathing;

import java.io.*;
import java.net.URISyntaxException;

import org.firstinspires.ftc.teamcode.subsystems.drivebase;

public class PathReadWriter {

    public String directory = jarLocation() + "paths/";

    public static class FullPath {
        public String name;
        public double x_coord;
        public double y_coord;
        public double heading;
        public double power;

        public FullPath(double xInput, double yInput, double headingInput, double powerInput) {
            this.x_coord = xInput;
            this.y_coord = yInput;
            this.heading = headingInput;
            this.power = powerInput;
        }

    }


    public void writeToFile(String fileName, FullPath path) {
        String absolutePath = directory + File.separator + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(absolutePath))) {

            writer.write("{"); writer.newLine();
            writer.write(" X: " + String.valueOf(path.x_coord)); writer.newLine();
            writer.write(" Y: " + String.valueOf(path.y_coord)); writer.newLine();
            writer.write(" Heading: " + String.valueOf(path.heading)); writer.newLine();
            writer.write(" Power Scale: " + String.valueOf(path.power)); writer.newLine();
            writer.write("}"); writer.newLine();

            System.out.println("File written successfully: " + absolutePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPath(String fileName, FullPath path) {
        String absolutePath = directory + File.separator + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(absolutePath, true))) {
            writer.newLine();

            writer.write("{"); writer.newLine();
            writer.write(" X: " + String.valueOf(path.x_coord)); writer.newLine();
            writer.write(" Y: " + String.valueOf(path.y_coord)); writer.newLine();
            writer.write(" Heading: " + String.valueOf(path.heading)); writer.newLine();
            writer.write(" Power Scale: " + String.valueOf(path.power)); writer.newLine();
            writer.write("}"); writer.newLine();

            System.out.println("File written successfully: " + absolutePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FullPath readFirstPath(String fileName) {
        String absolutePath = directory + File.separator + fileName;

        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {

            reader.readLine(); // "{"
            String xString = reader.readLine();
            String yString = reader.readLine();
            String headingString = reader.readLine();
            String powerString = reader.readLine();

            double x_coord = Double.parseDouble(xString.trim().split(":")[1].trim());
            double y_coord = Double.parseDouble(yString.trim().split(":")[1].trim());
            double heading = Double.parseDouble(headingString.trim().split(":")[1].trim());
            double powerScale = Double.parseDouble(powerString.trim().split(":")[1].trim());

            return new FullPath(x_coord, y_coord, heading, powerScale);
        } catch (IOException e) {
            e.printStackTrace(); // Return read error
            return new FullPath(0, 0,0,0);
        }
    }
    public FullPath readPath(String fileName, int index) {
        String absolutePath = directory + File.separator + fileName;
        int counter = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            while (counter != index) {
                reader.readLine(); reader.readLine();
                reader.readLine(); reader.readLine();
                reader.readLine(); reader.readLine();
                reader.readLine();
                counter += 1;
            }
            reader.readLine(); // "{"
            String xString = reader.readLine();
            String yString = reader.readLine();
            String headingString = reader.readLine();
            String powerString = reader.readLine();

            double x_coord = Double.parseDouble(xString.trim().split(":")[1].trim());
            double y_coord = Double.parseDouble(yString.trim().split(":")[1].trim());
            double heading = Double.parseDouble(headingString.trim().split(":")[1].trim());
            double powerScale = Double.parseDouble(powerString.trim().split(":")[1].trim());

            return new FullPath(x_coord, y_coord, heading, powerScale);

        } catch (IOException e) {
            e.printStackTrace(); // Return read error
            return new FullPath(0,0,0,0);
        }
    }
    public void returnPath(String fileName, int index) {
        String absolutePath = directory + File.separator + fileName;
        int counter = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            while (counter != index) { /* Skips to specified index */
                reader.readLine(); reader.readLine();
                reader.readLine(); reader.readLine();
                reader.readLine(); reader.readLine();
                reader.readLine();
                counter += 1;
            }
            reader.readLine(); // "{"
            String xString = reader.readLine();
            String yString = reader.readLine();
            String headingString = reader.readLine();
            String powerString = reader.readLine();

            double x_coord = Double.parseDouble(xString.trim().split(":")[1].trim());
            double y_coord = Double.parseDouble(yString.trim().split(":")[1].trim());
            double heading = Double.parseDouble(headingString.trim().split(":")[1].trim());
            double powerScale = Double.parseDouble(powerString.trim().split(":")[1].trim());

            System.out.println(x_coord);
            System.out.println(y_coord);
            System.out.println(heading);
            System.out.println(powerScale);

        } catch (IOException e) {
            e.printStackTrace(); // Return read error
        }
    }

    public boolean pathFileExists(String fileName) {
        String absolutePath = directory + File.separator + fileName;
        File file = new File(absolutePath);

        if (file.exists()) {
            return true;
        }
        else {
            return false;
        }
    }

    public String jarLocation() {
        try {
            // Get the location of the JAR file
            File jarFile = new File(PathReadWriter.class.getProtectionDomain().getCodeSource().getLocation().toURI());

            // Get the directory containing the JAR
            String output = jarFile.getParent() + "/";

            return output;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
    public int getPathIndexQuantity(String fileName) {
        String absolutePath = directory + File.separator + fileName;
        int counter = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {

            while (reader.readLine() != null) { counter ++;}

            return counter;

        } catch (IOException e) {
            e.printStackTrace();
            return counter;
        }
    }

}