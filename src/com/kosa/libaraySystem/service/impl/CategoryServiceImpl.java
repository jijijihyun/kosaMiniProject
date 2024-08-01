package com.kosa.libaraySystem.service.impl;

import com.kosa.libaraySystem.dao.CategoryDAO;
import com.kosa.libaraySystem.service.CategoryService;
import jdk.jfr.Category;

public class CategoryServiceImpl implements CategoryService {
    CategoryDAO categoryDAO = new CategoryDAO();
    @Override
    public String getCategoryNameByNum(int cateNum) {
        return categoryDAO.getCategoryNameByNumSelect(cateNum);
    }
}
