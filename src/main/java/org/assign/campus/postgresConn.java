package org.assign.campus;

import java.sql.*;

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
    public static void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        if(rs != null) rs.close();
        if(stmt != null) stmt.close();
        if(conn != null) conn.close();
    }
}
