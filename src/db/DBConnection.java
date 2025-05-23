/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ACER
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn != null) {
            return conn;
        }

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database URL, username and password
            String url = "jdbc:mysql://localhost:3306/hostel"; //  DB name
            String user = "root"; //  MySQL  username
            String password = "admin123"; // MySQL password

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
