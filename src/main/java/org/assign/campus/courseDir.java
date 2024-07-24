package org.assign.campus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class courseDir {

    public static String getRegNobyEmail(String email) throws SQLException{
        postgresConn dbConn = new postgresConn();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String regNumber = null;
        try {
            conn = dbConn.getConnection();
            if(conn != null){
                String sql = "SELECT \"regNumber\" FROM campus.student WHERE id = (SELECT id FROM campus.campus WHERE email = ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);
                rs = pstmt.executeQuery();

                if(rs.next())  regNumber = rs.getString("regNumber");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            postgresConn.close(conn, pstmt, rs);
        }
        return regNumber;
    }


    public static int getStudentIdByRegNo(String regNumber) throws SQLException{
        postgresConn dbConn = new postgresConn();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int studentId = -1;
        try {
            conn = dbConn.getConnection();
            if(conn != null){
                String sql = "SELECT id FROM campus.student WHERE \"regNumber\" = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, regNumber);
                rs = pstmt.executeQuery();

                if(rs.next())  studentId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            postgresConn.close(conn, pstmt, rs);
        }
        return studentId;
    }

    public static void registerCourses(int studentId, List<String> selectedCourses) throws SQLException {
        postgresConn dbConn = new postgresConn();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            conn = dbConn.getConnection();
            if(conn != null) {
                String sql = "INSERT INTO campus.semester1(unit1, unit2, unit3, unit4, unit5, student_id) VALUES(?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);

                for (int i = 0; i < selectedCourses.size(); i++){
                    stmt.setString(i + 1, selectedCourses.get(i));
                    stmt.addBatch();
                }
                stmt.setInt(6, studentId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            postgresConn.close(conn, stmt, rs);
        }
    }
}
