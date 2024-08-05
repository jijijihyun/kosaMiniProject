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

    // 작가 번호으로 각가 이름 찾는
    public String findNameByAuthorNo(int authorNo) {
        String sql = "SELECT name FROM Authors WHERE authorNo = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, authorNo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    // 출판사 번호로 출판사 이름 찾는
    public String findNAmeByPublisherNo(int publisherNO) {
        String sql = "SELECT * FROM Publishers WHERE publisherNO = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, publisherNO);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    // 카테고리 번호 로 카테고리 이름 찾는
    public String findCategoryNoByName(int categoryNo) {
        String sql = "SELECT name FROM Categories WHERE categoryNo = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryNo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    // 책 번호만 입력받아 바로 삭제
    public void deleteBookByTitle(int bookNo) {

        String sql = "DELETE FROM Books WHERE bookNo = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookNo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 타이틀 받아서
    public List<Book> findBookByTitle(String title) {

        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE title like ? ";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();

                book.setBookNo(rs.getInt("bookNo"));
                book.setTitle(rs.getString("title"));
                book.setAuthorNo(rs.getInt("authorNo"));
                book.setPublisherNo(rs.getInt("publisherNo"));
                book.setCategoryNo(rs.getInt("categoryNo"));

                list.add(book);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 북 넘버에 대한 제목을 변경
    public void updateBook(String newTitle, int authorNo, int publisherNo, int categoryNo, int bookNo) {

        String sql = "UPDATE Books " +
                "SET title = ?, authorNo = ?, publisherNo = ?, categoryNo = ? " +
                "WHERE bookNo = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, authorNo);
            pstmt.setInt(3, publisherNo);
            pstmt.setInt(4, categoryNo);
            pstmt.setInt(5, bookNo);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int findBooksNoByNo(int bookNo) {
        String sql = "SELECT * FROM books  WHERE bookNo = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookNo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("bookNo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /*
    ===============이하 유저에서 필요한 기능============
     */
    //북리스트 가져오는 쿼리
    public List<Book> getBookListSelectTitle(String title) throws SQLException {
        //일단 싹 긁어와 -> 북 리스트에 하나씩 다 넣어.
        //보여주는건 다른데서 처리
        String query = "SELECT * FROM Books b where b.title = ?";

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
                System.out.println(resultSet.getInt("bookNo"));
                book.setTitle(resultSet.getString("title"));
                System.out.println(resultSet.getString("title"));
                book.setAuthorNo(resultSet.getInt("authorNo"));
                System.out.println(resultSet.getInt("authorNo"));
                book.setPublisherNo(resultSet.getInt("publisherNo"));
                System.out.println(resultSet.getInt("publisherNo"));
                book.setCategoryNo(resultSet.getInt("categoryNo"));
                System.out.println(resultSet.getInt("categoryNo"));
                book.setStatus(resultSet.getString("status"));
                System.out.println(resultSet.getString("status"));
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
        String query = "SELECT bk.title, bkauthor.name, cate.name, pub.name, count(*) as cnt " +
                "FROM (SELECT b.title, b.authorNo, b.categoryNo, b.publisherNo from books b where b.title like ?) as bk " +
                "JOIN (SELECT a.authorNo, a.name from authors a) as bkauthor on bk.authorNo = bkauthor.authorNo " +
                "JOIN (SELECT c.categoryNo, c.name, c.parentNo from categories c) as cate on bk.categoryNo = cate.categoryNo " +
                "JOIN (SELECT p.publisherNo, p.name from publishers p) as pub on bk.publisherNo = pub.publisherNo " +
                "GROUP BY bk.title, bkauthor.name, cate.name, pub.name";

        List<BookGrouped> bookGroup = new ArrayList<>();

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preStat = conn.prepareStatement(query))
        //와우 트라이문 안에 STREAM 여러개를 열어도 되는구나 -> 예외처리를 한번에 트라이문 하나에서 처리
        {
            preStat.setString(1, '%'+bookTitle+'%');
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

    public List<BookGrouped> getBookGroupedListSelectAuthorName(String authorName) {
        String sql = "SELECT bk.title, bkauthor.name, cate.name, pub.name, count(*) as cnt " +
                "FROM (SELECT b.title, b.authorNo, b.categoryNo, b.publisherNo from books b) as bk " +
                "JOIN (SELECT a.authorNo, a.name from authors a where a.name like ?) as bkauthor on bk.authorNo = bkauthor.authorNo " +
                "JOIN (SELECT c.categoryNo, c.name, c.parentNo from categories c) as cate on bk.categoryNo = cate.categoryNo " +
                "JOIN (SELECT p.publisherNo, p.name from publishers p) as pub on bk.publisherNo = pub.publisherNo " +
                "GROUP BY bk.title, bkauthor.name, cate.name, pub.name";

        List<BookGrouped> bookGroup = new ArrayList<>();

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql))
        //와우 트라이문 안에 STREAM 여러개를 열어도 되는구나 -> 예외처리를 한번에 트라이문 하나에서 처리
        {
            preStat.setString(1, '%'+authorName+'%');
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

    public List<BookGrouped> getBookGroupedListSelectPublisherName(String pubName) {
        String sql = "SELECT bk.title, bkauthor.name, cate.name, pub.name, count(*) as cnt " +
                "FROM (SELECT b.title, b.authorNo, b.categoryNo, b.publisherNo from books b) as bk " +
                "JOIN (SELECT a.authorNo, a.name from authors a) as bkauthor on bk.authorNo = bkauthor.authorNo " +
                "JOIN (SELECT c.categoryNo, c.name, c.parentNo from categories c) as cate on bk.categoryNo = cate.categoryNo " +
                "JOIN (SELECT p.publisherNo, p.name from publishers p where p.name like ?) as pub on bk.publisherNo = pub.publisherNo " +
                "GROUP BY bk.title, bkauthor.name, cate.name, pub.name";

        List<BookGrouped> bookGroup = new ArrayList<>();

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql))
        //와우 트라이문 안에 STREAM 여러개를 열어도 되는구나 -> 예외처리를 한번에 트라이문 하나에서 처리
        {
            preStat.setString(1, '%'+pubName+'%');
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

    public List<BookGrouped> getBookGroupedListSelectCategoryNum(int categoryNo) {
        String sql = "SELECT bk.title, bkauthor.name, cate.name, pub.name, count(*) as cnt " +
                "FROM (SELECT b.title, b.authorNo, b.categoryNo, b.publisherNo from books b) as bk " +
                "JOIN (SELECT a.authorNo, a.name from authors a) as bkauthor on bk.authorNo = bkauthor.authorNo " +
                "JOIN (SELECT c.categoryNo, c.name, c.parentNo from categories c where c.categoryNo = ?) as cate on bk.categoryNo = cate.categoryNo " +
                "JOIN (SELECT p.publisherNo, p.name from publishers p) as pub on bk.publisherNo = pub.publisherNo " +
                "GROUP BY bk.title, bkauthor.name, cate.name, pub.name";

        List<BookGrouped> bookGroup = new ArrayList<>();

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql))
        //와우 트라이문 안에 STREAM 여러개를 열어도 되는구나 -> 예외처리를 한번에 트라이문 하나에서 처리
        {
            preStat.setInt(1, categoryNo);
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

    public Book selectBookByBookTitle(String title) throws SQLException{
        String sql = "Select * from books b where b.title = ?";
        Book b = new Book();

        try(Connection conn = DBUtils.getConnection();
        PreparedStatement p = conn.prepareStatement(sql)){
            p.setString(1, title);

            ResultSet rs = p.executeQuery();

            if(rs.next()){
                b.setTitle(rs.getString("title"));
                b.setAuthorNo(rs.getInt("authorNo"));
                b.setStatus(rs.getString("status"));
                b.setCategoryNo(rs.getInt("categoryNo"));
                b.setPublisherNo(rs.getInt("publisherNo"));
                b.setBookNo(rs.getInt("bookNo"));
            }
        }
        return b;
    }
}
