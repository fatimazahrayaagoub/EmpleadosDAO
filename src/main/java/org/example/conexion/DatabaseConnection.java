package org.example.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    String url = "jdbc:mariadb://localhost:3306/empresa";
    String user = "root";
    String password = "1234";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
}
