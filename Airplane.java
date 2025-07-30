package com.chiXianStudios.ChiGuysAirlines.models;

public record Airplane(Integer id, String manufacturer, String model, Integer year,
                       PlaneStatus planeStatus, Double flightHours, Double price, String engineType,
                        Integer seats) { // Class Starting Bracket

    public Airplane(Integer id, String manufacturer, String model, Integer year,
                    PlaneStatus planeStatus, Double flightHours, Double price, String engineType,
                    Integer seats) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.planeStatus = planeStatus;
        this.flightHours = flightHours;
        this.price = price;
        this.engineType = engineType;
        this.seats = seats;
    }
}
