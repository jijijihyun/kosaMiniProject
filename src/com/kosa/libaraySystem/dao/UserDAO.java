package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;
import com.kosa.libaraySystem.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public User getUserById(String userId) throws SQLException {
        String sql = "SELECT * FROM Users WHERE userId = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("username"), rs.getString("email"), rs.getInt("roleNo"));
            }

        }

        return null;
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (userId, password, username, email, roleNo) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getEmail());
            pstmt.setInt(5, user.getRoleNo());
            pstmt.executeUpdate();
        }
    }
}
