package com.puge.demo.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.puge.demo.eduservice.client.VodClient;
import com.puge.demo.eduservice.entity.EduCourse;
import com.puge.demo.eduservice.entity.EduCourseDescription;
import com.puge.demo.eduservice.entity.vo.CourseInfoVo;
import com.puge.demo.eduservice.entity.vo.CoursePublishVo;
import com.puge.demo.eduservice.mapper.EduCourseMapper;
import com.puge.demo.eduservice.service.EduChapterService;
import com.puge.demo.eduservice.service.EduCourseDescriptionService;
import com.puge.demo.eduservice.service.EduCourseService;
import com.puge.demo.eduservice.service.EduVideoService;
import com.puge.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author pyz
 * @since 2022-09-30
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    /**
     * 课程描述注入
     */
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    /**
     * 注入小节和章节service
     */
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private VodClient vodClient;


    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表中添加课程基本信息
        //CourseInfoVo-->EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            //添加失败
            throw new GuliException(20001, "添加课程信息失败");
        }

        //向课程简介表添加数据
        EduCourseDescription courseDescription = new EduCourseDescription();
        //获取并设置课程简介
        courseDescription.setDescription(courseInfoVo.getDescription());
        //获取并设置课程id
        //设置描述id
        courseDescription.setId(eduCourse.getId());
        courseDescriptionService.save(courseDescription);


        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        /**
         * 查询课程信息
         */
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        /**
         * 查询课程描述
         */
        EduCourseDescription eduCourseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            //添加失败
            throw new GuliException(20001, "修改课程信息失败");
        }

        //向课程简介表添加数据
        EduCourseDescription courseDescription = new EduCourseDescription();
        //获取并设置课程简介
        courseDescription.setDescription(courseInfoVo.getDescription());
        //获取并设置课程id
        //设置描述id
        courseDescription.setId(eduCourse.getId());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    /**
     * 根据课程id查询课程确认信息
     */
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //2 根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //3 根据课程id删除描述
        courseDescriptionService.removeById(courseId);

        //4 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result == 0) {
            //失败返回
            throw new GuliException(20001,"删除失败");
        }
    }


}
