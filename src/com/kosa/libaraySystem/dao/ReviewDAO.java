package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;
import com.kosa.libaraySystem.model.User;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class ReviewDAO {

    public boolean isValidBook(String bookTitle) throws SQLException {
        String sql = "SELECT * FROM Books WHERE Title = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookTitle);

            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void insertReview(User user, String bookTitle, String content){
        System.out.println("리뷰 등록 쿼리 실행");
        java.util.Date utilDate = new java.util.Date();
        // java.util.Date를 java.sql.Date로 변환
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String sqlforBookno = "SELECT Books.bookNo FROM Books WHERE Title = ?";
        String sqlforUserno = "SELECT User.userNo FROM User WHERE userId = ?";
        String sqlforInsertReview = "Insert into reviews ( bookNo,userNo,reviewText,reviewDate) values("+
                "?, ?, ?, ?"
                +")";

        String sqlSQOneline = "Insert into reviews ( bookNo,userNo,reviewText,reviewDate) values("+
                "(SELECT Books.bookNo FROM Books WHERE Title = ?),"+
                "(SELECT Users.userNo FROM Users where userid=?),"+
                "?,?)";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlSQOneline)) {
            pstmt.setString(1, sqlforBookno);
            pstmt.setString(2, user.getUserId());
            pstmt.setString(3, content);
            pstmt.setDate(4, sqlDate);

            int num = pstmt.executeUpdate();

            if(num >= 1) {
                System.out.println(num);
                return;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return;
        }
    }


}