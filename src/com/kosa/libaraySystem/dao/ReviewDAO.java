package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;
import com.kosa.libaraySystem.model.Review;
import com.kosa.libaraySystem.model.User;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

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

    public void insertReview(User user, int bookNo, String content, Date nowTime) throws SQLException {
        String sqlSQOneline = "INSERT INTO Reviews (bookNo, userNo, reviewText, reviewDate) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlSQOneline)) {
            pstmt.setInt(1, bookNo);
            pstmt.setInt(2, user.getUserNo());
            pstmt.setString(3, content);
            pstmt.setDate(4, nowTime);

            int num = pstmt.executeUpdate();
        }
    }

    //리뷰 리스트 뽑을거야
    public List<Review> selectReviewsByBookTitleAll(String BookTitle) throws SQLException {
        List<Review> reviews = new ArrayList<Review>();

        String sql = "Select r.reviewNo, r.bookNo, btitle.title, r.userNo, r.reviewText, r.reviewDate " +
                "from (Select b.title, b.bookNo from books b where b.title = ?) as btitle " +
                "Join reviews r on r.bookNo = btitle.bookNo";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, BookTitle);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Review r = new Review();
                r.setReviewNo(rs.getInt(1));
                r.setBookNo(rs.getInt(2));
                r.setUserNo(rs.getInt(4));
                r.setReviewText(rs.getString(5));
                r.setReviewDate(rs.getDate(6));

                reviews.add(r);
            }

        }
        return reviews;
    }

}