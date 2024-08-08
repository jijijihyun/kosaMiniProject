package com.kosa.libaraySystem.service.impl;

import com.kosa.libaraySystem.dao.AuthorDAO;
import com.kosa.libaraySystem.model.Author;
import com.kosa.libaraySystem.service.AuthorService;

import java.sql.SQLException;
import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private AuthorDAO authorDAO = new AuthorDAO();

    @Override
    public String getAuthorNameByNum(int AuthorNum) throws SQLException {
        return authorDAO.getAuthorNamebySelect(AuthorNum);
    }

    @Override
    public void addAuthorName(String authorName) {
        authorDAO.insertByAuthor(authorName);
    }

    @Override
    public void deletByAuthor(int AuthorNum) throws SQLException {
        authorDAO.deleteByAuthor(AuthorNum);
    }

    @Override
    public List<Author> findAuthorByAll() {
        return authorDAO.findAuthorByAll();
    }

    @Override
    public List<Author> findAuthorByName(String authorName) {
        return authorDAO.findAuthorByName(authorName);
    }

    @Override
    public void updateByAuthorName(int authorNo, String authorName) {
        authorDAO.updateByAuthor(authorNo, authorName);
    }


}
