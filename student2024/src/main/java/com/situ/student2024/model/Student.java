package com.situ.student2024.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Getter
@Setter
public class Student {
    private Integer id;//编号，主键
    private String studentId;//学号
    private String college;//学院
    private String major;//专业
    private String name;//姓名
    private String sex;//性别

}
