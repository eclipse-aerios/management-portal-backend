package eu.aeriosproject.managementportal.models;

import java.util.ArrayList;

public class Location{
    private String type;
    private ArrayList<Double> coordinates;

    public Location(String type, ArrayList<Double> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public Location() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }
}