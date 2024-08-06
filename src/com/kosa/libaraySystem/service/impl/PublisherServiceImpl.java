package com.kosa.libaraySystem.service.impl;

import com.kosa.libaraySystem.dao.PublisherDAO;
import com.kosa.libaraySystem.model.Publisher;
import com.kosa.libaraySystem.service.PublisherService;

import java.sql.SQLException;

public class PublisherServiceImpl implements PublisherService{

    PublisherDAO publisherDAO = new PublisherDAO();

    @Override
    public String getPublisherNameByNum(int publisherNo) throws SQLException {


        return publisherDAO.getPublisherNameByNumSelect(publisherNo);
    }


}
