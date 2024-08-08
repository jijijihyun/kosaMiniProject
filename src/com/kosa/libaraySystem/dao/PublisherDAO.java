package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;
import com.kosa.libaraySystem.model.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAO {
    public String getPublisherNameByNumSelect(int publisherNum){
        String sql = "Select p.name from publishers p where p.publisherNo = ? ";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement preState = conn.prepareStatement(sql)
        ) {
            preState.setInt(1,publisherNum);
            ResultSet rs = preState.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
            else{
                return null;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    // 춮판사 이름 받아서 새로 추가 하는  sql
    public void insertByPublisher(String publisherName) {
        String sql = " INSERT INTO Publishers (name) VALUES ( ? ) ";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql)) {

            preStat.setString(1, publisherName);

            preStat.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 저자 번호를 입력받은 후 저자 이름을 수정 하는 sql
    public void updateByPublisher(int publisherNo, String publisherName) {
        String sql = "UPDATE Publishers " +
                " SET name = ?" +
                " where publisherNo = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql)) {

            preStat.setString(1, publisherName);
            preStat.setInt(2, publisherNo);

            preStat.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 저자 번호로 삭제 하는  sql
    public void deleteByPublisher(int publisherNo) throws SQLException {
        String sql = " delete from Publishers where publisherNo = ? ";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preStat = conn.prepareStatement(sql)) {

            preStat.setInt(1, publisherNo);

            preStat.executeUpdate();

        }
    }

    // 모든 저자 정보 SELECT
    public List<Publisher> findPublisherByAll() {
        List<Publisher> list = new ArrayList<>();
        String sql = "SELECT * FROM Publishers ";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Publisher publisher = new Publisher();


                publisher.setPublisherNo(rs.getInt("publisherNo"));
                publisher.setName(rs.getString("name"));


                list.add(publisher);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 받은 정보가 포함 돤 저자 정보 SELECT
    public List<Publisher> findPublisherByName(String publisherName) {

        List<Publisher> list = new ArrayList<>();
        String sql = "SELECT * FROM Publishers WHERE name like ? ";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + publisherName + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Publisher publisher = new Publisher();


                publisher.setPublisherNo(rs.getInt("publisherNo"));
                publisher.setName(rs.getString("name"));


                list.add(publisher);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }





}
