package com.suave.newworld;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Suave
 */
@SpringBootApplication
@EnableCaching
@MapperScan("com.suave.newworld.dao")
public class NewWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewWorldApplication.class, args);
    }

}
