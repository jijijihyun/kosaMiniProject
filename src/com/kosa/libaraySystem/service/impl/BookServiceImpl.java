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

    // 출판사 이름을 입력받아 출판사 번호를 리턴
    @Override
    public int publisherSearch(String bpublisher) {
        // Implement the logic to search for a publisher using the DAO
        return bookDAO.findPublisherNoByName(bpublisher);
    }

    // 작가 이름을 입력받아 작가 번호를 리턴
    @Override
    public int authorSearch(String a) {
        // Implement the logic to search for an author using the DAO
        return bookDAO.findAuthorNoByName(a);
    }

    // 책 제목을 입력받아 해당 첵을 삭제
    public void deleteBookByTitle(String title) {
        bookDAO.deleteBookByTitle(title);
    }


    // 책 제목으로 검색
    @Override
    public Book searchBookByTitle(String title) {
        return bookDAO.findBookByTitle(title);
    }

    // 책 제목을 입력받아 바로 업데이트
    @Override
    public void updateBook(String oldTitle, String newTitle, int authorNo, int publisherNo, int categoryNo) {
        bookDAO.updateBook(oldTitle, newTitle, authorNo, publisherNo, categoryNo);
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


}
