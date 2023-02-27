package com.puge.demo.eduservice.controller;


import com.puge.commonutils.R;
import com.puge.demo.eduservice.entity.EduChapter;
import com.puge.demo.eduservice.entity.chapter.ChapterVo;
import com.puge.demo.eduservice.service.EduChapterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author pyz
 * @since 2022-10-01
 */
@RestController
@RequestMapping("/eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {

    private final EduChapterService service;

    public EduChapterController(EduChapterService service) {
        this.service = service;
    }

    /**
     * 课程大纲列表，根据课程id进行查询
     */
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = service.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    /**
     * 添加章节
     * @param eduChapter
     * @return
     */
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        service.save(eduChapter);
        return R.ok();
    }

    /**
     * 根据id查询章节
     * @param chapterId
     * @return
     */
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = service.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    /**
     * 修改章节
     * @param eduChapter
     * @return
     */
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        service.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 删除章节的方法
     * @param chapterId
     * @return
     */
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = service.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

