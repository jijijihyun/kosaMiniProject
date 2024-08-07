package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;
import com.kosa.libaraySystem.model.Book;
import com.kosa.libaraySystem.model.BookLoanInfo;
import com.kosa.libaraySystem.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                System.out.printf("\n==== %s 대출 완료 =====\n", book.getTitle());
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
        }
    }

    public List<BookLoanInfo> selectDataByUser(User user) throws SQLException{
        List<BookLoanInfo> results = new ArrayList<>();

        String sql = "SELECT * FROM bookloans l WHERE l.userNo = ? AND l.returnDate IS NULL";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement p = conn.prepareStatement(sql)){
            p.setInt(1, user.getUserNo());

            ResultSet rs = p.executeQuery();

            while(rs.next()){
                BookLoanInfo instance = new BookLoanInfo(
                        rs.getInt(1), rs.getInt(2),
                        rs.getInt(3), rs.getDate(4),
                        rs.getDate(5));
                results.add(instance);
            }
        }
        return results;
    }

    public void updateReturnDateByBookNo(int bookNo, int userNo) throws SQLException {
        String sql = "update bookloans set returnDate = ? where bookNo = ? and returnDate is null and userNo = ?";

        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement p = conn.prepareStatement(sql)){
            p.setDate(1, sqlDate);  p.setInt(2, bookNo);    p.setInt(3, userNo);
            int i = p.executeUpdate();
            
        }
    }
}
