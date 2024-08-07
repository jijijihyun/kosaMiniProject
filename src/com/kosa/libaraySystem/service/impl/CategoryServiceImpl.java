package com.kosa.libaraySystem.service.impl;

import com.kosa.libaraySystem.dao.CategoryDAO;
import com.kosa.libaraySystem.model.Category;
import com.kosa.libaraySystem.service.CategoryService;
import com.kosa.libaraySystem.util.TupleKNY;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDAO categoryDAO = new CategoryDAO();
    @Override
    public String getCategoryNameByNum(int cateNum) {
        return categoryDAO.getCategoryNameByNumSelect(cateNum);
    }

    @Override
    public List<Category> getSubCategoriesByParentNum(int parentNo) {
        return categoryDAO.getSubCategoriesBySelectParenNum(parentNo);
    }

    @Override
    public Category getCategoryByName(String name) throws SQLException {
        return categoryDAO.getCategoryClassByNameSelect(name);
    }

    @Override
    public Category getCategoryByCategoryNo(int num) throws SQLException {
        return categoryDAO.selectCategoryByCategoryNo(num);
    }

    public TupleKNY<String, String> getHierarchyCategory(Category c){

        if(c.getParentNo()==null){

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

    // ====================================================================================
    @Override
    public List<Category> getCategoryHierarchy() throws SQLException {
        return categoryDAO.getCategoryHierarchy();
    }

    @Override
    public void addCategory(String parentName, String name) throws SQLException {
        Integer parentNo = null;

        if(parentName != null && !parentName.isEmpty()) {
            if(!categoryDAO.categoryNameCheck(parentName)) {
                throw new IllegalArgumentException("상위 카테고리가 존재하지 않습니다.");
            }

            parentNo = categoryDAO.getCategoryNoByName(parentName);
        }

        categoryDAO.addCategory(name, parentNo);
    }

    @Override
    public void updateCategory(Integer categoryNo, String newParentName, String newName) throws SQLException {
        Integer newParentNo = null;

        if(newParentName != null && !newParentName.isEmpty()) {
            if(!categoryDAO.categoryNameCheck(newParentName)) {
                throw new IllegalArgumentException("상위 카테고리가 존재하지 않습니다.");
            }

            newParentNo = categoryDAO.getCategoryNoByName(newParentName);
        }

        categoryDAO.updateCategory(categoryNo, newName, newParentNo);
    }

    @Override
    public void deleteCategory(Integer categoryNo) throws SQLException {
        Category category = categoryDAO.getCategoryByNo(categoryNo);

        if(categoryDAO.hasSubCategories(categoryNo)) {
            throw new IllegalArgumentException("하위 카테고리가 존재하여 삭제할 수 없습니다.");
        }

        categoryDAO.deleteCategory(categoryNo);
    }

    @Override
    public Category getCategoryByNo(Integer categoryNo) throws SQLException {
        if(categoryNo == null) {
            throw new NullPointerException("존재하지 않는 카테고리 번호입니다.");
        }
        return categoryDAO.getCategoryByNo(categoryNo);
    }

    @Override
    public String getCategoryNameByNo(Integer parentNo) throws SQLException {
        return categoryDAO.getCategoryNameByNo(parentNo);
    }

    @Override
    public boolean isCategoryValid(Integer categoryNo) throws SQLException {
        return categoryDAO.isCategoryValid(categoryNo);
    }
}
