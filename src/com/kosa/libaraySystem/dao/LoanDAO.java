package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;
import com.kosa.libaraySystem.model.Book;
import com.kosa.libaraySystem.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoanDAO {
    public void insertBookloansByBook(Book book, User user) throws SQLException {
        String sql = "INSERT INTO BookLoans (bookNo, userNo, loanDate, returnDate) VALUES (?, ?, ?, ?)";

        int bNo = book.getBookNo();
        int uNo = user.getUserNo();
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, bNo);
            ps.setInt(2, uNo);
            ps.setDate(3, sqlDate);
            ps.setDate(4, null);

            int i = ps.executeUpdate();
            if(i>0){
                System.out.println(i);
            }
        }
    }

    public void updateBookStatusbyBook(Book book, String status) throws SQLException {
        String query = "UPDATE Books SET status = ? WHERE bookNo = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, book.getBookNo());
            int i = pstmt.executeUpdate();

            if(i>0){
                System.out.println("도서 대출중 변경");
            }

        }
    }
}
