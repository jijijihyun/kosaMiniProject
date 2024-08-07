package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;
import com.kosa.libaraySystem.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public String getCategoryNameByNumSelect(int CategoryNum){
        String sql = "Select c.name from categories c where c.categoryNo = ?";

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
    public List<Category> getSubCategoriesBySelectParenNum(int parentNo) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories WHERE parentNo = ?";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sql)) {

            preStat.setInt(1, parentNo);
            ResultSet resultSet = preStat.executeQuery();

            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryNo(resultSet.getInt("categoryNo"));
                category.setName(resultSet.getString("name"));
                category.setParentNo(resultSet.getInt("parentNo"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public Category getCategoryClassByNameSelect(String name) {
        Category c = new Category();
        String sql = "SELECT c.categoryNo, c.name, c.parentNo FROM Categories c WHERE name = ?";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sql)) {

            preStat.setString(1, name);
            ResultSet resultSet = preStat.executeQuery();

            if (resultSet.next()) {
                c.setCategoryNo(resultSet.getInt("categoryNo"));
                c.setName(resultSet.getString("name"));
                c.setParentNo(resultSet.getInt("parentNo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public Category selectCategoryByCategoryNo(int num) throws SQLException {
        String sql = "SELECT * FROM Categories c WHERE c.categoryNo = ?";
        Category c = new Category();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preStat = connection.prepareStatement(sql)) {

            preStat.setInt(1, num);
            ResultSet resultSet = preStat.executeQuery();

            if (resultSet.next()) {
                c.setCategoryNo(resultSet.getInt("categoryNo"));
                c.setName(resultSet.getString("name"));
                c.setParentNo(resultSet.getInt("parentNo"));
            }
        }
        return c;
    }
}
