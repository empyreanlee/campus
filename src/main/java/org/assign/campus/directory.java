package org.assign.campus;


import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class directory {
    interface PasswordUtil {
        String encryptPassword(String password);

        Boolean checkPassword(String plainPassword, String hashedPassword);
    }

    public static class BCryptPasswordUtil implements PasswordUtil {
        @Override
        public String encryptPassword(String password) {
            return BCrypt.hashpw(password, BCrypt.gensalt(12));
        }

        @Override
        public Boolean checkPassword(String plainPassword, String hashedPassword) {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        }
    }

    public static void insertUser(String nameval, String emailval, String passwordval, String cpassword) throws SQLException {
        if (!passwordval.equals(cpassword))
            throw new IllegalArgumentException("Passwords do not match!");

        BCryptPasswordUtil passwordUtil = new BCryptPasswordUtil();
        String hashedPassword = passwordUtil.encryptPassword(passwordval);

        postgresConn dbconn = new postgresConn();
        Connection conn = dbconn.getConnection();
        if (conn != null) {
            String sql = "INSERT INTO campus.campus (name,email,password,cpassword) VALUES (?,?,?,?)";
            String query = "SELECT \"regNumber\" FROM campus.student WHERE id = (SELECT id FROM campus.campus WHERE email = ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameval);
            stmt.setString(2, emailval);
            stmt.setString(3, hashedPassword);
            stmt.setString(4, hashedPassword);
            stmt.executeUpdate();
            stmt.close();
        } else {
            System.err.println("Error: connection is null");
        }
    }

    public static void insertStudentDetails(String regNumber) throws SQLException {
        StudentDetails details = StudentDetails.extractStudentDetails(regNumber);
        postgresConn dbconn = new postgresConn();
        Connection conn = dbconn.getConnection();
        if (conn != null) {
            String sql = "INSERT INTO campus.student(regNumber, courseName, Year) VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, regNumber);
            stmt.setString(2, details.deptName);
            stmt.setInt(3, details.yearOfStudy);
            stmt.executeUpdate();
            conn.close();
        }
    }

    public static Boolean confirmUser(String loginPassword, String loginEmail) throws SQLException {
        BCryptPasswordUtil passwordUtil = new BCryptPasswordUtil();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            postgresConn dbconn = new postgresConn();
            conn = dbconn.getConnection();
            String sql = "SELECT * FROM campus.campus WHERE email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, loginEmail);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString(loginPassword);
                return passwordUtil.checkPassword(loginPassword, hashedPassword);
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            postgresConn.close(conn, stmt, rs);
        }
    }
}


