package com.kosa.libaraySystem.service.impl;

import com.kosa.libaraySystem.dao.CategoryDAO;
import com.kosa.libaraySystem.model.Category;
import com.kosa.libaraySystem.service.CategoryService;
import com.kosa.libaraySystem.util.TupleKNY;

import java.sql.SQLException;
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

    @Override
    public Category getCategoryByCategoryNo(int num) throws SQLException {
        return categoryDAO.selectCategoryByCategoryNo(num);
    }

    public TupleKNY<String, String> getHierarchyCategory(Category c){
        if(c.getParentNo() == 0){
            return new TupleKNY<String,String>(c.getName(), "-");
        }
        else{
            try{
                return new TupleKNY<String,String>(getCategoryByCategoryNo(c.getParentNo()).getName(), c.getName());
            }catch(Exception e){
                System.out.println("유효한 입력이 아닙니다.");
                return new TupleKNY<String,String>("-", "-");
            }
        }
    }
}
