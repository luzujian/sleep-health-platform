package com.sleephealth.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.sleephealth.**.mapper")
public class MybatisConfig {
    // MyBatis Plus 配置通过 application.yml 完成
    // 此处可添加自定义配置
}
