package com.kosa.libaraySystem.service.impl;

import com.kosa.libaraySystem.dao.PublisherDAO;
import com.kosa.libaraySystem.model.Publisher;
import com.kosa.libaraySystem.service.PublisherService;

import java.sql.SQLException;
import java.util.List;

public class PublisherServiceImpl implements PublisherService{


    PublisherDAO publisherDAO = new PublisherDAO();

    @Override
    public void addPublisherName(String publisherNoName) {

        publisherDAO.insertByPublisher(publisherNoName);
    }

    @Override
    public void deletByPublisher(int publisherNo) throws SQLException {

        publisherDAO.deleteByPublisher(publisherNo);
    }

    @Override
    public List<Publisher> findPublisherByAll() {
        return publisherDAO.findPublisherByAll();
    }

    @Override
    public List<Publisher> findPublisherByName(String publisherNoName) {
        return publisherDAO.findPublisherByName(publisherNoName);
    }

    @Override
    public void updateByPublisherName(int publisherNo, String publisherNoName) {

        publisherDAO.updateByPublisher(publisherNo,publisherNoName);
    }

    @Override
    public String getPublisherNameByNum(int publisherNo) throws SQLException {


        return publisherDAO.getPublisherNameByNumSelect(publisherNo);
    }


}
