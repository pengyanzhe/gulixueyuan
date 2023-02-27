package com.puge.demo.eduservice.controller;


import com.puge.commonutils.R;
import com.puge.demo.eduservice.entity.subject.OneSubject;
import com.puge.demo.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-23
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    /**
     * 添加课程分类
     * 获取上传过来的文件
     * @param file
     * @return
     */
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    /**
     * 课程分类列表（树形）
     * @return
     */
    @GetMapping("getALLSubject")
    public R getALLSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

