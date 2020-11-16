package com.luck.dataWeb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;


@Configuration
@EnableSwagger2
public class SwaggerDocConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("缘来Api v1.0")
                .description("缘来系统的所有开发接口")
                .license("")
                .licenseUrl("http://www.luckCome.com")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("", "", ""))
                .build();
    }

    @Bean
    SecurityScheme apiKey() {
        return new ApiKey("token", "token", "header");
    }


    @Bean
    public Docket adminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin-api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.luck.dataWeb.controller"))
                .build()
                .directModelSubstitute(java.sql.Timestamp.class, String.class)
                .securitySchemes(newArrayList(apiKey()));
    }
}
