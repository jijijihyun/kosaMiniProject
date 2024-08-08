package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;
import com.kosa.libaraySystem.model.Author;
import com.kosa.libaraySystem.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    /* 작가 넘버를 가지고 있다면 작가 이름을 반환할수있도록 하는 매서드*/
    public String getAuthorNamebySelect(int authorNum) throws SQLException {
        String sql = "Select a.name from authors a where a.authorNo = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql)
        ) {
            preStat.setInt(1, authorNum);
            ResultSet rs = preStat.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 저자 이름 받아서 새로 추가 하는  sql
    public void insertByAuthor(String authorName) {
        String sql = " INSERT INTO Authors (name) VALUES ( ? ) ";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql)) {

            preStat.setString(1, authorName);

            preStat.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 저자 번호를 입력받은 후 저자 이름을 수정 하는 sql
    public void updateByAuthor(int authorNo, String authorName) {
        String sql = "UPDATE authors " +
                " SET name = ?" +
                " where authorNo = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql)) {

            preStat.setString(1, authorName);
            preStat.setInt(2, authorNo);

            preStat.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 저자 번호로 삭제 하는  sql
    public void deleteByAuthor(int authorNo) throws SQLException {
        String sql = " delete from authors where authorNo = ? ";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql)) {

            preStat.setInt(1, authorNo);

            preStat.executeUpdate();

        }
    }

    // 모든 저자 정보 SELECT
    public List<Author> findAuthorByAll() {
        List<Author> list = new ArrayList<>();
        String sql = "SELECT * FROM authors ";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Author author = new Author();


                author.setAuthorNo(rs.getInt("authorNo"));
                author.setAuthorName(rs.getString("name"));


                list.add(author);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 받은 정보가 포함 돤 저자 정보 SELECT
    public List<Author> findAuthorByName(String authorName) {

        List<Author> list = new ArrayList<>();
        String sql = "SELECT * FROM authors WHERE name like ? ";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + authorName + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Author author = new Author();


                author.setAuthorNo(rs.getInt("authorNo"));
                author.setAuthorName(rs.getString("name"));


                list.add(author);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
