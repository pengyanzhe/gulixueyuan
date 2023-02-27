package com.puge.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication

/**
 * 指定扫描位置
 */
@ComponentScan({"com.puge"})
@MapperScan("com.puge.educms.mapper")

/**
 * @author pyz
 */
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}