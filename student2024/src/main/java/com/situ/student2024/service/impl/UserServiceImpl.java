package com.situ.student2024.service.impl;

import com.situ.student2024.dao.UserDAO;
import com.situ.student2024.model.User;
import com.situ.student2024.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}
