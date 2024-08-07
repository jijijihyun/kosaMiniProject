package com.kosa.libaraySystem.service;

import com.kosa.libaraySystem.model.Author;

import java.sql.SQLException;
import java.util.List;

public interface AuthorService {
    public String getAuthorNameByNum(int authorNum) throws SQLException;

    public void addAuthorName(String authorName);

    public void deletByAuthor(int authorNum);

    public List<Author> findAuthorByAll();

    public List<Author> findAuthorByName(String authorName);

    public void updateByAuthorName(int authorNo, String authorName);
}


