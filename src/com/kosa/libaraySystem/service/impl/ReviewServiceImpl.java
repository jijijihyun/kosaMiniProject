package com.kosa.libaraySystem.service.impl;

import com.kosa.libaraySystem.dao.ReviewDAO;
import com.kosa.libaraySystem.model.Review;
import com.kosa.libaraySystem.model.User;
import com.kosa.libaraySystem.service.ReviewService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ReviewServiceImpl implements ReviewService {

    ReviewDAO reviewDAO = new ReviewDAO();

    public Review getReview(){

        return null;
    }

    public void setReview(User user, int bookNo, String content, Date nowTime){
        System.out.println("리뷰서비스 실행");
        reviewDAO.insertReview(user, bookNo, content, nowTime);
    }

    @Override
    public List<Review> getReviewsByBookTitle(String bookTitle) throws SQLException {
        return reviewDAO.selectReviewsByBookTitleAll(bookTitle);
    }

    /*
    도서가 존재한다면 트루 아니면 펄스
     */
    @Override
    public boolean canSetReview(String bookTitle) throws SQLException {

        return reviewDAO.isValidBook(bookTitle);
    }
}
