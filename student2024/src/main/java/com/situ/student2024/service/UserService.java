package com.situ.student2024.service;

import com.situ.student2024.model.User;


public interface UserService {
    User findByUsername(String username);
}
