package com.puge.demo.eduservice.config;

import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;

/**
 * @author pyz
 */
@Configuration
@MapperScan("com.puge.demo.eduservice.mapper")
public class EduConfig {


    /**
     * 逻辑删除插件
     * @return
     */
    @Bean
    public ISqlInjector sqllnjector() {
        return new LogicSqlInjector();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
