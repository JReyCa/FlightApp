package com.example.doudy.flightapplication;

public class Flight {
    private String flightNumber;
    private String originCity;
    private String destinationCity;
    private String departureDate;
    private String arrivalDate;
    private String duration;
    private String price;

    public String getFlightNumber() { return flightNumber; }

    public String getOrigin() { return originCity; }

    public String getDestination() { return destinationCity; }

    public String getDepartureDate() { return departureDate; }

    public String getArrivalDate() { return arrivalDate; }

    public String getDuration() {
        return duration;
    }

    public String getPrice() {return price;}

    public Flight(String flightNum, String origin, String destination, String departureD, String arrivalD, String dur, String p) {
        flightNumber = flightNum;
        originCity = origin;
        destinationCity = destination;
        departureDate = departureD;
        arrivalDate = arrivalD;
        duration = dur;
        price = p;
    }
}
