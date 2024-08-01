package com.kosa.libaraySystem.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Publisher {

    int publisherNo;
    String name;

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
