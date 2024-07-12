package org.assign.campus;


import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class directory {
    interface PasswordUtil{
        String encryptPassword(String password);
        Boolean checkPassword(String plainPassword, String hashedPassword);
    }
    public static class BCryptPasswordUtil implements PasswordUtil{

        public String encryptPassword(String password) {
            return BCrypt.hashpw(password, BCrypt.gensalt(12));
        }
        public Boolean checkPassword(String plainPassword, String hashedPassword){
            return BCrypt.checkpw(plainPassword,hashedPassword);
        }
    }

    public static void insertUser(String nameval, String emailval, String passwordval, String cpassword) throws  SQLException {
        if (!passwordval.equals(cpassword))
            throw new IllegalArgumentException("Passwords do not match!");

        BCryptPasswordUtil passwordUtil = new BCryptPasswordUtil();
        String hashedPassword = passwordUtil.encryptPassword(passwordval);

        postgresConn dbconn = new postgresConn();
        Connection conn = dbconn.getConnection();
        if(conn!=null) {
            String sql = "INSERT INTO campus.campus (name,email,password,cpassword) VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameval);
            stmt.setString(2, emailval);
            stmt.setString(3, hashedPassword);
            stmt.setString(4, hashedPassword);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } else{
            System.err.println("Error: connection is null");
        }
    }
}
