package com.study.web;

import com.study.annotation.TestAOPOrderAnnotation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 学习自定义注解
 * 学习aop执行顺序
 * 参考文章:https://blog.csdn.net/yb546822612/article/details/88116654
 */
@RestController
public class TestAOPOrderController {
    @TestAOPOrderAnnotation()
    @RequestMapping("/add")
    public String addData() {
        System.out.println("=====进入方法addData=====");
        return UUID.randomUUID().toString();
    }
}
