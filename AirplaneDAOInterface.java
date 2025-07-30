package com.chiXianStudios.ChiGuysAirlines.interfaces;

import com.chiXianStudios.ChiGuysAirlines.models.Airplane;

import java.util.List;

public interface AirplaneDAOInterface {
    List<Airplane> getAll();

    boolean update(Airplane airplane);

    boolean insert(Airplane airplane);

    Object getById(int id);

    boolean deleteById(int id);
}
