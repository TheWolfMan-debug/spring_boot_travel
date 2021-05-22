package com.wolfman.travel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@MapperScan(value = "com.wolfman.travel.mapper")//mybatis包扫描
@EnableCaching//支持缓存
@SpringBootApplication
public class TravelApplication {

    /**
     * main方法
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(TravelApplication.class, args);
    }

}
