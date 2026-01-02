/*
package dao;

import db.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // Register new user
    public void register(User user) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO users(username, password, role) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());

            ps.executeUpdate();
            System.out.println("User registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Login user
    public boolean login(String username, String password) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
*/
package dao;

import db.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // REGISTER USER
    public void register(User user) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getUsername().trim());
            ps.setString(2, user.getPassword().trim());
            ps.setString(3, user.getRole());

            ps.executeUpdate();
            System.out.println("User registered successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // LOGIN WITH ROLE
    public String loginWithRole(String username, String password) {
        try {
            Connection con = DBConnection.getConnection();

            // DEBUG: print what Java is actually sending
            System.out.println("DEBUG username=[" + username + "]");
            System.out.println("DEBUG password=[" + password + "]");

            String sql = "SELECT username, password, role FROM users";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("---- USERS IN DB ----");
            while (rs.next()) {
                System.out.println(
                        rs.getString("username") + " | " +
                                rs.getString("password") + " | " +
                                rs.getString("role")
                );
            }
            System.out.println("--------------------");

            // REAL LOGIN CHECK
            sql = "SELECT role FROM users WHERE TRIM(username)=TRIM(?) AND TRIM(password)=TRIM(?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}