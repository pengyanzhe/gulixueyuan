package com.puge.demo.eduservice.service;

import com.puge.demo.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author pyz
 * @since 2022-10-01
 */
public interface EduVideoService extends IService<EduVideo> {
    /**
     * 根据课程id删除小节
     */
    void removeVideoByCourseId(String courseId);
}
