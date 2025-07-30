package com.chiXianStudios.ChiGuysAirlines.dao;

import com.chiXianStudios.ChiGuysAirlines.interfaces.AirplaneDAOInterface;
import com.chiXianStudios.ChiGuysAirlines.models.Airplane;
import com.chiXianStudios.ChiGuysAirlines.models.PlaneStatus;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class AirplaneDAO implements AirplaneDAOInterface {
    private static final Logger logger = Logger.getLogger(AirplaneDAO.class.getName());


    private final DataSource dataSource;


    public AirplaneDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<Airplane> getAll() {
        List<Airplane> airplanes = new ArrayList<>();

        String sql = "SELECT * FROM airport.airplane";

        try (Connection conn = dataSource.getConnection(); PreparedStatement statement = conn.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Airplane airplane = new Airplane
                        (rs.getInt("id"),
                                rs.getString("manufacturer"),
                                rs.getString("model"),
                                rs.getInt("year"),
                                PlaneStatus.valueOf(rs.getString("status")),
                                rs.getDouble("flight_hours"),
                                rs.getDouble("price"),
                                rs.getString("engine_type"),
                                rs.getInt("seats"));


                airplanes.add(airplane);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error returning airport inventory", e);
        }
        return airplanes;
    }

    @Override
    public boolean update(Airplane airplane) {
        if (NullChecker.hasNullField(airplane)) {
            return false;
        }
        if (airplane.flightHours() <= 0) {
            logger.log(Level.SEVERE, "Flight hours cannot be negative or 0. Please enter valid flight hours" + airplane.flightHours());
            return false;

        }

        String sql = "UPDATE airport.airplane SET manufacturer  =  ?,  model  = ?, year = ?, status = ?, flight_hours = ?, price = ?, engine_type = ?, seats = ? WHERE id = ? ";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, airplane.manufacturer());
            statement.setString(2, airplane.model());
            statement.setInt(3, airplane.year());
            statement.setString(4, airplane.planeStatus().name());
            statement.setDouble(5, airplane.flightHours());
            statement.setDouble(6, airplane.price());
            statement.setString(7, airplane.engineType());
            statement.setInt(8, airplane.seats());
            statement.setInt(9, airplane.id());

            return statement.executeUpdate() > 0;


        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error Updating rows");
        }
        return false;
    }


    @Override
    public boolean insert(Airplane airplane) {
        if (NullChecker.hasNullField(airplane)) {
            return false;
        }
        String sql = "INSERT INTO airport.airplane (manufacturer, model, year, status, flight_hours, price, engine_type, seats) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, airplane.manufacturer());
            statement.setString(2, airplane.model());
            statement.setInt(3, airplane.year());
            statement.setString(4, airplane.planeStatus().name());
            statement.setDouble(5, airplane.flightHours());
            statement.setDouble(6, airplane.price());
            statement.setString(7, airplane.engineType());
            statement.setInt(8, airplane.seats());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error inserting new data", e);
        }


        return false;
    }

    public Airplane getById(int id) {
        if (id <= 0) {
            logger.log(Level.SEVERE, " Invalid id. Please enter a valid id.");
        }
        String sql = "SELECT * FROM airport.airplane WHERE id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();

            if (set.next()) {
                return new Airplane(
                        set.getInt("id"),
                        set.getString("manufacturer"),
                        set.getString("model"),
                        set.getInt("year"),
                        PlaneStatus.valueOf(set.getString("status").toUpperCase()),
                        set.getDouble("flight_hours"),
                        set.getDouble("price"),
                        set.getString("engine_type"),
                        set.getInt("seats")

                );
            }
        } catch (SQLException e) {
          logger.log(Level.SEVERE, "Error find airplane by id" + id, e);
        }


        return null;
    }

    @Override
    public boolean deleteById(int id) {
            if (id <= 0) return false;

            String sql = "DELETE FROM airport.airplane WHERE id = ?";

            try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

                    statement.setInt(1, id);
                    return statement.executeUpdate() > 0;


            } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Error deleting data,", e);
            }



        return false;
    }

    public static class NullChecker {
        public static boolean hasNullField(Object obj) {
            if (obj == null) return true;

            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true); // allow access to private fields
                try {
                    if (field.get(obj) == null) {
                        return true; // At least one field is null
                    }
                } catch (IllegalAccessException e) {
                    logger.log(Level.SEVERE, "Error");
                }
            }
            return false; // No fields are null
        }
    }
}




