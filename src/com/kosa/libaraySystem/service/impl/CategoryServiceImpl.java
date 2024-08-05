package com.kosa.libaraySystem.service.impl;

import com.kosa.libaraySystem.dao.CategoryDAO;
import com.kosa.libaraySystem.model.Category;
import com.kosa.libaraySystem.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    CategoryDAO categoryDAO = new CategoryDAO();
    @Override
    public String getCategoryNameByNum(int cateNum) {
        return categoryDAO.getCategoryNameByNumSelect(cateNum);
    }

    @Override
    public List<Category> getSubCategoriesByParentNum(int parentNo) {
        return categoryDAO.getSubCategoriesBySelectParenNum(parentNo);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryDAO.getCategoryClassByNameSelect(name);
    }
}
