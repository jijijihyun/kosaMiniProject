package com.kosa.libaraySystem.service.impl;

import com.kosa.libaraySystem.dao.BookDAO;
import com.kosa.libaraySystem.model.Book;
import com.kosa.libaraySystem.model.BookGrouped;
import com.kosa.libaraySystem.service.BookService;

import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDAO bookDAO = new BookDAO();


    // 책 정보를 DAO에 전달
    @Override
    public void addBook(String btitle, int authorNo, int publisherNo, int categoryNo) {
        // Implement the logic to add a book using the DAO
        bookDAO.insertBook(btitle, authorNo, publisherNo, categoryNo);
    }

    // 카태고리을 입력받아 카태고리 번호를 리턴
    @Override
    public int categorySearch(String bcategory) {
        // Implement the logic to search for a category using the DAO
        return bookDAO.findCategoryNoByName(bcategory);
    }

    @Override
    public String reverseCategorySearch(int bcategory) {
        return bookDAO.findCategoryNoByName(bcategory);
    }    // 출판사 이름을 입력받아 출판사 번호를 리턴
    @Override
    public int publisherSearch(String bpublisher) {
        // Implement the logic to search for a publisher using the DAO
        return bookDAO.findPublisherNoByName(bpublisher);
    }

    @Override
    public String reversePublisherSearch(int bpublisher) {
        return bookDAO.findNAmeByPublisherNo(bpublisher);
    }    // 작가 이름을 입력받아 작가 번호를 리턴
    @Override
    public int authorSearch(String a) {
        // Implement the logic to search for an author using the DAO
        return bookDAO.findAuthorNoByName(a);
    }

    @Override
    public String reverseAuthorSearch(int a) {

        return bookDAO.findNameByAuthorNo(a);

    }    // 책 제목을 입력받아 해당 첵을 삭제
    public void deleteBookByTitle(int bookNo) {
        bookDAO.deleteBookByTitle(bookNo);

    }


    // 책 제목으로 검색
    @Override
    public List<Book> searchBookByTitle(String title) {

        return bookDAO.findBookByTitle(title);
    }

    // 책 제목을 입력받아 바로 업데이트
    @Override
    public void updateBook(String newTitle, int ano, int pno, int cno, int bookNo) {
        bookDAO.updateBook(newTitle, ano, pno, cno, bookNo);
    }
    
    public int bNoSearch(int bno) {

        return bookDAO.findBooksNoByNo(bno);
    }


    /*
    ==========유저쪽 구현===========
     */
    @Override
    public List<Book> getBooksSearchTitle(String bookTitle) throws SQLException {
        return bookDAO.getBookListSelectTitle(bookTitle);
    }
    public List<BookGrouped> getBookGroupedSearchTitle(String bookTitle) throws SQLException{
        return bookDAO.getBookGroupedListSelectTitle(bookTitle);
    }

    @Override
    public List<BookGrouped> getBookGroupedSearchByAuthorName(String authorName) throws SQLException {
        return bookDAO.getBookGroupedListSelectAuthorName(authorName);
    }

    @Override
    public List<BookGrouped> getBookGroupedSearchByPublisherName(String pubName) throws SQLException {
        return bookDAO.getBookGroupedListSelectPublisherName(pubName);
    }

    @Override
    public List<BookGrouped> getBookGroupedSearchByCategoryNum(int categoryNo) {
        return bookDAO.getBookGroupedListSelectCategoryNum(categoryNo);
    }

    @Override
    public Book getBookSearchByTitle(String title) throws SQLException {
        return bookDAO.selectBookByBookTitle(title);
    }


}
