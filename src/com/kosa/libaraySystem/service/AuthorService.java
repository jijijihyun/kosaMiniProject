package com.kosa.libaraySystem.service;

import java.sql.SQLException;

public interface AuthorService {
    public String getAuthorNameByNum(int authorNum)throws SQLException;
}
