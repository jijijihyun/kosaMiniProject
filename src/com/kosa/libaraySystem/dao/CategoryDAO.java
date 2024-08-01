package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO {
    public String getCategoryNameByNumSelect(int CategoryNum){
        String sql = "Select c.name from categories c where c.categoryNum = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement preStat = conn.prepareStatement(sql)
        ){
            preStat.setInt(1, CategoryNum);
            ResultSet rs = preStat.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
            else{
                return null;
            }

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
