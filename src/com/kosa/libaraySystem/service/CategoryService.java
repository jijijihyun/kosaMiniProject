package com.kosa.libaraySystem.service;

import com.kosa.libaraySystem.model.Category;
import com.kosa.libaraySystem.util.TupleKNY;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    public String getCategoryNameByNum(int cateNum);

    public List<Category> getSubCategoriesByParentNum(int parentNo);
    public Category getCategoryByName(String name) throws SQLException;
    Category getCategoryByCategoryNo(int num)throws SQLException;
    public TupleKNY<String, String> getHierarchyCategory(Category c);

    // =========================================================================
    // 전체 카테고리 검색(재귀적 CTE를 사용하여 계층적 구조를 보여준다.)
    List<Category> getCategoryHierarchy() throws SQLException;

    // 카테고리 추가
    void addCategory(String parentName, String name) throws SQLException;

    void updateCategory(Integer categoryNo, String newParentName, String newName) throws SQLException;

    void deleteCategory(Integer categoryNo) throws SQLException;

    Category getCategoryByNo(Integer categoryNo) throws SQLException;

    String getCategoryNameByNo(Integer parentNo) throws SQLException;

    boolean isCategoryValid(Integer categoryNo) throws SQLException;
}
