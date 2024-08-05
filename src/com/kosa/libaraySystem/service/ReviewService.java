package com.kosa.libaraySystem.service;

import com.kosa.libaraySystem.model.Review;
import com.kosa.libaraySystem.model.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ReviewService {


    public boolean canSetReview(String bookTitle) throws SQLException; //리뷰 등록이 가능한지
    public Review getReview() throws SQLException; //책에 리뷰가 있다면 가져오고 없다면 null
    public void setReview(User user, int bookNo, String content, Date nowTime) throws SQLException; //책에 리뷰 세팅
    public List<Review> getReviewsByBookTitle(String bookTitle)throws SQLException;
}
