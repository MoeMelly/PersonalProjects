package com.chiXianStudios.ChiGuysAirlines.controllers;
import com.chiXianStudios.ChiGuysAirlines.models.PlaneStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chiXianStudios.ChiGuysAirlines.interfaces.AirplaneDAOInterface;
import com.chiXianStudios.ChiGuysAirlines.models.Airplane;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AirplaneController {


    @Autowired
    private AirplaneDAOInterface airplaneDAOInterface;


    @GetMapping("/airplanes")
    public List<Airplane> getAirplanes() {
        return airplaneDAOInterface.getAll();
    }

    @GetMapping("/airplane/id/{id}")
    public Airplane getAirplaneById(@PathVariable Integer id) {
        return getAirplanes().stream()
                .filter(plane -> id.equals(plane.id()))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/airplane/model/{model}")
    public List<Airplane> getAirplaneByModel(@PathVariable String model) {
        return getAirplanes().stream()
                .filter(plane -> model.equals(plane.model()))
                .toList();
    }

    @GetMapping("/airplane/manufacturer/{manufacturer}")
    public List<Airplane> getAirplaneByManuFacturer(@PathVariable String manufacturer) {
        return (getAirplanes().stream()
                .filter(plane -> manufacturer.equals(plane.manufacturer()))
                .toList());
    }

    @GetMapping("/airplane/year/{year}")
    public List<Airplane> getAirplaneByYear(@PathVariable Integer year) {
        return getAirplanes().stream()
                .filter(p -> Objects.equals(p.year(), year))
                .toList();
    }

    @GetMapping("/airplane/seats/{seats}")
    public int seatCount(@PathVariable Integer seats) {
        return getAirplanes().stream()
                .filter(s -> Objects.equals(s.seats(), seats))
                .mapToInt(Airplane::seats)
                .sum();
    }

    @GetMapping("/airplane/flight-hours")
    public List<Airplane> getFlightHoursByPlaneRange(@RequestParam int min, @RequestParam int max) {
        return getAirplanes().stream()
                .filter(plane -> plane.flightHours() >= min && plane.flightHours() <= max)
                .collect(Collectors.toList());
    }

    @GetMapping("/airplane/price/{price}")
    public List<Airplane> getAirplaneByPrice(@PathVariable double price) {
        return getAirplanes().stream()
                .filter(p -> Objects.equals(p.price(), price))
                .toList();

    }

    @GetMapping("/airplane/engine-type/{type}")
    public List<Airplane> getAirplaneByEngineType(@PathVariable String type) {
       return getAirplanes().stream()
                .filter(e -> type.equals(e.engineType()))
                .toList();



    }
    @GetMapping("/airplane/plane-status{status}")
    public List<Airplane> getPlanesByStatus (@PathVariable PlaneStatus status) {
        return getAirplanes().stream()
                .filter(s -> s.planeStatus().equals(status))
                .toList();

    }
}
