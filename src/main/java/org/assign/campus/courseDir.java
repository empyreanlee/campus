package org.assign.campus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class courseDir {

    public static void registerCourses(List<String> selectedCourses) throws SQLException {
        postgresConn dbConn = new postgresConn();
        Connection conn = null;
        PreparedStatement stmt = null;

        try{
            conn = dbConn.getConnection();
            if(conn != null){
                String sql1 = "SELECT * FROM campus.student WHERE regNumber = ?";
                String sql = "INSERT INTO campus.semester1(course_name, student_id) VALUES(?,?)";
                stmt = conn.prepareStatement(sql);

                for(String course: selectedCourses){
                    stmt.setString(1, course);
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}
