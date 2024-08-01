package com.kosa.libaraySystem.service.impl;

import com.kosa.libaraySystem.dao.AuthorDAO;
import com.kosa.libaraySystem.service.AuthorService;

import java.sql.SQLException;

public class AuthorServiceImpl implements AuthorService  {

    private AuthorDAO authorDAO = new AuthorDAO();

    @Override
    public String getAuthorNameByNum(int AuthorNum) throws SQLException {
        return authorDAO.getAuthorNamebySelect(AuthorNum);
    }
}
