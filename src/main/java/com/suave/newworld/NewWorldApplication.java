package com.suave.newworld;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Suave
 */
@SpringBootApplication
@MapperScan("com.suave.newworld.dao")
public class NewWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewWorldApplication.class, args);
    }

}
