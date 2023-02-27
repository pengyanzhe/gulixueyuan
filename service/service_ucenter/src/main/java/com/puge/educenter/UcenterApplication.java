package com.puge.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author pyz
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.puge"})
@MapperScan("com.puge.educenter.mapper")
public class UcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
