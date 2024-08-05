package com.kosa.libaraySystem.service;

import com.kosa.libaraySystem.model.User;

import java.sql.SQLException;

public interface UserService {
    public User login(String userId, String password) throws SQLException;
    public void register(User user) throws SQLException;

    /*
    유저쪽 구현
     */
    public User getUserInstanceDataByUserNo(int num) throws SQLException;
}
