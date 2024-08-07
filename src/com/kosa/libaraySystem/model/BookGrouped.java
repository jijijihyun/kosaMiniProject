package com.kosa.libaraySystem.model;

import com.kosa.libaraySystem.service.CategoryService;
import com.kosa.libaraySystem.service.impl.CategoryServiceImpl;
import com.kosa.libaraySystem.util.TupleKNY;

import java.sql.SQLException;
import java.util.List;

public class BookGrouped {
    private Book book;
    private int cnt;
    private String bookTitle;
    private String authorName;
    private String categoryName;
    private String publisherName;

    public BookGrouped() {

    }

    public BookGrouped(Book book, int cnt, String bookTitle, String authorName, String categoryName, String publisherName) {
        this.book = book;
        this.cnt = cnt;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.publisherName = publisherName;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    //문자열 길이 포맷팅
    private String formatString(String str, int maxLength) {
        return str.length() > maxLength ? str.substring(0, maxLength - 3) + "..." : str;
    }

    //단순 반복코드 도서 출력부로직만 따로
    public void showBookGroupedInfoUser() throws SQLException {
        CategoryService categoryService = new CategoryServiceImpl();
        TupleKNY<String,String> categoriesName =
            categoryService.getHierarchyCategory(categoryService.getCategoryByName(this.getCategoryName()));
        String bigCateName = categoriesName.getKey();
        String smallCateName = categoriesName.getValue();
        System.out.printf("|%-40s|%-10s|%-15s|%-15s|%-10s|%5d|\n",
                this.getBookTitle(),
                formatString(this.getAuthorName(), 10),
                formatString(bigCateName, 15),
                formatString(smallCateName, 15),
                formatString(this.getPublisherName(), 10),
                this.getCnt()
                );
    }
}
