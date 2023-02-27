package com.puge.demo.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.puge.demo.eduservice.entity.EduChapter;
import com.puge.demo.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author pyz
 * @since 2022-10-01
 */
public interface EduChapterService extends IService<EduChapter> {
    /**
     * 课程大纲列表,根据课程id进行查询
     * */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 删除章节的方法
     */
    boolean deleteChapter(String chapterId);


    /**
     * 根据课程id删除章节
     */
    void removeChapterByCourseId(String courseId);

}
