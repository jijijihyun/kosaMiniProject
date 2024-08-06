package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;
import com.kosa.libaraySystem.model.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
