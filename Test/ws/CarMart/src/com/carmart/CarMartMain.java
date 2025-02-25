package com.carmart;

import com.carmart.crud.CarCRUD;
import com.carmart.model.Car;

import java.util.List;
import java.util.Scanner;

public class CarMartMain {
    private static final CarCRUD carCRUD = new CarCRUD();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nCar Mart");
            System.out.println("-----------------------------");
            System.out.println("1. Add");
            System.out.println("2. Search");
            System.out.println("3. Update (Price)");
            System.out.println("4. Sold");
            System.out.println("5. Exit");
            System.out.println("---------------------------");

            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> addCar();
                case 2 -> searchMenu();
                case 3 -> updatePrice();
                case 4 -> soldMenu();
                case 5 -> {
                    System.out.println("Application Exited Successfully");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void addCar() {
        System.out.print("Enter Company: ");
        String company = SCANNER.nextLine().trim();

        System.out.print("Enter Model: ");
        String model = SCANNER.nextLine().trim();

        int seater = readInt("Enter Seater: ");

        System.out.print("Enter FuelType: ");
        String fuelType = SCANNER.nextLine().trim();

        System.out.print("Enter Type (Hatchback/Sedan/SUV): ");
        String type = SCANNER.nextLine().trim();

        double price = readDouble("Enter Price: ");

        boolean sold = readBoolean("Enter Sold Status (true/false): ");

        Car car = new Car(0, company, model, seater, fuelType, type, price, sold);
        carCRUD.addCar(car);
    }

    private static void searchMenu() {
        while (true) {
            System.out.println("\nSearch Menu");
            System.out.println("1. Display all unsold cars");
            System.out.println("2. By Company");
            System.out.println("3. By Type");
            System.out.println("4. By Price Range");
            System.out.println("5. Exit");

            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> {
                    List<Car> unsold = carCRUD.getAllUnsoldCars();
                    displayCars(unsold);
                }
                case 2 -> {
                    System.out.print("Enter Company: ");
                    String company = SCANNER.nextLine().trim();
                    List<Car> byCompany = carCRUD.getCarsByCompany(company);
                    displayCars(byCompany);
                }
                case 3 -> {
                    System.out.print("Enter Type (Hatchback/Sedan/SUV): ");
                    String type = SCANNER.nextLine().trim();
                    List<Car> byType = carCRUD.getCarsByType(type);
                    displayCars(byType);
                }
                case 4 -> {
                    double min = readDouble("Enter Min Price: ");
                    double max = readDouble("Enter Max Price: ");
                    List<Car> byRange = carCRUD.getCarsByPriceRange(min, max);
                    displayCars(byRange);
                }
                case 5 -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void updatePrice() {
        int carId = readInt("Enter CarID to update price: ");
        double newPrice = readDouble("Enter new Price: ");
        carCRUD.updateCarPrice(carId, newPrice);
    }

    private static void soldMenu() {
        while (true) {
            System.out.println("\nSold Menu");
            System.out.println("1. Display all the cars which have been sold");
            System.out.println("2. Update unsold cars to sold");
            System.out.println("3. Exit");

            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> {
                    List<Car> soldCars = carCRUD.getAllSoldCars();
                    displayCars(soldCars);
                }
                case 2 -> {
                    int carId = readInt("Enter CarID to mark as sold: ");
                    carCRUD.markCarAsSold(carId);
                }
                case 3 -> {
                    System.out.println("Returning to main menu");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void displayCars(List<Car> cars) {
        if (cars.isEmpty()) {
            System.out.println("No cars found!");
            return;
        }
        System.out.println("\nCar List:");
        for (Car c : cars) {
            System.out.println("CarID: " + c.getCarId()
                    + ", Company: " + c.getCompany()
                    + ", Model: " + c.getModel()
                    + ", Seater: " + c.getSeater()
                    + ", FuelType: " + c.getFuelType()
                    + ", Type: " + c.getType()
                    + ", Price: " + c.getPrice()
                    + ", Sold: " + c.isSold());
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Please enter a valid integer.");
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Retry.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Please enter a valid number.");
                continue;
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Retry");
            }
        }
    }

    private static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim().toLowerCase();
            if (input.equals("true")) {
                return true;
            } else if (input.equals("false")) {
                return false;
            } else {
                System.out.println("Invalid input. Enter only 'true' or 'false'.");
            }
        }
    }
}
