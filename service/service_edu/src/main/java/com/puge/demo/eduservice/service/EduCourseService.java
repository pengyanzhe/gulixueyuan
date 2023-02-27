package com.puge.demo.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.puge.demo.eduservice.entity.EduCourse;
import com.puge.demo.eduservice.entity.vo.CourseInfoVo;
import com.puge.demo.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author pyz
 * @since 2022-09-30
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息的方法
     * @param courseInfoVo
     * @return
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfo(String courseId);


    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    CoursePublishVo publishCourseInfo(String id);

    /**
     * 删除课程
     * @param courseId
     */
    void removeCourse(String courseId);
}
