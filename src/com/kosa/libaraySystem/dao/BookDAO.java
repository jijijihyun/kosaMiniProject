package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;
import com.kosa.libaraySystem.model.Author;
import com.kosa.libaraySystem.model.Book;
import com.kosa.libaraySystem.model.BookGrouped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    
    // 작가 이름으로 각가 번호 찾는
        public int findAuthorNoByName(String authorName) {
        String sql = "SELECT authorNo FROM Authors WHERE name = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, authorName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("authorNo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 출판사 이름으로 출판사 번호 찾는
    public int findPublisherNoByName(String publisherName) {
        String sql = "SELECT publisherNo FROM Publishers WHERE name = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, publisherName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("publisherNo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 카테고리 이름으로 카테고리 번호 찾는
    public int findCategoryNoByName(String categoryName) {
        String sql = "SELECT categoryNo FROM Categories WHERE name = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, categoryName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("categoryNo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 입력한  정보로 찾은 번호(작가, 출판사, 카태고리)와 입력한 제목으로 북 태이블에 책 추가
    public void insertBook(String title, int authorNo, int publisherNo, int categoryNo) {
        String sql = "INSERT INTO Books (title, authorNo, publisherNo, categoryNo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setInt(2, authorNo);
            pstmt.setInt(3, publisherNo);
            pstmt.setInt(4, categoryNo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 책 제목만 입력받아 바로 삭제
    public void deleteBookByTitle(String title) {
        String sql = "DELETE FROM Books WHERE title = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 작가 이름으로 각가 번호 찾는
    public Book findBookByTitle(String title) {
            /* 20240801 추가
        String sql = "SELECT * FROM Books WHERE title = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setBookTitle(rs.getString("title"));
                book.setBookAuthorNo(rs.getInt("authorNo"));
                book.setBookPublisherNo(rs.getInt("publisherNo"));
                book.setBookCategoryNo(rs.getInt("categoryNo"));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */

        return null;
    }

    // 작가 이름으로 각가 번호 찾는
    public void updateBook(String oldTitle, String newTitle, int authorNo, int publisherNo, int categoryNo) {
        String sql = "UPDATE Books SET title = ?, authorNo = ?, publisherNo = ?, categoryNo = ? WHERE title = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, authorNo);
            pstmt.setInt(3, publisherNo);
            pstmt.setInt(4, categoryNo);
            pstmt.setString(5, oldTitle);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    ===============이하 유저에서 필요한 기능============
     */
    //북리스트 가져오는 쿼리
    public List<Book> getBookListSelectTitle(String title) throws SQLException {
        //일단 싹 긁어와 -> 북 리스트에 하나씩 다 넣어.
        //보여주는건 다른데서 처리
        String query = "SELECT " +
                "b.bookNo," +
                "b.title," +
                "b.authorNo," +
                "b.publisherNo," +
                "b.categoryNo" +
                "FROM Books b where b.title like '%?%'";

        List<Book> books = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preStat = connection.prepareStatement(query))
        //와우 트라이문 안에 STREAM 여러개를 열어도 되는구나 -> 예외처리를 한번에 트라이문 하나에서 처리
        {
            preStat.setString(1,  title);
            ResultSet resultSet = preStat.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setBookNo(resultSet.getInt("bookNo"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthorNo(resultSet.getInt("authorNo"));
                book.setPublisherNo(resultSet.getInt("publisherNo"));
                book.setCategoryNo(resultSet.getInt("categoryNo"));
                book.setStatus(resultSet.getString("status"));
                books.add(book);
            }
        }
        return books;
    }


    public void selectShowListBook(List<Book> lsBook ){

    }

    public List<BookGrouped> getBookGroupedListSelectTitle(String bookTitle) {
        //일단 싹 긁어와 -> 북 리스트에 하나씩 다 넣어.
        //보여주는건 다른데서 처리
        String query = "SELECT bk.title, bkauthor.name, cate.name, pub.name, count(*)" +
                "FROM (SELECT b.title, b.authorNo, b.categoryNo, b.publisherNo from books b where b.title like '%?%') as bk" +
                "join (SELECT a.authorNo, a.name from authors a) as bkauthor on bk.authorNo = bkauthor.authorNo" +
                "join (SELECT c.categoryNo, c.name, c.parentNo from categories c) as cate on bk.categoryNo = cate.categoryNo" +
                "join (SELECT publisherNo, p.name from publishers p) as pub on bk.publisherNo = pub.publisherNo" +
                "group by bk.title, bkauthor.name, cate.name, pub.name";

        List<BookGrouped> bookGroup = new ArrayList<>();

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preStat = conn.prepareStatement(query))
        //와우 트라이문 안에 STREAM 여러개를 열어도 되는구나 -> 예외처리를 한번에 트라이문 하나에서 처리
        {
            preStat.setString(1, bookTitle);
            ResultSet resultSet = preStat.executeQuery();

            while (resultSet.next()) {
                BookGrouped book = new BookGrouped();
                book.setBookTitle(resultSet.getString(1));
                book.setAuthorName(resultSet.getString(2));
                book.setCategoryName(resultSet.getString(3));
                book.setPublisherName(resultSet.getString(4));
                book.setCnt(resultSet.getInt(5));
                bookGroup.add(book);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return bookGroup;
    }
}
