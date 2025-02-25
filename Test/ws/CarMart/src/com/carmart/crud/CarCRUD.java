package com.carmart.crud;

import com.carmart.db.DBConnection;
import com.carmart.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarCRUD {

    public void addCar(Car car) {
        String sql = "INSERT INTO cars (company, model, seater, fuel_type, type, price, sold) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, car.getCompany());
            ps.setString(2, car.getModel());
            ps.setInt(3, car.getSeater());
            ps.setString(4, car.getFuelType());
            ps.setString(5, car.getType());
            ps.setDouble(6, car.getPrice());
            ps.setBoolean(7, car.isSold());
            ps.executeUpdate();
            
            System.out.println("Car added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getAllUnsoldCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE sold = false";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(extractCarFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getCarsByCompany(String company) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE company = ? AND sold = false";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, company);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cars.add(extractCarFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getCarsByType(String type) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE type = ? AND sold = false";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cars.add(extractCarFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> getCarsByPriceRange(double min, double max) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE price BETWEEN ? AND ? AND sold = false";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, min);
            ps.setDouble(2, max);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cars.add(extractCarFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    public void updateCarPrice(int carId, double newPrice) {
        String sql = "UPDATE cars SET price = ? WHERE car_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newPrice);
            ps.setInt(2, carId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Price updated successfully!");
            } else {
                System.out.println("No car found with ID: " + carId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getAllSoldCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE sold = true";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cars.add(extractCarFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public void markCarAsSold(int carId) {
        String sql = "UPDATE cars SET sold = true WHERE car_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Car marked as sold!");
            } else {
                System.out.println("No car found with ID: " + carId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Car extractCarFromResultSet(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setCarId(rs.getInt("car_id"));
        car.setCompany(rs.getString("company"));
        car.setModel(rs.getString("model"));
        car.setSeater(rs.getInt("seater"));
        car.setFuelType(rs.getString("fuel_type"));
        car.setType(rs.getString("type"));
        car.setPrice(rs.getDouble("price"));
        car.setSold(rs.getBoolean("sold"));
        return car;
    }
}
