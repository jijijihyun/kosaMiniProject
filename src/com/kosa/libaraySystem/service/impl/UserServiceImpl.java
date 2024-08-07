package com.kosa.libaraySystem.service.impl;

import com.kosa.libaraySystem.dao.UserDAO;
import com.kosa.libaraySystem.model.User;
import com.kosa.libaraySystem.service.UserService;
import com.kosa.libaraySystem.util.PasswordUtil;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO = new UserDAO();
    @Override
    public User login(String userId, String password) throws SQLException {
        User user = userDAO.getUserById(userId);
        if(user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    @Override
    public void register(User user) throws SQLException {
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        userDAO.addUser(user);
    }

    @Override
    public User getUserInstanceDataByUserNo(int num) throws SQLException {
        return userDAO.getUserByUserNo(num);
    }
}
