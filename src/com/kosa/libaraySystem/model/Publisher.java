package com.kosa.libaraySystem.model;


public class Publisher {

    int publisherNo;
    String name;

    public Publisher(int publisherNo, String name) {
        this.publisherNo = publisherNo;
        this.name = name;
    }

    public Publisher() {

    }

    public int getPublisherNo() {
        return publisherNo;
    }

    public void setPublisherNo(int publisherNo) {
        this.publisherNo = publisherNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
