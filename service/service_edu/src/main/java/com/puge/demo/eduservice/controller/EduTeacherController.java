package com.puge.demo.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.puge.commonutils.R;
import com.puge.demo.eduservice.entity.EduTeacher;
import com.puge.demo.eduservice.entity.vo.TeacherQuery;
import com.puge.demo.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author pyz
 * @since 2022-09-02
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService service;

    /**
     * rest风格
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = service.list(null);
        return R.ok().data("items",list);
    }

    /**
     * 逻辑删除讲师的方法
     * @param id 讲师id
     * @return
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)
                           @PathVariable String id) {
        boolean flag = service.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 分页查询讲师的方法
     * @param current 当前页
     * @param limit 每页记录数
     * @return
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current", value = "查询页数+1", required = true)
                                 @PathVariable long current,
                             @ApiParam(name = "limit", value = "查询数量", required = true)
                                 @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

//        try {
//            int a = 10/0;
//        }catch(Exception e) {
//            throw new GuliException(20001,"出现自定义异常");
//        }
        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        service.page(pageTeacher,null);

        //总记录数
        long total = pageTeacher.getTotal();
        //数据list集合
        List<EduTeacher> records = pageTeacher.getRecords();

//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);

        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "条件分页查询")
    /**
     * 4 条件查询带分页的方法
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current", value = "查询页数+1", required = true)
                                      @PathVariable long current,
                                  @ApiParam(name = "limit", value = "查询数量", required = true)
                                      @PathVariable long limit,
                                  @RequestBody(required = false)  TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        // mybatis学过 动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        service.page(pageTeacher,wrapper);

        //总记录数
        long total = pageTeacher.getTotal();
        //数据list集合
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }


    /**
     * 添加讲师接口的方法
     * @param eduTeacher
     * @return
     */
    @PostMapping("addTeacher")
    @ApiOperation(value = "添加讲师")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = service.save(eduTeacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 根据讲师id进行查询
     * @param id
     * @return
     */
    @GetMapping("getTeacher/{id}")
    @ApiOperation(value = "根据ID查询")
    public R getTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)@PathVariable String id) {
        EduTeacher eduTeacher = service.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }


    /**
     * 讲师修改功能
     * @param eduTeacher 讲师类
     * @return
     */
    @PostMapping("updateTeacher")
    @ApiOperation(value = "讲师修改")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = service.updateById(eduTeacher);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}

