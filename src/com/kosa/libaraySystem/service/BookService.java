package com.kosa.libaraySystem.service;

import com.kosa.libaraySystem.model.Author;
import com.kosa.libaraySystem.model.Book;
import com.kosa.libaraySystem.model.BookGrouped;

import java.sql.SQLException;
import java.util.List;

public interface BookService {

    public int authorSearch(String a);

    public String reverseAuthorSearch(int a);
    public void addBook(String btitle, int authorNo, int publisherNo, int categoryNo);
    int categorySearch(String bcategory);
    String reverseCategorySearch(int bcategory);
    int publisherSearch(String bpublisher);

    String reversePublisherSearch(int bpublisher);

    void deleteBookByTitle(int bookNo);

    List<Book> searchBookByTitle(String title);
    
    void updateBook(String newTitle, int ano, int pno, int cno, int bookNo);

    int bNoSearch(int bNo);

    /*
    ==============유저쪽 추가==================
     */
    public List<Book> getBooksSearchTitle(String bookTitle)throws SQLException;
    public List<BookGrouped> getBookGroupedSearchTitle(String bookTitle) throws SQLException;

    List<BookGrouped> getBookGroupedSearchByAuthorName(String authorName) throws SQLException;

    List<BookGrouped> getBookGroupedSearchByPublisherName(String pubName)throws SQLException;

    List<BookGrouped> getBookGroupedSearchByCategoryNum(int categoryNo)throws SQLException;

    Book getBookSearchByTitle(String title)throws SQLException;

    public Book readDataByBookNum(int bookNo)throws SQLException;
}
