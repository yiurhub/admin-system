package com.yiur.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * swagger 配置
 * @author Yiur
 */
@Profile({"dev", "test"})
@EnableOpenApi
@Configuration
public class SwaggerConfig {

    /**
     * 配置swagger
     * @return Docket
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yiur.admin.controller"))
                .paths(PathSelectors.ant("/**"))
                .build().groupName("master");
    }

    /**
     * 配置swagger标题信息
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("Yiur", "https://www.cnblogs.com/yiur-bgy/", "3227341995@qq.com");

        return new ApiInfo(
                "admin system",
                "Yiur admin system api",
                "version-1.0",
                "https://www.cnblogs.com/yiur-bgy/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>()
        );
    }

}
