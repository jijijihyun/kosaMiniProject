package com.kosa.libaraySystem.service.impl;

import com.kosa.libaraySystem.dao.LoanDAO;
import com.kosa.libaraySystem.model.Book;
import com.kosa.libaraySystem.model.BookLoanInfo;
import com.kosa.libaraySystem.model.User;
import com.kosa.libaraySystem.service.LoanService;

import java.sql.SQLException;
import java.util.List;

public class LoanServiceImpl implements LoanService {
    LoanDAO loanDAO = new LoanDAO();
    @Override
    public void createLoanRowByBook(Book book, User user) throws SQLException {
        try{
            loanDAO.insertBookloansByBook(book, user);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatusBookByBook(Book book, String status) throws SQLException {
        try{
            loanDAO.updateBookStatusbyBook(book, status);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateReturnDateByBookNo(int bookNo, int userNo) throws SQLException {
        loanDAO.updateReturnDateByBookNo(bookNo, userNo);
    }

    @Override
    public List<BookLoanInfo> readUserNotYetReturnDataList(User user) throws SQLException {
        try{
            return loanDAO.selectDataByUser(user);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
