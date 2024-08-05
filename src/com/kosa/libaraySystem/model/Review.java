package com.kosa.libaraySystem.model;

import java.sql.Date;

public class Review {
    int reviewNo;
    int bookNo;
    int userNo;
    String reviewText;
    Date reviewDate;

    public int getReviewNo() {
        return reviewNo;
    }

    public void setReviewNo(int reviewNo) {
        this.reviewNo = reviewNo;
    }

    public int getBookNo() {
        return bookNo;
    }

    public void setBookNo(int bookNo) {
        this.bookNo = bookNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Review() {
    }

    public Review(int reviewNo, int bookNo, int userNo, String reviewText, Date reviewDate) {
        this.reviewNo = reviewNo;
        this.bookNo = bookNo;
        this.userNo = userNo;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
    }

}
