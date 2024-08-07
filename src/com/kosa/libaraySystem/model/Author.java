package com.kosa.libaraySystem.model;


public class Author {
    private int authorNo;
    private String authorName;

    public Author(int authorNo, String authorName) {
        this.authorNo = authorNo;
        this.authorName = authorName;
    }

    public Author() {

    }

    public int getAuthorNo() {
        return authorNo;
    }

    public void setAuthorNo(int authorNo) {
        this.authorNo = authorNo;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
