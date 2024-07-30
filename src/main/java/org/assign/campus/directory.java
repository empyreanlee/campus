package org.assign.campus;


import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assign.campus.course_directory.*;

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
            conn.close();
        } else {
            System.err.println("Error: connection is null");
        }
    }

    public static void insertStudentDetails(String regNumber, String email) throws SQLException {
        int detailsId = getDetailsIdById(email);
        if (detailsId == -1) throw new SQLException("Student details not found");
        StudentDetails details = StudentDetails.extractStudentDetails(regNumber);
        postgresConn dbconn = new postgresConn();
        Connection conn = dbconn.getConnection();
        if (conn != null) {
            String sql = "INSERT INTO campus.student(course_name,year,reg_number, details_id) VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, details.deptName);
            stmt.setInt(2, details.yearOfStudy);
            stmt.setString(3, regNumber);
            stmt.setInt(4,detailsId);
            stmt.executeUpdate();
            stmt.close();
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
                String hashedPassword = rs.getString("password");
                return passwordUtil.checkPassword(loginPassword, hashedPassword);
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            postgresConn.close(conn, stmt, rs);
        }
    }
    public static Boolean isRegisteredCourses(String email) throws SQLException {
        String regNumber = getRegNobyEmail(email);
        if (regNumber == null) return false;

        int studentId = getStudentIdByRegNo(regNumber);
        if (studentId == -1) return false;
        return hasRegisteredCourses(studentId);
    }

    private static Boolean hasRegisteredCourses(int studentId) throws SQLException {
        postgresConn dbCon = new postgresConn();
        Connection conn =  null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isRegistered = false;
        try{
            conn = dbCon.getConnection();
            if (conn != null){
                String sql = "SELECT 1 FROM campus.semester1 where student_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1,studentId);
                rs = stmt.executeQuery();
                if (rs.next())
                    isRegistered = true;
            }
        } catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
            postgresConn.close(conn, stmt, rs);
        }
		return isRegistered;
    }

}


