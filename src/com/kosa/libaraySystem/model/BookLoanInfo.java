package com.kosa.libaraySystem.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;

public class BookLoanInfo {
    int loanNo;
    int bookNo;
    int userNo;
    Date loanDate;
    Date returnDate;

    public int getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(int loanNo) {
        this.loanNo = loanNo;
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

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BookLoanInfo(int loanNo, int bookNo, int userNo, Date loanDate, Date returnDate) {
        this.loanNo = loanNo;
        this.bookNo = bookNo;
        this.userNo = userNo;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public BookLoanInfo() {
    }
}
