package com.kosa.libaraySystem.model;


import lombok.*;

public class Book {

    private int bookNo;
    private String title;
    private int authorNo;
    private int publisherNo;
    private int categoryNo;
    private String status;

    public Book() {

    }

    public Book(int bookNo, String title, int authorNo, int publisherNo, int categoryNo, String status) {
        this.bookNo = bookNo;
        this.title = title;
        this.authorNo = authorNo;
        this.publisherNo = publisherNo;
        this.categoryNo = categoryNo;
        this.status = status;
    }

    public int getBookNo() {
        return bookNo;
    }

    public void setBookNo(int bookNo) {
        this.bookNo = bookNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorNo() {
        return authorNo;
    }

    public void setAuthorNo(int authorNo) {
        this.authorNo = authorNo;
    }

    public int getPublisherNo() {
        return publisherNo;
    }

    public void setPublisherNo(int publisherNo) {
        this.publisherNo = publisherNo;
    }

    public int getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(int categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

