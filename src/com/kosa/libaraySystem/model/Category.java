package com.kosa.libaraySystem.model;

public class Category {
    private Integer categoryNo;
    private String name;
    private Integer parentNo;
    private String path;

    public Category(Integer categoryNo, String name, Integer parentNo, String path) {
        this.categoryNo = categoryNo;
        this.name = name;
        this.parentNo = parentNo;
        this.path = path;
    }

    public Category(Integer categoryNo, String name, Integer parentNo) {
        this.categoryNo = categoryNo;
        this.name = name;
        this.parentNo = parentNo;
    }

    public Category() {

    }

    public Integer getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(Integer categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentNo() {
        return parentNo;
    }

    public void setParentNo(Integer parentNo) {
        this.parentNo = parentNo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
