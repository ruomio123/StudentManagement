package com.situ.student2024.dao;

import com.situ.student2024.model.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface StudentDao {
    //查询所有员工列表
    List<Student> findAll(Student student);

    Student findById(Integer id);

    //批量删除
    int deleteByIds(Integer[] ids);

    //保存员工信息
    int save(Student student);

    //修改员工信息
    int update(Student student);
}
