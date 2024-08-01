package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDAO {

    /* 작가 넘버를 가지고 있다면 작가 이름을 반환할수있도록 하는 매서드*/
    public String getAuthorNamebySelect(int authorNum) throws SQLException {
        String sql = "Select a.name from authors a where a.authorNo = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement preStat = conn.prepareStatement(sql)
        ){
            preStat.setInt(1, authorNum);
            ResultSet rs = preStat.executeQuery();

            if(rs.next()){
                return rs.getString(1);
            }
            else{
                return null;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
