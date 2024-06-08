package com.situ.student2024.dao;

import com.situ.student2024.model.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDAO {

    User findByUsername(String username);
}
