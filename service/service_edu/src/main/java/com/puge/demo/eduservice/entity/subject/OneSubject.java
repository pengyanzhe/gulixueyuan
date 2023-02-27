package com.puge.demo.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pyz
 */
@Data
public class OneSubject {
    private String id;
    private String title;

    /**
     * 一个一级分类由多个二级分类
     */
    private List<TwoSubject> children = new ArrayList<>();
}
