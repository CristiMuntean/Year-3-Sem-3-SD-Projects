package model.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseFactory {
    public boolean initializeDatabase() {
        return createDatabase() && createUserTable() && createOwnerTable() && createCarTable() && createServiceLogTable();
    }

    protected boolean createDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "passw0rd");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE DATABASE auto_service");
            con.close();
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            return false;
        }
    }

    protected boolean createCarTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_service", "root", "passw0rd");
            Statement stmt = con.createStatement();
            stmt.execute("CREATE TABLE `auto_service`.`car` (" +
                    "  `id` VARCHAR(30) NOT NULL," +
                    "  `brand` VARCHAR(45) NOT NULL," +
                    "  `modelName` VARCHAR(45) NOT NULL," +
                    "  `color` VARCHAR(45) NOT NULL," +
                    "  `fuelType` VARCHAR(45) NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    protected boolean createOwnerTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_service", "root", "passw0rd");
            Statement stmt = con.createStatement();
            stmt.execute("CREATE TABLE `auto_service`.`owner` (" +
                    "  `cnp` VARCHAR(15) NOT NULL," +
                    "  `name` VARCHAR(45) NOT NULL," +
                    "  `surname` VARCHAR(45) NOT NULL," +
                    "  PRIMARY KEY (`cnp`)," +
                    "  UNIQUE INDEX `cnp_UNIQUE` (`cnp` ASC) VISIBLE);");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    protected boolean createServiceLogTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_service", "root", "passw0rd");
            Statement stmt = con.createStatement();
            stmt.execute("CREATE TABLE `auto_service`.`servicelog` (" +
                    "  `serviceNumber` VARCHAR(30) NOT NULL," +
                    "  `ownerCnp` VARCHAR(15) NOT NULL," +
                    "  `carId` VARCHAR(45) NOT NULL," +
                    "   PRIMARY KEY (`serviceNumber`)," +
                    "   UNIQUE INDEX `serviceNumber_UNIQUE` (`serviceNumber` ASC) VISIBLE," +
                    "   UNIQUE INDEX `carId_UNIQUE` (`carId` ASC) VISIBLE);");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    protected boolean createUserTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_service", "root", "passw0rd");
            Statement stmt = con.createStatement();
            stmt.execute("CREATE TABLE `auto_service`.`user` (" +
                    "  `id` VARCHAR(30) NOT NULL," +
                    "  `username` VARCHAR(30) NOT NULL," +
                    "  `password` VARCHAR(30) NOT NULL," +
                    "  `role` VARCHAR(20) NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE," +
                    "  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE);");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    protected boolean dropDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_service", "root", "passw0rd");
            Statement stmt = con.createStatement();
            stmt.execute("DROP DATABASE `auto_service`;");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }
}
