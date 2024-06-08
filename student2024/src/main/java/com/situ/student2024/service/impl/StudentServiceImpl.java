package com.situ.student2024.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.student2024.dao.StudentDao;
import com.situ.student2024.model.Student;
import com.situ.student2024.service.StudentService;
import com.situ.student2024.util.PaginateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao;

    @Autowired
    public void setEmployeeDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public List<Student> findAll(PaginateInfo pi, Student student) {
        //第1步：在本地线程变量中存储分页信息，指定页码和页面大小
        try (Page<?> p = PageHelper.startPage(pi.getPageNo(), pi.getPageSize());) {
            //第2步：发起正常查询
            List<Student> list = studentDao.findAll(student);

            //第3步：获取分页数据，分页对象
            PageInfo<Student> pif = new PageInfo<>(list);
            pif.calcByNavigatePages(5);//共显示5页导航
            pi.setPageInfo(pif);
            return list;
        }
    }

    @Override
    public Student findById(Integer id) {
        return studentDao.findById(id);
    }

    @Override
    public int deleteByIds(Integer[] ids) {
        return studentDao.deleteByIds(ids);
    }

    @Override
    public boolean save(Student student) {
        return studentDao.save(student) > 0;
    }


    @Override
    public boolean update(Student student) {
        return studentDao.update(student) > 0;
    }
}
