package com.zhang.storagestatisticsserve;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({ "com.zhang.storagestatisticsserve.mapper" })
public class StorageStatisticsServeApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageStatisticsServeApplication.class, args);
    }

}
