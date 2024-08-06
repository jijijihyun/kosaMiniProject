package com.kosa.libaraySystem.service;

import com.kosa.libaraySystem.model.Category;
import com.kosa.libaraySystem.util.TupleKNY;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    public String getCategoryNameByNum(int cateNum);

    public List<Category> getSubCategoriesByParentNum(int parentNo);
    public Category getCategoryByName(String name);
    Category getCategoryByCategoryNo(int num)throws SQLException;
    public TupleKNY<String, String> getHierarchyCategory(Category c);
}
