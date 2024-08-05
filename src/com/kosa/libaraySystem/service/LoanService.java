package com.kosa.libaraySystem.service;

import com.kosa.libaraySystem.model.Book;
import com.kosa.libaraySystem.model.User;

import java.sql.SQLException;

public interface LoanService {
    void createLoanRowByBook(Book book, User user) throws SQLException;

    void updateStatusBookByBook(Book book, String status) throws SQLException;
}
