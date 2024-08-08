package com.kosa.libaraySystem.service;

import com.kosa.libaraySystem.model.Publisher;

import java.sql.SQLException;
import java.util.List;

public interface PublisherService{
    public String getPublisherNameByNum(int publisherNo)throws SQLException;


    public void addPublisherName(String publisherNoName);

    public void deletByPublisher(int publisherNo) throws SQLException;

    public List<Publisher> findPublisherByAll();

    public List<Publisher> findPublisherByName(String publisherNoName);

    public void updateByPublisherName(int publisherNo, String publisherNoName);



}
