package com.kosa.libaraySystem.service;

import com.kosa.libaraySystem.model.Review;
import com.kosa.libaraySystem.model.User;

import java.sql.SQLException;

public interface ReviewService {


    public boolean canSetReview(String bookTitle) throws SQLException; //리뷰 등록이 가능한지
    public Review getReview() throws SQLException; //책에 리뷰가 있다면 가져오고 없다면 null
    public void setReview(User user, String bookTitle, String content) throws SQLException; //책에 리뷰 세팅
}
