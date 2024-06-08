package com.situ.student2024.api;

import com.github.pagehelper.PageInfo;
import com.situ.student2024.model.Student;
import com.situ.student2024.service.StudentService;
import com.situ.student2024.util.PaginateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/v1/student", produces = "application/json;charset=utf-8")
public class StudentApi {
    private StudentService studentService;

    @Autowired
    public void setEmployeeService(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 接收一个get类型的请求，返回Map类型的对象，可以转换成js中的json对象
     */
    @GetMapping
    public Map<String, Object> list(@RequestParam(defaultValue = "1") Integer pageNo,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    Student student) {

        //创建分页对象
        PaginateInfo pi = PaginateInfo.of(pageNo, pageSize);

        //调用业务类的方法
        List<Student> studentList = studentService.findAll(pi, student);

        //取出分页对象
        PageInfo<?> pif = pi.getPageInfo();

        return Map.of("success", true, "data", studentList, "pi", pif);
    }


    /**
     * 删除操作
     *
     * @param ids 整数数组，主键，编号
     * @return 键值对集合
     */
    @DeleteMapping
    public Map<String, Object> deleteByIds(Integer[] ids) {
        //返回受影响的行数
        int rows = studentService.deleteByIds(ids);
        return Map.of("success", rows > 0, "rows", rows);
    }

    /**
     * 新增学生
     *
     * @param student 员工对象
     * @return 键值对
     */
    @PostMapping
    public Map<String, Object> add(Student student) {
        boolean success = studentService.save(student);
        return Map.of("success", success);
    }

    /**
     * 修改员工信息
     */
    @PatchMapping
    public Map<String, Object> edit(Student student) {
        boolean success = studentService.update(student);
        return Map.of("success", success);
    }


}
