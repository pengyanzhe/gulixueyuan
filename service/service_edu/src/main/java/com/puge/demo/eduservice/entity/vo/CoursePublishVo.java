package com.puge.demo.eduservice.entity.vo;

import lombok.Data;

/**
 * @author pyz
 */
@Data
public class CoursePublishVo {
    //只用于显示

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;
}
