package com.kosa.libaraySystem.service;

import com.kosa.libaraySystem.model.Book;
import com.kosa.libaraySystem.model.BookLoanInfo;
import com.kosa.libaraySystem.model.User;

import java.sql.SQLException;
import java.util.List;

public interface LoanService {
    void createLoanRowByBook(Book book, User user) throws SQLException;

    void updateStatusBookByBook(Book book, String status) throws SQLException;

    void updateReturnDateByBookNo(int bookNo, int userNo) throws SQLException;

    List<BookLoanInfo> readUserNotYetReturnDataList(User user) throws SQLException;
}
