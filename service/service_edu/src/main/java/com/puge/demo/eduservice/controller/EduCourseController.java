package com.puge.demo.eduservice.controller;


import com.puge.commonutils.R;
import com.puge.demo.eduservice.entity.EduCourse;
import com.puge.demo.eduservice.entity.vo.CourseInfoVo;
import com.puge.demo.eduservice.entity.vo.CoursePublishVo;
import com.puge.demo.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author pyz
 * @since 2022-09-30
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService service;

    /**
     * 课程列表 基本实现
     * TODO  完善条件查询带分页
     *
     * @return
     */
    @GetMapping
    public R getCourseList() {
        List<EduCourse> list = service.list(null);
        return R.ok().data("list", list);
    }

    /**
     * 添加课程基本信息的方法
     *
     * @param courseInfoVo 课程信息
     * @return
     */
    @PostMapping("addCourseInfo")
    @ApiOperation(value = "添加课程信息")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = service.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    /**
     * 根据课程id查询课程基本信息
     *
     * @param courseId 课程id
     * @return
     */
    @GetMapping("getCourseInfo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = service.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    /**
     * 修改课程信息
     *
     * @param courseInfoVo 课程信息
     * @return
     */
    @PostMapping("updateCourseInfo")
    @ApiOperation(value = "修改课程信息")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        service.updateCourseInfo(courseInfoVo);
        return R.ok();

    }

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = service.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }


    //

    /**
     * 课程最终发布
     * 修改课程状态
     * @param id
     * @return
     */
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        //设置课程发布状态
        eduCourse.setStatus("Normal");
        service.updateById(eduCourse);
        return R.ok();
    }

    /**
     * 删除课程
     * @param courseId
     * @return
     */
    @DeleteMapping("{courseId}")
    public R deleteChapter(@PathVariable String courseId) {
        service.removeCourse(courseId);
        return R.ok();
    }

}

