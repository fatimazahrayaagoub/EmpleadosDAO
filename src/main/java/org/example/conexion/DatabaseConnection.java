package org.example.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    String url="jdbc:sqlite:database.db";

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url);
    }
}
