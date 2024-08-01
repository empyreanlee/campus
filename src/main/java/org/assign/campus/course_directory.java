package org.assign.campus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class course_directory {
	static postgresConn dbConn = new postgresConn();
	static Connection conn;
	static ResultSet rs;
	static PreparedStatement pstmt;

	private static final String SCHEMA_NAME = "campus";

	public static String getRegNobyEmail(String email) throws SQLException {
		dbConn = new postgresConn();
		conn = null;pstmt = null;rs = null;
		String regNumber = null;
		try {
			conn = dbConn.getConnection();
			if (conn != null) {
				String sql = "SELECT s.reg_number FROM campus.student s "+
						"JOIN campus.campus c ON s.details_id = c.id WHERE c.email = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, email);
				rs = pstmt.executeQuery();

				if (rs.next()) regNumber = rs.getString("reg_number");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			postgresConn.close(conn, pstmt, rs);
		}
		return regNumber;
	}


	public static int getStudentIdByRegNo(String regNumber) throws SQLException {
		dbConn = new postgresConn();
		conn = null;
		pstmt = null;
		rs = null;
		int studentId = -1;
		try {
			conn = dbConn.getConnection();
			if (conn != null) {
				String sql = "SELECT id FROM campus.student WHERE \"reg_number\" = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, regNumber);
				rs = pstmt.executeQuery();

				if (rs.next()) studentId = rs.getInt("id");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			postgresConn.close(conn, pstmt, rs);
		}
		return studentId;
	}

	public static void registerCourses(int studentId, List<String> selectedCourses, List<String> selectedCourses2) throws SQLException {
		dbConn = new postgresConn();
		conn = null;pstmt = null;
		PreparedStatement stmt2 = null;
		rs = null;
		try {
			conn = dbConn.getConnection();
			if (conn != null) {
				String sql = "INSERT INTO campus.semester1(unit1, unit2, unit3, unit4, unit5, student_id) VALUES(?,?,?,?,?,?)";
				String sql2 = "INSERT INTO campus.semester2(unit1,unit2,unit3,unit4,unit5,student_id2) VALUES(?,?,?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				stmt2 = conn.prepareStatement(sql2);

				for (int i = 0; i < selectedCourses.size(); i++) {
					pstmt.setString(i + 1, selectedCourses.get(i));
					pstmt.addBatch();
				}
				pstmt.setInt(6, studentId);
				pstmt.executeUpdate();
				for (int i = 0; i < selectedCourses2.size(); i++) {
					stmt2.setString(i + 1, selectedCourses2.get(i));
				}
				stmt2.setInt(6, studentId);
				stmt2.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			postgresConn.close(conn, pstmt, null);
			postgresConn.close(null, stmt2, null);
		}
	}
	public static int getDetailsIdById(String email) throws SQLException {
		dbConn = new postgresConn();
		conn = null;pstmt = null;rs = null;
		int detailsId = -1;
		try {
			conn = dbConn.getConnection();
			if (conn != null) {
				String sql = "SELECT id FROM campus.campus WHERE email = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, email);
				rs = pstmt.executeQuery();

				if (rs.next()) detailsId = rs.getInt("id");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			postgresConn.close(conn, pstmt, rs);
		}
		return detailsId;
	}

	public static String getNameByRegNo(String regNumber) throws SQLException {
		dbConn = new postgresConn();
		conn = null;pstmt = null;rs = null;
		String name = null;
		try {
			conn = dbConn.getConnection();
			if (conn != null) {
				String sql = "SELECT s.name FROM campus.campus s "+
						"JOIN campus.student c ON s.id = c.details_id WHERE c.reg_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, regNumber);
				rs = pstmt.executeQuery();

				if (rs.next()) name = rs.getString("name");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			postgresConn.close(conn, pstmt, rs);
		}
		return name;
	}
	public static void insertMarks(int studentId, List<Integer> marksList, Map<Integer, String> marksMap) throws SQLException {
		dbConn = new postgresConn();
		conn = null;pstmt = null;rs = null;
		String name = null;
		try {
			conn = dbConn.getConnection();
			if (conn != null) {
				String sql = "INSERT INTO campus.marks(grades_id, assignments,cats,exam,total,grade,unit) VALUES(?,?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, studentId);
				for (int i : marksList){
					pstmt.setInt(i + 1, marksList.get(i));
					pstmt.addBatch();
				}
				pstmt.setString(5, String.valueOf(marksMap.values()));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public static List<String> getCourseList(String regNumber) throws SQLException {
		int studentId = getDetailsIdById(regNumber);
		dbConn = new postgresConn();
		conn = null;
		pstmt = null;
		rs = null;
		List<String> courses;
		try {
			conn = dbConn.getConnection();
			String sql = "SELECT unit1, unit2, unit3, unit4, unit5 FROM campus.semester1  WHERE studentId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, studentId);
			rs = pstmt.executeQuery();
			courses = new ArrayList<>();
			if (rs.next()) {
				courses.add(rs.getString("unit1"));
				courses.add(rs.getString("unit2"));
				courses.add(rs.getString("unit3"));
				courses.add(rs.getString("unit4"));
				courses.add(rs.getString("unit5"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return courses;
	}
}
