package com.kosa.libaraySystem.service;

import com.kosa.libaraySystem.model.Category;

import java.util.List;

public interface CategoryService {
    public String getCategoryNameByNum(int cateNum);

    public List<Category> getSubCategoriesByParentNum(int parentNo);
    public Category getCategoryByName(String name);
}
