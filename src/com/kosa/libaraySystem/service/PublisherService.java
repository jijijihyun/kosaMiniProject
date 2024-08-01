package com.kosa.libaraySystem.service;

import java.sql.SQLException;

public interface PublisherService{
    public String getPublisherNameByNum(int publisherNo)throws SQLException;
}
