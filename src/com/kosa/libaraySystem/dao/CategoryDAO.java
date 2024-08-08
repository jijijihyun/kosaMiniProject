package com.kosa.libaraySystem.dao;

import com.kosa.libaraySystem.config.DBUtils;
import com.kosa.libaraySystem.model.Category;

import java.sql.*;
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

    public Category getCategoryClassByNameSelect(String name) throws SQLException {
        String sql = "SELECT * FROM categories WHERE name = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);

            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return new Category(rs.getInt("categoryNo"),
                            rs.getString("name"),
                            rs.getObject("parentNo") != null ? rs.getInt("parentNo") : null);
                } else {
                    return null;
                }
            }
        }
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
                c.setParentNo(resultSet.getObject("parentNo") != null ? resultSet.getInt("parentNo") : null);
            }
        }
        return c;
    }

    // =======================================================================================
    public boolean categoryNoCheck(String categoryName) throws SQLException {
        String sql = "SELECT 1 FROM categories WHERE name = ? LIMIT 1";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, categoryName);

            try(ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int getCategoryNoByName(String categoryName) throws SQLException {
        String sql = "SELECT categoryNo FROM categories WHERE name = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, categoryName);

            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt("categoryNo");
                } else {
                    throw new SQLException("카테고리가 존재하지 않습니다.");
                }
            }
        }
    }

    public List<Category> getCategoryHierarchy() throws SQLException {
        List<Category> categories = new ArrayList<>();

        String sql = "WITH RECURSIVE CategoryHierarchy AS ("
                + "    SELECT categoryNo, name, parentNo, CAST(name AS CHAR(1000)) AS path"
                + "    FROM Categories"
                + "    WHERE parentNo IS NULL"
                + "    UNION ALL"
                + "    SELECT cg.categoryNo, cg.name, cg.parentNo, CONCAT(ch.path, ' -> ', cg.name)"
                + "    FROM Categories cg"
                + "    INNER JOIN CategoryHierarchy ch ON cg.parentNo = ch.categoryNo"
                + ")"
                + "SELECT categoryNo, name, parentNo, path FROM CategoryHierarchy";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Integer categoryNo = rs.getInt("categoryNo");
                String name = rs.getString("name");
                Integer parentNo = rs.getInt("parentNo");
                String path = rs.getString("path");

                categories.add(new Category(categoryNo, name, parentNo, path));
            }
        }

        return categories;
    }

    public void addCategory(String name, Integer parentNo) throws SQLException {
        String sql = "INSERT INTO categories (name, parentNo) "
                + "VALUES (?, ?)";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            if(parentNo == null) {
                pstmt.setNull(2, Types.INTEGER);
            } else {
                pstmt.setInt(2, parentNo);
            }

            pstmt.executeUpdate();
        }
    }

    public boolean categoryNameCheck(String parentName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM categories WHERE name = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, parentName);

            try(ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public void updateCategory(Integer categoryNo, String newName, Integer newParentNo) throws SQLException {
        String sql = "UPDATE categories SET "
                + "name = ?, parentNo = ? "
                + "WHERE categoryNo = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            if(newParentNo == null) {
                pstmt.setInt(2, Types.INTEGER);
            } else {
                pstmt.setInt(2, newParentNo);
            }
            pstmt.setInt(3, categoryNo);
            pstmt.executeUpdate();
        }
    }

    public Category getCategoryByNo(Integer categoryNo) throws SQLException {
        String sql = "SELECT * FROM categories WHERE categoryNo = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryNo);

            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return new Category(rs.getInt("categoryNo"),
                            rs.getString("name"),
                            rs.getObject("parentNo") != null ? rs.getInt("parentNo") : null);
                } else {
                    return null;
                }
            }
        }
    }

    public boolean hasSubCategories(Integer categoryNo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM categories WHERE parentNo = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryNo);

            try(ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1 ) > 0;
            }
        }
    }

    public void deleteCategory(Integer categoryNo) throws SQLException {
        String sql = "DELETE FROM categories WHERE categoryNo = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryNo);
            pstmt.executeUpdate();
        }
    }

    public String getCategoryNameByNo(Integer parentNo) throws SQLException {
        if(parentNo == 0) {
            return "-";
        }

        String sql = "SELECT name FROM categories WHERE categoryNo = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, parentNo);

            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    return rs.getString("name");
                } else {
                    return "-";
                }
            }
        }
    }

    public boolean isCategoryValid(Integer categoryNo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM categories WHERE categoryNo = ?";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryNo);

            try(ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1 ) > 0;
            }
        }
    }
}
