package com.sleephealth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sleephealth.**.mapper")
public class SleepHealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SleepHealthApplication.class, args);
    }
}
