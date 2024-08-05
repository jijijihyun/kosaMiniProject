package com.kosa.libaraySystem.service;

import com.kosa.libaraySystem.model.Author;
import com.kosa.libaraySystem.model.Book;
import com.kosa.libaraySystem.model.BookGrouped;

import java.sql.SQLException;
import java.util.List;

public interface BookService {

    public int authorSearch (String a);
    public void addBook(String btitle, int authorNo, int publisherNo, int categoryNo);
    int categorySearch(String bcategory);
    int publisherSearch(String bpublisher);
    void deleteBookByTitle(String title);
    Book searchBookByTitle(String title); // Add this method
    void updateBook(String oldTitle, String newTitle, int authorNo, int publisherNo, int categoryNo); // Add this method


    /*
    ==============유저쪽 추가==================
     */
    public List<Book> getBooksSearchTitle(String bookTitle)throws SQLException;
    public List<BookGrouped> getBookGroupedSearchTitle(String bookTitle) throws SQLException;

    List<BookGrouped> getBookGroupedSearchByAuthorName(String authorName) throws SQLException;

    List<BookGrouped> getBookGroupedSearchByPublisherName(String pubName)throws SQLException;

    List<BookGrouped> getBookGroupedSearchByCategoryNum(int categoryNo);
}
