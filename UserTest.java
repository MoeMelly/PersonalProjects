package com.chiXianStudios.ChiGuysAirlines;

import com.chiXianStudios.ChiGuysAirlines.dao.AirplaneDAO;
import com.chiXianStudios.ChiGuysAirlines.models.Airplane;
import com.chiXianStudios.ChiGuysAirlines.models.PlaneStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class UserTest implements CommandLineRunner {
    private static final Logger logger = Logger.getLogger(UserTest.class.getName());
    static Scanner scanner = new Scanner(System.in);
    private final AirplaneDAO airplaneDAO;


    public UserTest(AirplaneDAO airplaneDAO) {
        this.airplaneDAO = airplaneDAO;
    }


    @Override
    public void run(String... args) {
        boolean keepRunning = true;
        while (keepRunning) {
            greetUser();
            System.out.println("\uD83D\uDC4B\uD83C\uDD95\uD83D\uDC64What Would you like to do today?:");
            System.out.println("\n==============================");
            System.out.println("✈️  AIRPORT MANAGEMENT MENU");
            System.out.println("==============================");
            System.out.println("1. --List All Airport Entities-- ");
            System.out.println("2. --Search for Plane by ID--");
            System.out.println("3. --Create New Airplane Entity--");
            System.out.println("4. --Delete an Airplane Entity--");
            System.out.println("5. --Update an Airplane Entity--");
            System.out.println("------------------------------");
            System.out.println("6. 🚪  Exit Menu");
            System.out.println("==============================");
            System.out.print("Enter your choice (1-6): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> displayPlanes();
                case 2 -> searchById();
                case 3 -> createPlaneEntity();
                case 4 -> deletePlane();
                case 5 -> updatePlane();
                case 6 -> {
                    keepRunning = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Unexpected value: " + choice);
            }

        }

    }

    public void greetUser() {
        String name = "";
        while (name.isBlank()) {
            System.out.println("Enter your name: ");
            name = scanner.nextLine().trim();
            if (name.isBlank()) {
                System.out.println("Name cannot be empty. Please try again.");
            }
            LocalTime currentTime = LocalTime.now();
            int hour = currentTime.getHour();
            String greeting;

            if (hour >= 5 && hour < 12) {
                greeting = "Good Morning!";
            } else if (hour >= 12 && hour < 17) {
                greeting = "Good Afternoon!";

            } else if (hour >= 17 && hour < 21) {
                greeting = "Good Evening!";

            } else {
                greeting = "Good Night";
            }
            LocalDate date = LocalDate.now();
            System.out.println("\n" + greeting + " " + name + "!");
            System.out.println("Current time: " + currentTime.withNano(0));
            System.out.println("Today's date: " + date);
            System.out.println("======================");


        }
    }

    public void displayPlanes() {
        List<Airplane> airplanes = airplaneDAO.getAll();
        for (Airplane planes : airplanes) {
            System.out.println(planes);
        }
    }

    public void searchById() {
        System.out.println("Enter Plane ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Airplane airplane = airplaneDAO.getById(id);
        if (airplane != null) {
            System.out.println("Plane Found\n: " + airplane);
        } else {
            System.out.println("Plane not found with id: " + id);
        }
    }

    public void createPlaneEntity() {
        System.out.println("Enter Plane Details Below!:");

        System.out.println("Enter Plane Manufacturer: ");
        String manufacturer = scanner.nextLine();

        System.out.println("Enter Plane Model: ");
        String model = scanner.nextLine();

        System.out.println("Enter Plane Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        PlaneStatus status = null;
        while (status == null) {
            System.out.println("Enter Plane Status) Available, Maintenance, Sold): ");
            String input = scanner.nextLine().toUpperCase();
            try {
                status = PlaneStatus.valueOf(input);
            } catch (IllegalArgumentException e) {
                logger.log(Level.WARNING, "Invalid status entered. Please try again.");

            }
            System.out.println("You entered: " + input);
        }
        System.out.println("Enter FlightHours: ");
        double flighthours = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter Plane Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter Plane Engine Type: ");
        String engineType = scanner.nextLine();


        System.out.println("Enter Seat Count: ");
        int seats = scanner.nextInt();
        scanner.nextLine();

        Airplane airplane = new Airplane(null, manufacturer, model, year, status, flighthours, price, engineType, seats);
        boolean success = airplaneDAO.insert(airplane);
        System.out.println(success ? "Airplane Entity has been created!" : "Failed to create Plane.");
    }

    public void deletePlane() {
        System.out.println("Enter Plane Id: ");
        int id = scanner.nextInt();
        boolean success = airplaneDAO.deleteById(id);
        System.out.println(success ? "Airplane has been deleted!" : "Failed to remove Plane entity.");
    }

    public void updatePlane() {
        System.out.println("Enter Plane Id to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter new manufacturer: ");
        String manufacturer = scanner.nextLine();

        System.out.println("Enter new Model: ");
        String model = scanner.nextLine();

        System.out.println("Enter new year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        PlaneStatus status = null;
        while (status == null) {
            System.out.println("Enter new Plane Status) Available, Maintenance, Sold): ");
            String input = scanner.nextLine().toUpperCase();
            try {
                status = PlaneStatus.valueOf(input);
            } catch (IllegalArgumentException e) {
                logger.log(Level.WARNING, "Invalid status entered. Please try again.");

            }
            System.out.println("You entered: " + input);
        }
        System.out.println("Enter new flighthours: ");
        double flighthours = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter new price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter new engineType: ");
        String enginetype = scanner.nextLine();

        System.out.println("Enter new seat count: ");
        int seats = scanner.nextInt();
        scanner.nextLine();

        Airplane airplane = new Airplane(null, manufacturer, model, year, status, flighthours, price, enginetype, seats);
        boolean success = airplaneDAO.update(airplane);
        System.out.println(success ? "Successfully Updated Plane Query" : "Error Updating query");
    }
    //public void
}






