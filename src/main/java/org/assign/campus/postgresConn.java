package org.assign.campus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class postgresConn {
    String url = "jdbc:postgresql://localhost:5432/Campus";
    String user = System.getenv("DB_USER");
    String password = System.getenv("DB_PASS");

    public Connection getConnection() {
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
