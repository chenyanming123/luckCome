package com.luck.dataWeb.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@SpringBootConfiguration
public class MySpringConfig extends WebMvcConfigurerAdapter {
    @Resource
    private LoginAppConfig loginAppConfig;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAppConfig)
                .excludePathPatterns("/error","/login/login","/login/register","/login/httpTest","/storageSync/getCode2Session")
                .excludePathPatterns("/appDataUserinfo/queryUserInfo","/appDataUserinfo/getUserInfo")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        super.addInterceptors(registry);
    }

}
