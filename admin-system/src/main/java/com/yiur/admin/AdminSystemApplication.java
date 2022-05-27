package com.yiur.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 解决2.6.x与swagger的冲突
 *
 * @author Yiur
 */
@MapperScan("com.yiur.admin.mapper")
@EnableWebMvc
@SpringBootApplication
public class AdminSystemApplication {

    /**
     * SpringBoot Application 上下文
     */
    public static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(AdminSystemApplication.class, args);
    }

}
