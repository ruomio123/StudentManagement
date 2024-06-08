package com.situ.student2024.service;

import com.situ.student2024.model.Student;
import com.situ.student2024.util.PaginateInfo;

import java.util.List;


public interface StudentService {
    //查询所有员工列表
    List<Student> findAll(PaginateInfo pi, Student student);

    //根据主键查询唯一员工
    Student findById(Integer id);


    //批量删除，返回受影响的行数
    int deleteByIds(Integer[] ids);

    /**
     * 保存一个员工对象
     */
    boolean save(Student student);


    boolean update(Student student);
}
