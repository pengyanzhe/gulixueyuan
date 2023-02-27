package com.puge.demo.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.puge.demo.eduservice.entity.EduSubject;
import com.puge.demo.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-09-23
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file
     * @param subjectService
     */
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
