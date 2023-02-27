package com.puge.demo.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.puge.demo.eduservice.entity.EduSubject;
import com.puge.demo.eduservice.entity.excel.SubjectData;
import com.puge.demo.eduservice.entity.subject.OneSubject;
import com.puge.demo.eduservice.entity.subject.TwoSubject;
import com.puge.demo.eduservice.listener.SubjectExcelListener;
import com.puge.demo.eduservice.mapper.EduSubjectMapper;
import com.puge.demo.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-09-23
 */
@Service

public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    /**
     * 添加课程分类
     */
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        InputStream in;
        try {
            //文件输入流
            in = file.getInputStream();

            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有的一级与二级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", 0);
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", 0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);


        //封装一级与二级分类
        //创建list集合,用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();


        //遍历oneSubjectList集合
        for (EduSubject eduSubject : oneSubjectList) {
            //把eduSubject，放到finalSubjectList里面去
            //多个OneSubject放到finalSubjectList里面
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //简化操作
            BeanUtils.copyProperties(eduSubject, oneSubject);
            //多个OneSubject放到finalSubjectList里面
            finalSubjectList.add(oneSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建一个twoFinalSubjectList用来封装TwoSubject
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            //多个TwoSubject放到twoFinalSubjectList里面
            //遍历获取每一个二级分类
            for (EduSubject subject : twoSubjectList) {
                //判断一二级分类是否对应
                if (subject.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    //复制subject到twoSubject
                    BeanUtils.copyProperties(subject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }

            //把所有的二级分类放到以及分类里面
                oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}
