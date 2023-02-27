package com.puge.demo.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.puge.demo.eduservice.entity.EduCourse;
import com.puge.demo.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author pyz
 * @since 2022-09-30
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getPublishCourseInfo(String courseId);

}
