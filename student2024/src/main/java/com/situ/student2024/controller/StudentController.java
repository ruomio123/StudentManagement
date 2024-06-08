package com.situ.student2024.controller;

import com.situ.student2024.model.Student;
import com.situ.student2024.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
@RequestMapping("/student")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * 展示一个学生列表
     */
    @GetMapping("/list")
    public String list() {


        return "student/list";
    }

    /**
     * 跳转到添加页面
     */
    @GetMapping("/add")
    public String add() {
        return "student/add";
    }


    /**
     * 跳转到修改页面
     */
    @GetMapping("/edit")
    public String edit(Integer id, Map<String, Object> map) {
        Student student = service.findById(id);
        if (student == null) {
            map.put("error", "指定编号的员工不存在");
        } else {
            map.put("stu", student);

        }
        return "student/edit";
    }


}
